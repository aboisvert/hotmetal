package hotmetal

import munit.FunSuite

class HtmlElementsSuite extends FunSuite with HtmlAssertions:
  import Html.*
  import HtmlElements.*

  // --- document metadata ---

  test("head renders nested metadata"):
    val actual = Html:
      head():
        title():
          text("Page")
    assertHtmlEquals(actual.toString, "<head>\n<title>Page</title>\n</head>\n")

  test("title renders nested text"):
    val actual = Html:
      title():
        text("Dashboard")
    assertHtmlEquals(actual.toString, "<title>Dashboard</title>")

  test("title renders named attributes"):
    val actual = Html:
      title(id = "t", `class` = "page-title"):
        text("Dashboard")
    assertHtmlEquals(actual.toString, """<title id="t"  class="page-title" >Dashboard</title>""")

  test("meta renders void element"):
    val actual = Html:
      meta(charset = "utf-8")
    assertHtmlEquals(actual.toString, """<meta charset="utf-8" />""")

  test("meta renders extra attrs"):
    val actual = Html:
      meta(name = "viewport", content = "width=device-width")
    assertHtmlEquals(actual.toString, """<meta name="viewport"  content="width=device-width" />""")

  test("link renders void element"):
    val actual = Html:
      link(rel = "stylesheet", href = "/app.css")
    assertHtmlEquals(actual.toString, """<link rel="stylesheet"  href="/app.css" />""")

  test("style renders nested css"):
    val actual = Html:
      style():
        html".card { color: red; }"
    assertHtmlEquals(actual.toString, "<style>\n.card { color: red; }\n</style>\n")

  test("script(defer, src) renders defer and nested content"):
    val actual = Html:
      script(defer = true, src = "/app.js")
    assertHtmlEquals(actual.toString, "<script defer src=\"/app.js\" >\n\n</script>\n")

  test("script(string) renders inline script"):
    val actual = Html:
      script("console.log('hi');")
    assertHtmlEquals(actual.toString, "<script>\nconsole.log('hi');\n</script>\n")

  test("noscript renders fallback content"):
    val actual = Html:
      noscript:
        p():
          text("Enable JavaScript")
    assertHtmlEquals(actual.toString, "<noscript>\n<p>Enable JavaScript</p>\n</noscript>\n")

  test("base renders void element"):
    val actual = Html:
      base(href = "https://example.com/")
    assertHtmlEquals(actual.toString, """<base href="https://example.com/" />""")

  // --- page structure ---

  test("body renders nested content"):
    val actual = Html:
      body(`class` = "app"):
        main():
          text("Hello")
    assertHtmlEquals(actual.toString, """<body class="app" ><main>Hello</main></body>""")

  test("header renders nested content"):
    val actual = Html:
      header(id = "site", `class` = "header"):
        h1():
          text("Hotmetal")
    assertHtmlEquals(actual.toString, """<header id="site"  class="header" ><h1>Hotmetal</h1></header>""")

  test("nav renders nested links"):
    val actual = Html:
      nav(id = "main-nav"):
        a(href = "/"):
          text("Home")
    assertHtmlEquals(actual.toString, """<nav id="main-nav" ><a href="/" >Home</a></nav>""")

  test("main renders nested content"):
    val actual = Html:
      main(id = "content"):
        text("Primary")
    assertHtmlEquals(actual.toString, """<main id="content" >Primary</main>""")

  test("section renders nested content"):
    val actual = Html:
      section(id = "features"):
        h2():
          text("Features")
    assertHtmlEquals(actual.toString, """<section id="features" ><h2>Features</h2></section>""")

  test("article renders nested content"):
    val actual = Html:
      article(`class` = "post"):
        h2():
          text("Notes")
    assertHtmlEquals(actual.toString, """<article class="post" ><h2>Notes</h2></article>""")

  test("aside renders nested content"):
    val actual = Html:
      aside(`class` = "sidebar"):
        text("Related")
    assertHtmlEquals(actual.toString, """<aside class="sidebar" >Related</aside>""")

  test("footer renders nested content"):
    val actual = Html:
      footer(`class` = "site-footer"):
        p():
          text("© 2026")
    assertHtmlEquals(actual.toString, """<footer class="site-footer" ><p>© 2026</p></footer>""")

  test("address renders nested content"):
    val actual = Html:
      address():
        text("support@example.com")
    assertHtmlEquals(actual.toString, "<address>support@example.com</address>")

  // --- headings and text ---

  test("h1 renders nested text"):
    val actual = Html:
      h1(id = "page-title"):
        text("Welcome")
    assertHtmlEquals(actual.toString, """<h1 id="page-title" >Welcome</h1>""")

  test("h2 renders nested text"):
    val actual = Html:
      h2():
        text("Overview")
    assertHtmlEquals(actual.toString, "<h2>Overview</h2>")

  test("h3 renders nested text"):
    val actual = Html:
      h3():
        text("Details")
    assertHtmlEquals(actual.toString, "<h3>Details</h3>")

  test("h4 renders nested text"):
    val actual = Html:
      h4():
        text("Subsection")
    assertHtmlEquals(actual.toString, "<h4>Subsection</h4>")

  test("h5 renders nested text"):
    val actual = Html:
      h5():
        text("Note")
    assertHtmlEquals(actual.toString, "<h5>Note</h5>")

  test("h6 renders nested text"):
    val actual = Html:
      h6():
        text("Fine print")
    assertHtmlEquals(actual.toString, "<h6>Fine print</h6>")

  test("p renders nested text"):
    val actual = Html:
      p(`class` = "lead"):
        text("Intro")
    assertHtmlEquals(actual.toString, """<p class="lead" >Intro</p>""")

  test("p escapes dangerous text"):
    val actual = Html:
      p():
        text("<script>alert('XSS')</script>")
    assertHtmlEquals(
      actual.toString,
      """<p>&lt;script&gt;alert(&apos;XSS&apos;)&lt;&#47;script&gt;</p>"""
    )

  test("a renders hyperlink"):
    val actual = Html:
      a(id = "docs", `class` = "link", href = "/docs"):
        text("Docs")
    assertHtmlEquals(actual.toString, """<a id="docs"  class="link"  href="/docs" >Docs</a>""")

  test("span renders inline content"):
    val actual = Html:
      span(`class` = "badge"):
        text("New")
    assertHtmlEquals(actual.toString, """<span class="badge" >New</span>""")

  test("div renders block content"):
    val actual = Html:
      div(id = "demo", `class` = "grid"):
        text("hello")
    assertHtmlEquals(actual.toString, """<div id="demo"  class="grid" >hello</div>""")

  test("br renders self-closing tag"):
    val actual = Html:
      br()
    assertHtmlEquals(actual.toString, "<br/>")

  test("hr renders self-closing tag"):
    val actual = Html:
      hr()
    assertHtmlEquals(actual.toString, "<hr/>")

  test("strong renders nested text"):
    val actual = Html:
      strong():
        text("save")
    assertHtmlEquals(actual.toString, "<strong>save</strong>")

  test("em renders nested text"):
    val actual = Html:
      em():
        text("important")
    assertHtmlEquals(actual.toString, "<em>important</em>")

  test("b renders nested text"):
    val actual = Html:
      b():
        text("Bold")
    assertHtmlEquals(actual.toString, "<b>Bold</b>")

  test("i renders nested text"):
    val actual = Html:
      i():
        text("italic")
    assertHtmlEquals(actual.toString, "<i>italic</i>")

  test("u renders nested text"):
    import HtmlElements.u
    val actual = Html:
      u():
        text("annotated")
    assertHtmlEquals(actual.toString, "<u>annotated</u>")

  test("small renders nested text"):
    val actual = Html:
      small():
        text("Disclaimer")
    assertHtmlEquals(actual.toString, "<small>Disclaimer</small>")

  test("mark renders nested text"):
    val actual = Html:
      mark():
        text("keyword")
    assertHtmlEquals(actual.toString, "<mark>keyword</mark>")

  test("sub renders nested text"):
    val actual = Html:
      sub():
        text("2")
    assertHtmlEquals(actual.toString, "<sub>2</sub>")

  test("sup renders nested text"):
    val actual = Html:
      sup():
        text("2")
    assertHtmlEquals(actual.toString, "<sup>2</sup>")

  test("code renders nested text"):
    val actual = Html:
      code():
        text("Html.text")
    assertHtmlEquals(actual.toString, "<code>Html.text</code>")

  test("pre renders nested text"):
    val actual = Html:
      pre():
        text("val x = 1")
    assertHtmlEquals(actual.toString, "<pre>val x = 1</pre>")

  test("kbd renders nested text"):
    val actual = Html:
      kbd():
        text("Ctrl")
    assertHtmlEquals(actual.toString, "<kbd>Ctrl</kbd>")

  test("samp renders nested text"):
    val actual = Html:
      samp():
        text("OK")
    assertHtmlEquals(actual.toString, "<samp>OK</samp>")

  test("var renders nested text"):
    val actual = Html:
      `var`():
        text("n")
    assertHtmlEquals(actual.toString, "<var>n</var>")

  test("blockquote renders nested content"):
    val actual = Html:
      blockquote(id = "quote"):
        p():
          text("Quoted")
    assertHtmlEquals(actual.toString, """<blockquote id="quote" ><p>Quoted</p></blockquote>""")

  test("q renders inline quote"):
    val actual = Html:
      q(id = "inline-quote"):
        text("hello")
    assertHtmlEquals(actual.toString, """<q id="inline-quote" >hello</q>""")

  test("cite renders nested text"):
    val actual = Html:
      cite():
        text("The Book")
    assertHtmlEquals(actual.toString, "<cite>The Book</cite>")

  test("abbr renders nested text"):
    val actual = Html:
      abbr(id = "html-abbr"):
        text("HTML")
    assertHtmlEquals(actual.toString, """<abbr id="html-abbr" >HTML</abbr>""")

  test("time renders nested text"):
    val actual = Html:
      time(id = "published"):
        text("May 18")
    assertHtmlEquals(actual.toString, """<time id="published" >May 18</time>""")

  // --- media and embeds ---

  test("img renders void element"):
    val actual = Html:
      img(src = "/logo.svg", alt = "Logo")
    assertHtmlEquals(actual.toString, """<img src="/logo.svg"  alt="Logo" />""")

  test("picture renders nested sources"):
    val actual = Html:
      picture():
        source(src = "/hero.webp")
        img(src = "/hero.jpg", alt = "Hero")
    assertHtmlEquals(
      actual.toString,
      """<picture><source src="/hero.webp" /><img src="/hero.jpg"  alt="Hero" /></picture>"""
    )

  test("source renders void element"):
    val actual = Html:
      source(src = "/clip.webm", `type` = "video/webm")
    assertHtmlEquals(actual.toString, """<source src="/clip.webm"  type="video/webm" />""")

  test("figure renders nested content"):
    val actual = Html:
      figure():
        img(src = "/chart.png", alt = "Chart")
        figcaption():
          text("Q1")
    assertHtmlEquals(
      actual.toString,
      """<figure><img src="/chart.png"  alt="Chart" /><figcaption>Q1</figcaption></figure>"""
    )

  test("figcaption renders nested text"):
    val actual = Html:
      figcaption():
        text("Caption")
    assertHtmlEquals(actual.toString, "<figcaption>Caption</figcaption>")

  test("audio renders nested source"):
    val actual = Html:
      audio(controls = "controls"):
        source(src = "/sound.mp3", `type` = "audio/mpeg")
    assertHtmlEquals(
      actual.toString,
      """<audio controls="controls" ><source src="/sound.mp3"  type="audio/mpeg" /></audio>"""
    )

  test("video renders nested source"):
    val actual = Html:
      video(controls = "controls", width = "640"):
        source(src = "/clip.mp4", `type` = "video/mp4")
    assertHtmlEquals(
      actual.toString,
      """<video controls="controls"  width="640" ><source src="/clip.mp4"  type="video/mp4" /></video>"""
    )

  test("track renders void element"):
    val actual = Html:
      track(kind = "captions", src = "/en.vtt", srclang = "en", label = "English")
    assertHtmlEquals(
      actual.toString,
      """<track src="/en.vtt"  kind="captions"  srclang="en"  label="English" />"""
    )

  test("iframe renders nested content"):
    val actual = Html:
      iframe(src = "https://example.com/embed", title = "Embedded")
    assertHtmlEquals(
      actual.toString,
      """<iframe src="https://example.com/embed"  title="Embedded" />"""
    )

  test("embed renders void element"):
    val actual = Html:
      embed(src = "/animation.swf", `type` = "application/x-shockwave-flash")
    assertHtmlEquals(
      actual.toString,
      """<embed src="/animation.swf"  type="application/x-shockwave-flash" />"""
    )

  test("object renders nested fallback"):
    val actual = Html:
      `object`(data = "/file.pdf", `type` = "application/pdf"):
        text("PDF")
    assertHtmlEquals(actual.toString, """<object data="/file.pdf"  type="application/pdf" >PDF</object>""")

  test("param renders void element"):
    val actual = Html:
      param(name = "autoplay", value = "true")
    assertHtmlEquals(actual.toString, """<param name="autoplay"  value="true" />""")

  test("canvas renders nested fallback"):
    val actual = Html:
      canvas(id = "chart", width = "400", height = "200"):
        text("No canvas")
    assertHtmlEquals(
      actual.toString,
      """<canvas id="chart"  width="400"  height="200" >No canvas</canvas>"""
    )

  test("svg renders nested markup"):
    val actual = Html:
      svg(viewBox = "0 0 24 24", width = "24", height = "24"):
        html"<circle />"
    assertHtmlEquals(
      actual.toString,
      """<svg viewBox="0 0 24 24"  width="24"  height="24" ><circle /></svg>"""
    )

  // --- lists ---

  test("ul renders list items"):
    val actual = Html:
      ul(`class` = "list-disc"):
        li():
          text("First")
        li():
          text("Second")
    assertHtmlEquals(actual.toString, """<ul class="list-disc" ><li>First</li><li>Second</li></ul>""")

  test("ol renders list items"):
    val actual = Html:
      ol(start = "1"):
        li():
          text("Step one")
        li():
          text("Step two")
    assertHtmlEquals(actual.toString, """<ol start="1" ><li>Step one</li><li>Step two</li></ol>""")

  test("li renders list entry"):
    val actual = Html:
      li(`class` = "item"):
        text("Entry")
    assertHtmlEquals(actual.toString, """<li class="item" >Entry</li>""")

  test("dl renders description list"):
    val actual = Html:
      dl():
        dt():
          text("HTML")
        dd():
          text("HyperText Markup Language")
    assertHtmlEquals(actual.toString, "<dl><dt>HTML</dt><dd>HyperText Markup Language</dd></dl>")

  test("dt renders description term"):
    val actual = Html:
      dt():
        text("API")
    assertHtmlEquals(actual.toString, "<dt>API</dt>")

  test("dd renders description details"):
    val actual = Html:
      dd():
        text("Application Programming Interface")
    assertHtmlEquals(actual.toString, "<dd>Application Programming Interface</dd>")

  // --- tables ---

  test("table renders nested structure"):
    val actual = Html:
      table(`class` = "min-w-full"):
        thead():
          tr():
            th():
              text("Name")
        tbody():
          tr():
            td():
              text("Ada")
    assertHtmlEquals(
      actual.toString,
      """<table class="min-w-full" ><thead><tr><th>Name</th></tr></thead><tbody><tr><td>Ada</td></tr></tbody></table>"""
    )

  test("caption renders nested text"):
    val actual = Html:
      caption():
        text("Monthly totals")
    assertHtmlEquals(actual.toString, "<caption>Monthly totals</caption>")

  test("thead renders nested rows"):
    val actual = Html:
      thead():
        tr():
          th():
            text("Col")
    assertHtmlEquals(actual.toString, "<thead><tr><th>Col</th></tr></thead>")

  test("tbody renders nested rows"):
    val actual = Html:
      tbody():
        tr():
          td():
            text("Value")
    assertHtmlEquals(actual.toString, "<tbody><tr><td>Value</td></tr></tbody>")

  test("tfoot renders nested rows"):
    val actual = Html:
      tfoot():
        tr():
          td():
            text("Total")
    assertHtmlEquals(actual.toString, "<tfoot><tr><td>Total</td></tr></tfoot>")

  test("tr renders nested cells"):
    val actual = Html:
      tr():
        td():
          text("Cell")
    assertHtmlEquals(actual.toString, "<tr><td>Cell</td></tr>")

  test("th renders header cell"):
    val actual = Html:
      th(scope = "col"):
        text("Price")
    assertHtmlEquals(actual.toString, """<th scope="col" >Price</th>""")

  test("td renders data cell"):
    val actual = Html:
      td(colspan = "2"):
        text("Merged")
    assertHtmlEquals(actual.toString, """<td colspan="2" >Merged</td>""")

  test("col renders void element"):
    val actual = Html:
      col(span = "2", `class` = "numeric")
    assertHtmlEquals(actual.toString, """<col class="numeric"  span="2" />""")

  test("colgroup renders nested columns"):
    val actual = Html:
      colgroup(span = "3"):
        col(`class` = "wide")
    assertHtmlEquals(actual.toString, """<colgroup span="3" ><col class="wide" /></colgroup>""")

  // --- forms ---

  test("form renders nested controls"):
    val actual = Html:
      form(action = "/login", method = "post"):
        input(`type` = "text", name = "email")
    assertHtmlEquals(
      actual.toString,
      """<form action="/login"  method="post" ><input type="text"  name="email" /></form>"""
    )

  test("label renders nested text"):
    val actual = Html:
      label(`for` = "email"):
        text("Email")
    assertHtmlEquals(actual.toString, """<label for="email" >Email</label>""")

  test("input renders void element"):
    val actual = Html:
      input(`type` = "email", name = "email", required = "required")
    assertHtmlEquals(
      actual.toString,
      """<input type="email"  name="email"  required="required" />"""
    )

  test("textarea renders nested text"):
    val actual = Html:
      textarea(name = "message", rows = "4"):
        text("Hello")
    assertHtmlEquals(actual.toString, """<textarea name="message"  rows="4" >Hello</textarea>""")

  test("button renders nested label"):
    val actual = Html:
      button(`type` = "submit", `class` = "btn"):
        text("Save")
    assertHtmlEquals(actual.toString, """<button class="btn"  type="submit" >Save</button>""")

  test("select renders nested options"):
    val actual = Html:
      select(name = "plan"):
        option(value = "free"):
          text("Free")
        option(value = "pro", selected = "selected"):
          text("Pro")
    assertHtmlEquals(
      actual.toString,
      """<select name="plan" ><option value="free" >Free</option><option value="pro"  selected="selected" >Pro</option></select>"""
    )

  test("option renders nested text"):
    val actual = Html:
      option(value = "ca"):
        text("Canada")
    assertHtmlEquals(actual.toString, """<option value="ca" >Canada</option>""")

  test("optgroup renders nested options"):
    val actual = Html:
      optgroup(label = "North America"):
        option(value = "ca"):
          text("Canada")
    assertHtmlEquals(
      actual.toString,
      """<optgroup label="North America" ><option value="ca" >Canada</option></optgroup>"""
    )

  test("fieldset renders nested content"):
    val actual = Html:
      fieldset():
        legend():
          text("Shipping")
        input(`type` = "text", name = "address")
    assertHtmlEquals(
      actual.toString,
      """<fieldset><legend>Shipping</legend><input type="text"  name="address" /></fieldset>"""
    )

  test("legend renders nested text"):
    val actual = Html:
      legend():
        text("Account details")
    assertHtmlEquals(actual.toString, "<legend>Account details</legend>")

  test("datalist renders nested options"):
    val actual = Html:
      datalist(id = "browsers"):
        option(value = "Chrome")()
        option(value = "Firefox")()
    assertHtmlEquals(
      actual.toString,
      """<datalist id="browsers" ><option value="Chrome" ></option><option value="Firefox" ></option></datalist>"""
    )

  test("output renders nested text"):
    val actual = Html:
      output(name = "total", `for` = "qty price"):
        text("0")
    assertHtmlEquals(actual.toString, """<output for="qty price"  name="total" >0</output>""")

  test("progress renders void element"):
    val actual = Html:
      progress(value = "70", max = "100")
    assertHtmlEquals(actual.toString, """<progress value="70"  max="100" />""")

  test("meter renders void element"):
    val actual = Html:
      meter(value = "0.6", min = "0", max = "1")
    assertHtmlEquals(actual.toString, """<meter value="0.6"  min="0"  max="1" />""")

  // --- interactive and web components ---

  test("details renders nested summary and content"):
    val actual = Html:
      details():
        summary():
          text("More info")
        p():
          text("Hidden")
    assertHtmlEquals(actual.toString, "<details><summary>More info</summary><p>Hidden</p></details>")

  test("summary renders nested text"):
    val actual = Html:
      summary():
        text("Click to expand")
    assertHtmlEquals(actual.toString, "<summary>Click to expand</summary>")

  test("dialog renders nested content"):
    val actual = Html:
      dialog(id = "confirm", open = "open"):
        p():
          text("Are you sure?")
    assertHtmlEquals(actual.toString, """<dialog id="confirm"  open="open" ><p>Are you sure?</p></dialog>""")

  test("template renders nested content"):
    val actual = Html:
      template(id = "row-template"):
        tr():
          td():
            text("Placeholder")
    assertHtmlEquals(
      actual.toString,
      """<template id="row-template" ><tr><td>Placeholder</td></tr></template>"""
    )

  test("menu renders nested commands"):
    val actual = Html:
      menu(`type` = "toolbar"):
        button(`type` = "button"):
          text("Copy")
    assertHtmlEquals(actual.toString, """<menu type="toolbar" ><button type="button" >Copy</button></menu>""")

  test("slot renders nested fallback"):
    val actual = Html:
      slot(name = "title"):
        text("Default title")
    assertHtmlEquals(actual.toString, """<slot name="title" >Default title</slot>""")

  test("div helper matches elem div output"):
    val rendered = Html:
      div(`class` = "grid gap-4", id = "demo"):
        text("hello")

    assertHtmlEquals(rendered.toString, """<div id="demo"  class="grid gap-4" >hello</div>""")

end HtmlElementsSuite
