package hotmetal

import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.OutputStream
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.Charset
import java.nio.charset.CodingErrorAction
import java.nio.charset.StandardCharsets
import java.nio.charset.StandardCharsets.UTF_8
import scala.annotation.nowarn

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
  import Html.HtmlFn

  private final val buffer = java.lang.StringBuilder(initialCapacity)

  inline def append(inline s: String): Unit =
    { buffer.append(s); () }

  inline def append(inline chars: CharSequence): Unit =
    { buffer.append(chars); () }

  inline def append(c: Char): Unit =
    { buffer.append(c); () }

  inline def append(i: Int): Unit =
    { buffer.append(i); () }

  inline def append(l: Long): Unit =
    { buffer.append(l); () }

  def length: Int = buffer.length

  /** Clear the current buffer so this instance can be reused for another render. */
  def reset(): Unit =
    buffer.setLength(0)

  /** `asCharSequence` is more efficient than `toString` as it doesn't allocate */
  def asCharSequence: CharSequence = buffer

  override def toString: String = buffer.toString

  def toInputStream: InputStream =
    if buffer.length == 0 then return new ByteArrayInputStream(Array.emptyByteArray)
    val bytes = Html.encodeBytes(buffer, UTF_8)
    new ByteArrayInputStream(bytes, 0, bytes.length)

  def writeInto(out: OutputStream, charset: Charset = StandardCharsets.UTF_8): Unit =
    Html.writeEncoded(buffer, out, charset)


  /** Append a double-quoted attribute when `value` is non-null.
    *
    * Hotmetal always emits `name="value"`. User-controlled double quotes in `value`
    * are escaped so they cannot terminate the attribute value.
    */
  def attrNotNull(name: String, value: String): Unit =
    if value != null then
      append(' ')
      append(name)
      append('=')
      append('"')
      append(Html.escapeAttr(value))
      append('"')

  /** Append an attribute without a value, e.g. "disabled" or "required". */
  def attrNoValue(name: String): Unit =
    append(' ')
    append(name)

  inline def foreach(inline attrs: HtmlFn*)(using Html): Unit =
    var i = 0
    while i < attrs.length do
      attrs(i).apply
      i += 1

end Html // class

object Html:
  private final val EncodeBufferChars = 2048

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

  private def encodeBytes(chars: CharSequence, charset: Charset): Array[Byte] =
    val out = java.io.ByteArrayOutputStream(chars.length)
    writeEncoded(chars, out, charset)
    out.toByteArray

  private def writeEncoded(chars: CharSequence, out: OutputStream, charset: Charset): Unit =
    val encoder = charset
      .newEncoder()
      .onMalformedInput(CodingErrorAction.REPLACE)
      .onUnmappableCharacter(CodingErrorAction.REPLACE)
    val input = CharBuffer.wrap(chars)
    val output = ByteBuffer.allocate(math.max(EncodeBufferChars, (encoder.maxBytesPerChar() * EncodeBufferChars).toInt))

    var finishedEncoding = false
    while !finishedEncoding do
      val result = encoder.encode(input, output, true)
      flushEncodedBytes(output, out)
      if result.isUnderflow then finishedEncoding = true
      else if !result.isOverflow then result.throwException()

    var finishedFlushing = false
    while !finishedFlushing do
      val result = encoder.flush(output)
      flushEncodedBytes(output, out)
      if result.isUnderflow then finishedFlushing = true
      else if !result.isOverflow then result.throwException()

  private def flushEncodedBytes(buffer: ByteBuffer, out: OutputStream): Unit =
    val size = buffer.position()
    if size > 0 then out.write(buffer.array(), 0, size)
    buffer.clear()

  /** Create a new Html context, allowing the nested function to append content into it. */
  inline def apply(inline f: HtmlFn): Html =
    val buf = new Html()
    f(using buf)
    buf

  /** Escape HTML characters that can cause XSS hijacking. */
  inline def escape(chars: CharSequence, optimize: Boolean = true): CharSequence =
    HtmlUtils.escapeHtml(chars, optimize)

  /** Escape characters dangerous in hotmetal's double-quoted attribute values.
    *
    * Escapes `&`, `<`, `>`, and `"`. Apostrophes and `/` are left literal because
    * hotmetal always wraps attribute values in double quotes.
    *
    * Not suitable for single-quoted or unquoted attribute syntax; use `attr()` / `:=`
    * for library-generated attributes, or own escaping if emitting custom markup.
    */
  inline def escapeAttr(chars: CharSequence, optimize: Boolean = true): CharSequence =
    HtmlUtils.escapeHtmlAttr(chars, optimize)

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
  inline def unescaped(s: String)(using buf: Html): Unit =
    buf.append(s)

  def elem(name: String)(attrs: HtmlFn*)(nested: HtmlFn = ())(using
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

  /** Append a double-quoted attribute into the Html context.
    *
    * Always emits `name="value"`. The value is escaped for a double-quoted attribute
    * context to prevent XSS and attribute-value breakout. Apostrophes remain literal;
    * `/` is not escaped (e.g. `/login` stays as-is).
    */
  def attr(name: String, value: String)(using html: Html): Unit =
    html.append(' ')
    html.append(name)
    html.append('=')
    html.append('"')
    html.append(Html.escapeAttr(value))
    html.append('"')

  inline def attrNoValue(name: String)(using html: Html): Unit =
    html.attrNoValue(name)

  /** Append a sequence of attributes into the Html context. */
  inline def attrs(inline attrs: HtmlFn*)(using buf: Html): Unit =
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
              case value: java.lang.Integer =>
                '{ ${ htmlExpr }.append(${ Expr(value.intValue) }) }
              case value: java.lang.Long =>
                '{ ${ htmlExpr }.append(${ Expr(value.longValue) }) }
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
            else if term.tpe <:< IntTpe then
              '{ ${ htmlExpr }.append(${ term.asExprOf[Int] }) }
            else if term.tpe <:< LongTpe then
              '{ ${ htmlExpr }.append(${ term.asExprOf[Long] }) }
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
    inline def :=(value: String)(using Html): Unit =
      attr(s, value)

  /** Join several values with spaces, suitable as an attribute value.
    *
    * This helper produces a space-separated value string — not an attribute declaration.
    * Use it inside an attribute assignment:
    *
    * {{{
    *   "class" := attrValues("btn", "btn-primary")
    * }}}
    *
    * Which emits: `class="btn btn-primary"`
    */
  def attrValues(ss: String*)(using html: Html): Unit =
    var i = 0
    while i < ss.length do
      html.append(ss(i))
      if i < ss.length - 1 then html.append(" ")
      i += 1

end Html // object
