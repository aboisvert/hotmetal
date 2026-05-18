package hotmetal

import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.charset.StandardCharsets.UTF_8

/** Html supports efficient generation of Html content using the `html` interpolator or
  * elements/attributes provided by the `Dsl` object.
  *
  * The Html context is effectively a 'builder' that avoids creating many temporary objects. The
  * various html-generating helpers write into a mutable StringBuilder that's part of the context.
  *
  * The Html context is typically created at the root of a Html document, or is instantiated for
  * standalone Html fragments that are cached and composed into larger documents.
  */
final class Html(initialCapacity: Int = 256):
  private final val buffer = java.lang.StringBuilder(initialCapacity)
  private var renderedCache: String | Null = null

  inline def append(chars: CharSequence): Unit =
    renderedCache = null
    buffer.append(chars)

  inline def append(c: Char): Unit =
    renderedCache = null
    buffer.append(c)

  def length: Int = buffer.length

  def reset(): Unit =
    renderedCache = null
    buffer.setLength(0)

  /** `asCharSequence` is more efficient than `toString` as it doesn't allocate */
  def asCharSequence: CharSequence = buffer

  override def toString: String =
    val cached = renderedCache
    if cached != null then cached.nn
    else
      val rendered = buffer.toString
      renderedCache = rendered
      rendered

  def toInputStream: InputStream =
    if buffer.length == 0 then return new ByteArrayInputStream(Array.empty)
    val bytes = toString.getBytes(UTF_8)
    new ByteArrayInputStream(bytes, 0, bytes.length)

  def writeInto(out: OutputStream, charset: Charset = StandardCharsets.UTF_8): Unit =
    if buffer.length == 0 then return
    val bytes = toString.getBytes(charset)
    out.write(bytes, 0, bytes.length)

end Html // class

