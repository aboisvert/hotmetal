package hotmetal

import munit.FunSuite

class HtmlAssertionsSuite extends FunSuite with HtmlAssertions:

  test("ignores whitespace-only gaps between elements"):
    assertHtmlEquals(
      obtained = "</title>\n</head>",
      expected = "</title></head>"
    )

  test("collapses incidental spacing inside tag declarations"):
    assertHtmlEquals(
      obtained = """<div id="demo"  class="grid gap-4" >hello</div>""",
      expected = """<div id="demo" class="grid gap-4">hello</div>"""
    )

  test("preserves quoted attribute whitespace"):
    assertHtmlEquals(
      obtained = """<div class="grid gap-4"></div>""",
      expected = """<div class="grid gap-4"></div>"""
    )

  test("preserves significant text-node whitespace"):
    intercept[AssertionError]:
      assertHtmlEquals(
        obtained = "<p> hello </p>",
        expected = "<p>hello</p>"
      )

  test("preserves raw element interior whitespace"):
    assertHtmlEquals(
      obtained = "<style>\n.card { color: red; }\n</style>",
      expected = "<style>\n.card { color: red; }\n</style>"
    )
    assertHtmlEquals(
      obtained = "<pre>  spaced  </pre>",
      expected = "<pre>  spaced  </pre>"
    )

  test("treats differently spaced markup as equivalent structure"):
    assertHtmlEquals(
      obtained = "<head>\n<title>Page</title>\n</head>\n",
      expected = "<head><title>Page</title></head>"
    )
