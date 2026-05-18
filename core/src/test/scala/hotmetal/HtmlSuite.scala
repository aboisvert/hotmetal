package hotmetal

import munit.FunSuite

import java.nio.charset.StandardCharsets
import java.nio.charset.StandardCharsets.UTF_8

class HtmlSuite extends FunSuite with CompileUtils:
  import Html.*

  test("Html interpolator - literal string"):
    val actual = Html:
      html"""<div></div>"""
    assertEquals(
      obtained = actual.toString,
      expected = """<div></div>"""
    )

  test("Html interpolator - with interpolated (Int) value"):
    val div = Html:
      html"""<div>${1}</div>"""
    assertEquals(
      obtained = div.toString,
      expected = """<div>1</div>"""
    )

  test("Html interpolator - with nested fragment"):
    val nested = Html:
      html"nestedValue"

    val div = Html:
      html"""<div>$nested</div>"""

    assertEquals(
      obtained = div.toString,
      expected = """<div>nestedValue</div>"""
    )


  test("Html interpolator - with nested def fragment"):
    println()
    println("====================================")
    println()
    def nested(using Html) =
      html"<span>nestedDef</span>"

    val div = Html:
      html"""<div>${nested}</div>"""


    assertEquals(
      obtained = div.toString,
      expected = """<div><span>nestedDef</span></div>"""
    )

  test("Some primitive literals are not supported: Boolean, Float, Double"):
    expectCompileError("java.lang.Boolean literals are intentionally not supported"):
      """
      Html:
        html"<div>${false}</div>"
      """

    expectCompileError("java.lang.Float literals are intentionally not supported"):
      """
      Html:
        html"<div>${1.0f}</div>"
      """

    expectCompileError("java.lang.Double literals are intentionally not supported"):
      """
      Html:
        html"<div>${1.0d}</div>"
      """

  test("Html interpolator - for loop calling a def"):
    def nested(i: Int)(using Html) =
      html"<span>$i</span>"

    val div = Html:
      html"""<div>${for i <- 1 to 2 do nested(i)}</div>"""

    assertEquals(
      obtained = div.toString,
      expected = """<div><span>1</span><span>2</span></div>"""
    )

  test("Html interpolator - for loop with inline nested html"):
    val div = Html:
      html"""
        <div>${for i <- 1 to 2 do html"<span>$i</span>"}</div>
      """

    assertEquals(
      obtained = div.toString.trim,
      expected = """<div><span>1</span><span>2</span></div>"""
    )

  test("Using text() always escapes dangerous characters"):
    val div = Html:
      html"""<div>${text("<script>alert('XSS')</script>")}</div>"""

    assertEquals(
      obtained = div.toString,
      expected = """<div>&lt;script&gt;alert(&apos;XSS&apos;)&lt;&#47;script&gt;</div>"""
    )

  test("String expressions are always escaped"):
    val str = "<script>alert('XSS')</script>"
    val div = Html:
      html"""<div>$str</div>"""

    assertEquals(
      obtained = div.toString,
      expected = """<div>&lt;script&gt;alert(&apos;XSS&apos;)&lt;&#47;script&gt;</div>"""
    )

  test("Interpolate html tag parameter"):
    val div = Html:
      val i = 1
      html"""<div id="$i">${"hello'"}</div>"""

    assertEquals(
      obtained = div.toString,
      expected = """<div id="1">hello&apos;</div>"""
    )

  test("Dangerous characters contained in variables are escaped"):
    val div = Html:
      val danger = "<bold>hello</bold>"
      html"""<span>${danger}</span>"""

    assertEquals(
      obtained = div.toString,
      expected = """<span>&lt;bold&gt;hello&lt;&#47;bold&gt;</span>"""
    )

  test("Html.toInputStream"):
    val div = Html:
      html"""<div>foo</div>"""
    assertEquals(
      obtained = String(div.toInputStream.readAllBytes(), StandardCharsets.UTF_8),
      expected = "<div>foo</div>"
    )

  test("Html.writeInto"):
    val div = Html:
      html"""<div>foo</div>"""
    val baos = java.io.ByteArrayOutputStream()
    div.writeInto(baos)
    assertEquals(
      obtained = baos.toString(StandardCharsets.UTF_8),
      expected = "<div>foo</div>"
    )

  test("Html.elem"):
    val div = Html:
      elem("div")(
        "class" := "foo",
        "id" := "bar"
      )(
        text("hello")
      )

    assertEquals(
      obtained = div.toString,
      expected = """<div class="foo" id="bar">hello</div>"""
    )

  test("attrValues()"):
    val attributes = Seq("btn", "btn-primary")
    val div = Html:
      html"""<div class="${attrValues(attributes*)}">foo</div>"""
    assertEquals(
      obtained = String(div.toInputStream.readAllBytes(), StandardCharsets.UTF_8),
      expected = """<div class="btn btn-primary">foo</div>"""
    )

  test("attributes are interpolated"):
    val div = Html:
      html"""<div ${"class" := "btn btn-primary"}>foo</div>"""
    assertEquals(
      obtained = String(div.toInputStream.readAllBytes(), StandardCharsets.UTF_8),
      expected = """<div  class="btn btn-primary">foo</div>""" // note the two spaces before 'class'
    )

  test("Html.writeInto() should generate correct bytes"):
    val fragment = Html:
      html"""<start>${for i <- 1 to 2 do html"<div>$i</div>"}<end>"""
    val baos = java.io.ByteArrayOutputStream()
    fragment.writeInto(baos)
    baos.close()
    assertEquals(
      obtained = baos.toByteArray.toSeq,
      expected = """<start><div>1</div><div>2</div><end>""".getBytes(UTF_8).toSeq
    )

  test("Html.toInputStream() should generate correct bytes"):
    val fragment = Html:
      html"""<start>${for i <- 1 to 2 do html"<div>$i</div>"}<end>"""
    val bytes = fragment.toInputStream.readAllBytes()
    assertEquals(
      obtained = bytes.toSeq,
      expected = """<start><div>1</div><div>2</div><end>""".getBytes(UTF_8).toSeq
    )

  test("Interpolator reports an error on an unapplied def"):
    val foo = () => "foo"

    expectCompileError("Expression 'foo' of type 'trait Function0' intentionally not supported."):
      """
      Html:
        html"<div>${foo}</div>"
      """

  test("Interpolator reports an error on map() operation returning a collection"):
    val foo = () => "foo"

    expectCompileError("Expression 'scala.collection.immutable.Seq[java.lang.String]' of type 'trait Seq' intentionally not supported."):
      """
      Html:
        html"<div>${Seq(1, 2, 3).map(_.toString)}</div>"
      """


  test("Interpolator reports an error arbitrary class"):
    case class Foo()
    expectCompileError("Expression 'Foo' of type 'class Foo' intentionally not supported."):
      """
      Html:
        html"<div>${Foo()}</div>"
      """

  test("Given an instance of Html.Stringify[Foo](needsEscaping = false), it should be used to convert Foo to CharSequence"):
    given Html.Stringify[Foo] = Html.Stringify.unescaped[Foo](_ => "Foo&")
    case class Foo()
    val div = Html:
      html"<div>${Foo()}</div>"
    assertEquals(
      obtained = div.toString,
      expected = """<div>Foo&</div>"""
    )

  test("Given an instance of Html.Stringify[Foo](needsEscaping = true), it should be used to convert Foo to CharSequence"):
    given Html.Stringify[Foo] = Html.Stringify.escaped[Foo](_ => "Foo&")
    case class Foo()
    val div = Html:
      html"<div>${Foo()}</div>"
    assertEquals(
      obtained = div.toString,
      expected = """<div>Foo&amp;</div>"""
    )