object Html:
  /** Shorthand for functions generating Html content. */
  type HtmlFn = Html ?=> Unit

  /** Implicitly convert Html to String. */
  given Conversion[Html, String] with
    def apply(html: Html): String = html.toString

  /** Witness that a type is allowed to be used as an escaped/unescaped string in an Html context.
    */
  class Stringify[T](val needsEscaping: Boolean, val toStr: T => CharSequence)

  /** Convenience object to create instances of `Stringify`.
    *
    * Example:
    *
    * ```
    * given Html.Stringify[Foo] = Html.Stringify.escaped(_.toString)
    * ```
    */
  object Stringify:
    def escaped[T](toStr: T => CharSequence): Stringify[T] =
      Stringify[T](needsEscaping = true, toStr)
    def unescaped[T](toStr: T => CharSequence): Stringify[T] =
      Stringify[T](needsEscaping = false, toStr)

  // given [T, U]: HtmlArg[Function[T, U]] = HtmlArg.NotAllowed()

  val empty: Html = new Html(initialCapacity = 0)

  /** Create a new Html context, allowing the nested function to append content into it. */
  def apply(f: Html ?=> Unit): Html =
    val buf = new Html()
    f(using buf)
    buf

  /** Escape HTML characters that can cause XSS hijacking. */
  inline def escape(chars: CharSequence, optimize: Boolean = true): CharSequence =
    HtmlUtils.escapeHtml(chars, optimize)

  /** Append a text fragment into the Html context.
    *
    * This method *always* escapes dangerous characters to prevent XSS.
    */
  inline def text(chars: CharSequence)(using buf: Html): Unit =
    buf.append(escape(chars))

  /** Append a text fragment into the Html context.
    *
    * This method *does not* escape dangerous characters to prevent XSS.
    */
  inline def unescaped(chars: CharSequence)(using buf: Html): Unit =
    buf.append(chars)

  def elem(name: String)(attrs: (Html ?=> Unit)*)(nested: Html ?=> Unit = ())(using
      buf: Html
  ): Unit =
    buf.append('<')
    buf.append(name)
    var i = 0
    while i < attrs.length do
      attrs(i).apply
      i += 1
    buf.append('>')
    nested.apply
    buf.append('<')
    buf.append('/')
    buf.append(name)
    buf.append('>')

  /** Append an attribute into the Html context. */
  def attr(name: String, value: CharSequence)(using _html: Html): Unit =
    _html.append(' ')
    _html.append(name)
    _html.append('=')
    _html.append('"')
    _html.append(value)
    _html.append('"')

  /** Append a sequence of attributes into the Html context. */
  def attrs(attrs: (Html ?=> Unit)*)(using buf: Html): Unit =
    var i = 0
    while i < attrs.length do
      attrs(i).apply(using buf)
      i += 1

  /** Scala compiler hook for the `html` interpolator. */
  extension (inline sc: StringContext)
    /** Html interpolator */
    inline def html(inline args: Any*)(using html: Html): Unit =
      // forwards to the macro implementation
      ${ Impl.html('sc, 'args, 'html) }

    inline def u(inline args: Any*)(using html: Html): Unit =
      // forwards to the macro implementation
      unescaped(sc.s(args*))

  extension (inline s: String)
    inline def u(using html: Html): Unit =
      unescaped(s)

  private object Impl:
    import scala.quoted.*

    /** Implementation of the `html` interpolator. */
    def html(scExpr: Expr[StringContext], argsExpr: Expr[Seq[Any]], htmlExpr: Expr[Html])(using
        _quotes: Quotes
    ): Expr[Unit] =
      import quotes.reflect.{*, given}
      import report.*

      val sc: StringContext = scExpr.valueOrAbort

      val stringLiterals: Seq[String] = sc.parts

      val args = argsExpr match
        case Varargs(argExprs) => argExprs
        case _                 => errorAndAbort(s"Args must be explicit", argsExpr)

      val HtmlExpr = '{ Html }

      def appendExpr(expr: Expr[Any]): Expr[Unit] =
        expr.asTerm match
          case term @ Literal(constant) =>
            constant.value match
              case value: CharSequence =>
                '{ ${ htmlExpr }.append(${ HtmlExpr }.escape(${ term.asExprOf[CharSequence] })) }
              case value: Char =>
                '{ ${ htmlExpr }.append(${ HtmlExpr }.escape(${ term.asExprOf[Char] }.toString)) }
              case value: Unit =>
                '{}
              case value: (java.lang.Integer | java.lang.Long) =>
                // other literals like Int, Long, etc. don't need to be escaped
                '{ ${ htmlExpr }.append(${ HtmlExpr }.escape(${ term.asExpr }.toString)) }
              case otherValue =>
                report.errorAndAbort(
                  s"${otherValue.getClass.getName} literals are intentionally not supported.  Use .toString if you insist.",
                  expr
                )
          case term =>
            val CharTpe = TypeRepr.of[Char]
            val CharSequenceTpe = TypeRepr.of[CharSequence]
            val StringTpe = TypeRepr.of[String]
            val UnitTpe = TypeRepr.of[Unit]
            val HtmlTpe = TypeRepr.of[Html].dealias
            val IntTpe = TypeRepr.of[Int]
            val LongTpe = TypeRepr.of[Long]

            // support some primitive types: Char, CharSequence, String, Int, Long
            if term.tpe <:< CharTpe then
              '{ ${ htmlExpr }.append(${ HtmlExpr }.escape(${ term.asExprOf[Char] }.toString)) }
            else if term.tpe <:< CharSequenceTpe then
              '{ ${ htmlExpr }.append(${ HtmlExpr }.escape(${ term.asExprOf[CharSequence] })) }
            else if term.tpe <:< StringTpe then
              '{ ${ htmlExpr }.append(${ HtmlExpr }.escape(${ term.asExprOf[String] })) }
            else if term.tpe <:< IntTpe || term.tpe <:< LongTpe then
              '{ ${ htmlExpr }.append(${ term.asExpr }.toString) }
            // support nested Html fragments
            else if term.tpe <:< HtmlTpe then
              '{ ${ htmlExpr }.append(${ term.asExprOf[Html] }.asCharSequence) }
            // support Unit type, which is literally ignored
            else if term.tpe <:< UnitTpe then //
              '{ ${ term.asExpr }: Unit }
            else
              term.tpe.dealias.widen.asType match
                case '[tTpe] =>
                  Expr.summon[Stringify[tTpe]] match
                    case Some(stringify) =>
                      '{
                        if ${ stringify }.needsEscaping then
                          ${ htmlExpr }.append(${ HtmlExpr }.escape(${ stringify }.toStr(${
                            term.asExprOf[tTpe]
                          })))
                        else //
                          ${ htmlExpr }.append(${ stringify }.toStr(${ term.asExprOf[tTpe] }))
                      }
                    case None =>
                      report.errorAndAbort(
                        s"Expression '${term.tpe.dealias.show}' of type '${term.tpe.typeSymbol}' intentionally not supported." +
                          s" Use .toString if you insist or provide a given HtmlArg.Escaped or HtmlArg.Unescaped.",
                        expr
                      )

      val htmlAppends = stringLiterals
        .zipAll(args, "", '{})
        .map { (stringLiteral, interpolatedExpr) =>
          val len = stringLiteral.length
          if len == 0 then
            // no need to append an empty string
            appendExpr(interpolatedExpr)
          else if len == 1 then
            // optimized case for single character strings, which are common
            // to separate values/expressions using spaces, commas, dashes, etc.
            '{
              ${ htmlExpr }.append(${ Expr(stringLiteral.charAt(0)) })
              ${ appendExpr(interpolatedExpr) }
            }
          else
            '{
              ${ htmlExpr }.append(${ Expr(stringLiteral) })
              ${ appendExpr(interpolatedExpr) }
            }
        }
        .map(_.asTerm)

      Block(htmlAppends.toList, Literal(UnitConstant())).asExprOf[Unit]
  end Impl

  extension (s: String)
    /** Emits an attribute.
      *
      * Syntax { "color" := "red" } is equivalent to { attr("color", "red") }
      */
    def :=(value: CharSequence)(using _html: Html): Unit =
      attr(s, value)

  /** Append a sequence of attribute values into the Attrs context.
    *
    * Syntax: "class" := attrValues("btn", "btn-primary")
    */
  def attrValues(ss: String*)(using _html: Html): Unit =
    var i = 0
    while i < ss.length do
      _html.append(ss(i))
      if i < ss.length - 1 then _html.append(" ")
      i += 1

end Html // object
