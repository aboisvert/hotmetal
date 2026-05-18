package hotmetal

import munit.FunSuite

class HtmlElementsSuite extends FunSuite:
  import Html.*
  import HtmlElements.*

  test("title renders nested text"):
    val actual = Html:
      title():
        text("Dashboard")

    assertEquals(
      obtained = actual.toString,
      expected = "<title>Dashboard</title>"
    )

  test("head wraps nested title"):
    val actual = Html:
      head():
        title():
          text("My Page")

    assertEquals(
      obtained = actual.toString,
      expected = "<head><title>My Page</title></head>"
    )

  test("meta renders attributes"):
    val actual = Html:
      meta("charset" := "utf-8")()

    assertEquals(
      obtained = actual.toString,
      expected = """<meta charset="utf-8"></meta>"""
    )

  test("body renders class attribute and nested content"):
    val actual = Html:
      body("class" := "app"):
        p():
          text("Hello")

    assertEquals(
      obtained = actual.toString,
      expected = """<body class="app"><p>Hello</p></body>"""
    )

  test("head, body, and main compose a minimal page skeleton"):
    val actual = Html:
      head():
        meta("charset" := "utf-8")()
        title():
          text("Hotmetal")
      body():
        main("id" := "content"):
          h1():
            text("Welcome")

    assertEquals(
      obtained = actual.toString,
      expected =
        """<head><meta charset="utf-8"></meta><title>Hotmetal</title></head><body><main id="content"><h1>Welcome</h1></main></body>"""
    )

  test("nav and anchor render a navigation link"):
    val actual = Html:
      nav("aria-label" := "Main"):
        a("href" := "/"):
          text("Home")

    assertEquals(
      obtained = actual.toString,
      expected = """<nav aria-label="Main"><a href="/">Home</a></nav>"""
    )

  test("div and span render nested inline content"):
    val actual = Html:
      div("class" := "card"):
        span("class" := "badge"):
          text("New")

    assertEquals(
      obtained = actual.toString,
      expected = """<div class="card"><span class="badge">New</span></div>"""
    )

  test("text content is escaped inside HtmlElements"):
    val actual = Html:
      p():
        text("<script>alert('x')</script>")

    assertEquals(
      obtained = actual.toString,
      expected = """<p>&lt;script&gt;alert(&apos;x&apos;)&lt;&#47;script&gt;</p>"""
    )

  test("input renders typed attributes"):
    val actual = Html:
      input("type" := "email", "name" := "email", "required" := "required")()

    assertEquals(
      obtained = actual.toString,
      expected = """<input type="email" name="email" required="required"></input>"""
    )
