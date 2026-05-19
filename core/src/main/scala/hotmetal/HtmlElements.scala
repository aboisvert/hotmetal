package hotmetal

import Html.*
import scala.annotation.nowarn

/** HtmlElements provides a set of inline methods for each HTML element.
  *
  * These methods are used to generate HTML elements in a more functional style. Each method accepts attribute fragments
  * and nested content that append into the current `Html` context.
  *
  * @example
  *   Html: head(): title("class" := "page-title"): text("My Page")
  */
object HtmlElements:

  /** The `<head>` element: Document metadata container.
    *
    * '''Common usage:''' Wrap `title`, `meta`, `link`, `style`, and `script` elements inside `html`.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   head(): meta("charset" := "utf-8") title(): text("My Page")
    */
  @nowarn
  def head(id: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<head")
    html.foreach(attrs)
    html.append(">\n")
    nested.apply
    html.append("\n</head>\n")

  /** The `<title>` element: Document title.
    *
    * '''Common usage:''' Set the page title shown in the browser tab and search results.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   title: text("Dashboard")
    */
  @nowarn
  def title(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<title")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</title>")

  /** The `<meta>` element: Metadata.
    *
    * '''Common usage:''' Declare charset, viewport, description, and other document or Open Graph metadata.
    *
    * '''Common attributes:''' `charset`, `name`, `content`, `http-equiv`, `property`, `media`
    *
    * @example
    *   meta("charset" := "utf-8") meta("name" := "viewport", "content" := "width=device-width, initial-scale=1")
    */
  @nowarn
  def meta(charset: String = null, name: String = null, content: String = null, attrs: HtmlFn*)(using
      html: Html
  ): Unit =
    html.append("<meta")
    html.attrNotNull("charset", charset)
    html.attrNotNull("name", name)
    html.attrNotNull("content", content)
    html.foreach(attrs)
    html.append("/>")

  /** The `<link>` element: External resource link.
    *
    * '''Common usage:''' Load stylesheets, icons, manifests, or prefetch resources.
    *
    * '''Common attributes:''' `rel`, `href`, `type`, `media`, `sizes`, `crossorigin`, `as`
    *
    * @example
    *   link("rel" := "stylesheet", "href" := "/app.css")
    */
  @nowarn // html.foeach giving bogus warning
  def link(
      rel: String = null,
      href: String = null,
      `type`: String = null,
      media: String = null,
      sizes: String = null,
      crossorigin: String = null,
      as: String = null,
      attrs: HtmlFn*
  )(using html: Html): Unit =
    html.append("<link")
    html.attrNotNull("rel", rel)
    html.attrNotNull("href", href)
    html.attrNotNull("type", `type`)
    html.attrNotNull("media", media)
    html.attrNotNull("sizes", sizes)
    html.attrNotNull("crossorigin", crossorigin)
    html.attrNotNull("as", as)
    html.foreach(attrs)
    html.append("/>")

  /** The `<style>` element: Embedded CSS.
    *
    * '''Common usage:''' Inline stylesheet rules inside the document.
    *
    * '''Common attributes:''' `media`, `type`, `nonce`, `id`, `class`
    *
    * @example
    *   style: html""" .card { padding: 1rem; } """
    */
  @nowarn
  def style(
      media: String = null,
      `type`: String = null,
      nonce: String = null,
      id: String = null,
      `class`: String = null,
      attrs: HtmlFn*
  )(nested: HtmlFn)(using html: Html): Unit =
    html.append("<style")
    html.attrNotNull("media", media)
    html.attrNotNull("type", `type`)
    html.attrNotNull("nonce", nonce)
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append(">\n")
    nested.apply
    html.append("\n</style>\n")

  /** The `<script>` element: Executable script.
    *
    * Specialized version of `script` that accepts a `defer` flag and a `src` attribute.
    *
    * @example
    *   script(defer = true, src = "/app.js")
    */
  @nowarn
  def script(defer: Boolean, src: String, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<script")
    if defer then html.attrNoValue("defer")
    html.attrNotNull("src", src)
    html.foreach(attrs)
    html.append(">\n")
    nested.apply
    html.append("\n</script>\n")

  /** The `<script>` element: Executable script.
    *
    * Specialized version of `script` that accepts a nested string.
    *
    * @example
    *   script("console.log('Hello, world!');")
    */
  @nowarn
  def script(nested: String)(using html: Html): Unit =
    html.append("<script>\n")
    html.append(nested)
    html.append("\n</script>\n")

  /** The `<noscript>` element: No-script fallback.
    *
    * '''Common usage:''' Provide alternate content when scripting is disabled.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   noscript: p: text("Please enable JavaScript.")
    */
  @nowarn
  def noscript(nested: HtmlFn)(using html: Html): Unit =
    html.append("<noscript>\n")
    nested.apply
    html.append("\n</noscript>\n")

  /** The `<base>` element: Base URL.
    *
    * '''Common usage:''' Set the default URL for relative links and form actions in the document.
    *
    * '''Common attributes:''' `href`, `target`
    *
    * @example
    *   base("href" := "https://example.com/")
    */
  @nowarn
  def base(href: String = null, target: String = null, attrs: HtmlFn*)(using html: Html): Unit =
    html.append("<base")
    html.attrNotNull("href", href)
    html.attrNotNull("target", target)
    html.foreach(attrs)
    html.append("/>")

  /** The `<body>` element: Document body.
    *
    * '''Common usage:''' Contain all visible page content rendered in the browser.
    *
    * '''Common attributes:''' `id`, `class`, `style`
    *
    * @example
    *   body("class" := "min-h-screen"): main: text("Hello")
    */
  @nowarn
  def body(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<body")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</body>")
    elem("body")(attrs*)(nested)

  /** The `<header>` element: Page or section header.
    *
    * '''Common usage:''' Introduce a page, article, or section with branding, titles, or toolbars.
    *
    * '''Common attributes:''' `id`, `class`, `role`
    *
    * @example
    *   header("class" := "site-header"): h1: text("Hotmetal")
    */
  @nowarn
  def header(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<header")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</header>")

  /** The `<nav>` element: Navigation links.
    *
    * '''Common usage:''' Group primary or secondary navigation links.
    *
    * '''Common attributes:''' `id`, `class`, `aria-label`
    *
    * @example
    *   nav("aria-label" := "Main"): a("href" := "/"): text("Home")
    */
  @nowarn
  def nav(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<nav")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</nav>")

  /** The `<main>` element: Main content.
    *
    * '''Common usage:''' Identify the dominant content of the document; use once per page.
    *
    * '''Common attributes:''' `id`, `class`, `role`
    *
    * @example
    *   main("id" := "content"): p: text("Primary page content.")
    */
  @nowarn
  def main(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<main")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</main>")

  /** The `<section>` element: Thematic grouping.
    *
    * '''Common usage:''' Organize related content with an optional heading.
    *
    * '''Common attributes:''' `id`, `class`, `aria-labelledby`
    *
    * @example
    *   section("id" := "features"): h2: text("Features")
    */
  @nowarn
  def section(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<section")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</section>")

  /** The `<article>` element: Self-contained composition.
    *
    * '''Common usage:''' Mark up blog posts, comments, cards, or news items that can stand alone.
    *
    * '''Common attributes:''' `id`, `class`, `itemscope`, `itemtype`
    *
    * @example
    *   article("class" := "post"): h2: text("Release notes")
    */
  @nowarn
  def article(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<article")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</article>")

  /** The `<aside>` element: Sidebar or tangential content.
    *
    * '''Common usage:''' Hold related notes, ads, or navigation complementary to main content.
    *
    * '''Common attributes:''' `id`, `class`, `aria-label`
    *
    * @example
    *   aside("class" := "sidebar"): nav: text("Related links")
    */
  @nowarn
  def aside(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<aside")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</aside>")

  /** The `<footer>` element: Footer for page or section.
    *
    * '''Common usage:''' Display copyright, links, or metadata at the bottom of a page or section.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   footer("class" := "site-footer"): p: text("© 2026 Example")
    */
  @nowarn
  def footer(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<footer")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</footer>")

  /** The `<address>` element: Contact information.
    *
    * '''Common usage:''' Mark up contact details for a person or organization.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   address: text("Contact us at support@example.com")
    */
  @nowarn
  def address(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<address")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</address>")

  /** The `<h1>` element: Heading level 1.
    *
    * '''Common usage:''' Top-level document or page heading; typically one per page.
    *
    * '''Common attributes:''' `id`, `class`, `title`
    *
    * @example
    *   h1("id" := "page-title"): text("Welcome")
    */
  @nowarn
  def h1(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<h1")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</h1>")

  /** The `<h2>` element: Heading level 2.
    *
    * '''Common usage:''' Major section heading beneath an `h1`.
    *
    * '''Common attributes:''' `id`, `class`, `title`
    *
    * @example
    *   h2: text("Overview")
    */
  @nowarn
  def h2(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<h2")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</h2>")

  /** The `<h3>` element: Heading level 3.
    *
    * '''Common usage:''' Subsection heading beneath an `h2`.
    *
    * '''Common attributes:''' `id`, `class`, `title`
    *
    * @example
    *   h3: text("Details")
    */
  @nowarn
  def h3(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<h3")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</h3>")

  /** The `<h4>` element: Heading level 4.
    *
    * '''Common usage:''' Nested subsection heading beneath an `h3`.
    *
    * '''Common attributes:''' `id`, `class`, `title`
    *
    * @example
    *   h4: text("Subsection")
    */
  @nowarn
  def h4(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<h4")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</h4>")

  /** The `<h5>` element: Heading level 5.
    *
    * '''Common usage:''' Minor heading beneath an `h4`.
    *
    * '''Common attributes:''' `id`, `class`, `title`
    *
    * @example
    *   h5: text("Note")
    */
  @nowarn
  def h5(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<h5")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</h5>")

  /** The `<h6>` element: Heading level 6.
    *
    * '''Common usage:''' Lowest-level heading beneath an `h5`.
    *
    * '''Common attributes:''' `id`, `class`, `title`
    *
    * @example
    *   h6: text("Fine print")
    */
  @nowarn
  def h6(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<h6")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</h6>")

  /** The `<p>` element: Paragraph.
    *
    * '''Common usage:''' Render a block of text content.
    *
    * '''Common attributes:''' `id`, `class`, `lang`, `dir`
    *
    * @example
    *   p("class" := "lead"): text("A short introduction.")
    */
  @nowarn
  def p(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<p")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</p>")

  /** The `<a>` element: Hyperlink.
    *
    * '''Common usage:''' Link to another page, fragment, file, or email address.
    *
    * '''Common attributes:''' `href`, `target`, `rel`, `download`, `hreflang`, `type`, `referrerpolicy`
    *
    * @example
    *   a(id = "docs-link", `class` = "link", href = "/docs"): text("Documentation")
    */
  @nowarn
  def a(
      id: String = null,
      `class`: String = null,
      href: String = null,
      target: String = null,
      rel: String = null,
      attrs: HtmlFn*
  )(nested: HtmlFn)(using html: Html): Unit =
    html.append("<a")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("href", href)
    html.attrNotNull("target", target)
    html.attrNotNull("rel", rel)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</a>")

  /** The `<span>` element: Generic inline container.
    *
    * '''Common usage:''' Wrap inline text or phrasing content for styling or scripting hooks.
    *
    * '''Common attributes:''' `id`, `class`, `lang`, `title`
    *
    * @example
    *   span("class" := "badge"): text("New")
    */
  @nowarn
  def span(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<span")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</span>")

  /** The `<div>` element: Generic block container.
    *
    * '''Common usage:''' Group block-level content for layout, styling, or behavior.
    *
    * '''Common attributes:''' `id`, `class`, `role`, `tabindex`
    *
    * @example
    *   div(`class` = "card"): p: text("Card body")
    */
  @nowarn
  def div(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<div")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</div>")

  /** The `<br>` element: Line break.
    *
    * '''Common usage:''' Insert a line break within text content.
    *
    * '''Common attributes:''' `id`, `class`, `clear`
    *
    * @example
    *   p: text("Line one") br() text("Line two")
    */
  def br()(using html: Html): Unit =
    html.append("<br/>")

  /** The `<hr>` element: Thematic break.
    *
    * '''Common usage:''' Separate sections with a horizontal rule.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   section: p: text("Part one") hr() p: text("Part two")
    */
  @nowarn
  def hr()(using html: Html): Unit =
    html.append("<hr/>")

  /** The `<strong>` element: Strong importance.
    *
    * '''Common usage:''' Indicate strongly important text; typically rendered bold.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p: text("Please ") strong: text("save") text(" your work.")
    */
  @nowarn
  def strong(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<strong")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</strong>")

  /** The `<em>` element: Emphasis.
    *
    * '''Common usage:''' Stress emphasis in text; typically rendered italic.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p: text("This is ") em: text("important") text(".")
    */
  @nowarn
  def em(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<em")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</em>")

  /** The `<b>` element: Bold text (presentational).
    *
    * '''Common usage:''' Draw attention without implying extra importance; prefer `strong` for semantics.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p: b: text("Bold") text(" label")
    */
  @nowarn
  def b(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<b")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</b>")
    elem("b")(attrs*)(nested)

  /** The `<i>` element: Italic text (presentational).
    *
    * '''Common usage:''' Offset text such as technical terms; prefer `em` for emphasis.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p: i: text("italic") text(" styling")
    */
  @nowarn
  def i(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<i")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</i>")

  /** The `<u>` element: Unarticulated annotation.
    *
    * '''Common usage:''' Mark text with a non-textual annotation such as a spelling error.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p: u: text("annotated")
    */
  @nowarn
  def u(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<u")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</u>")

  /** The `<small>` element: Side comment or fine print.
    *
    * '''Common usage:''' Represent disclaimers, copyright, or secondary information.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   small: text("Prices may vary.")
    */
  @nowarn
  def small(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<small")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</small>")

  /** The `<mark>` element: Highlighted text.
    *
    * '''Common usage:''' Highlight text referenced in another context, such as search results.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p: text("Find the ") mark: text("keyword") text(" here.")
    */
  @nowarn
  def mark(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<mark")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</mark>")

  /** The `<sub>` element: Subscript.
    *
    * '''Common usage:''' Render subscript text such as chemical formulas.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p: text("H") sub: text("2") text("O")
    */
  @nowarn
  def sub(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<sub")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</sub>")

  /** The `<sup>` element: Superscript.
    *
    * '''Common usage:''' Render superscript text such as footnote markers or exponents.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p: text("x") sup: text("2")
    */
  @nowarn
  def sup(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<sup")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</sup>")

  /** The `<code>` element: Inline code.
    *
    * '''Common usage:''' Mark a fragment of computer code within a sentence.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p: text("Use ") code: text("Html.text") text(" for escaping.")
    */
  @nowarn
  def code(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<code")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</code>")

  /** The `<pre>` element: Preformatted text.
    *
    * '''Common usage:''' Display text that preserves whitespace, often paired with `code`.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   pre: code: text("val x = 1")
    */
  @nowarn
  def pre(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<pre")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</pre>")

  /** The `<kbd>` element: Keyboard input.
    *
    * '''Common usage:''' Represent user keyboard input.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p: text("Press ") kbd: text("Ctrl") text("+") kbd: text("S") text(" to save.")
    */
  @nowarn
  def kbd(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<kbd")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</kbd>")

  /** The `<samp>` element: Sample output.
    *
    * '''Common usage:''' Represent sample or program output.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p: text("Output: ") samp: text("OK")
    */
  @nowarn
  def samp(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<samp")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</samp>")

  /** The `<var>` element: Variable.
    *
    * '''Common usage:''' Mark the name of a variable in mathematical or programming notation.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p: text("Let ") `var`: text("n") text(" be the count.")
    */
  @nowarn
  def `var`(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<var")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</var>")

  /** The `<blockquote>` element: Block quotation.
    *
    * '''Common usage:''' Quote content from another source.
    *
    * '''Common attributes:''' `cite`, `id`, `class`
    *
    * @example
    *   blockquote("cite" := "https://example.com"): p: text("A quoted passage.")
    */
  @nowarn
  def blockquote(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<blockquote")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</blockquote>")

  /** The `<q>` element: Inline quotation.
    *
    * '''Common usage:''' Quote short text inline.
    *
    * '''Common attributes:''' `cite`, `id`, `class`
    *
    * @example
    *   p: text("As they said, ") q("cite" := "https://example.com"): text("hello") text(".")
    */
  @nowarn
  def q(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<q")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</q>")

  /** The `<cite>` element: Citation.
    *
    * '''Common usage:''' Reference the title of a creative work.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p: cite: text("The Pragmatic Programmer")
    */
  @nowarn
  def cite(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<cite")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</cite>")

  /** The `<abbr>` element: Abbreviation.
    *
    * '''Common usage:''' Expand or annotate an abbreviation or acronym.
    *
    * '''Common attributes:''' `title`, `id`, `class`
    *
    * @example
    *   abbr("title" := "HyperText Markup Language"): text("HTML")
    */
  @nowarn
  def abbr(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<abbr")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</abbr>")

  /** The `<time>` element: Machine-readable datetime.
    *
    * '''Common usage:''' Represent dates, times, or durations with an optional `datetime` attribute.
    *
    * '''Common attributes:''' `datetime`, `id`, `class`
    *
    * @example
    *   time("datetime" := "2026-05-18"): text("May 18, 2026")
    */
  @nowarn
  def time(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<time")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</time>")

  /** The `<img>` element: Image.
    *
    * '''Common usage:''' Embed an image into the document.
    *
    * '''Common attributes:''' `src`, `alt`, `width`, `height`, `loading`, `decoding`, `srcset`, `sizes`, `crossorigin`
    *
    * @example
    *   img("src" := "/logo.svg", "alt" := "Logo")
    */
  @nowarn
  def img(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<img")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</img>")

  /** The `<picture>` element: Responsive image container.
    *
    * '''Common usage:''' Offer multiple image sources for different viewports or formats.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   picture: source("media" := "(min-width: 800px)", "srcset" := "large.jpg") img("src" := "small.jpg", "alt" :=
    *   "Hero")
    */
  @nowarn
  def picture(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<picture")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</picture>")

  /** The `<source>` element: Media source.
    *
    * '''Common usage:''' Specify alternative media for `picture`, `audio`, or `video` elements.
    *
    * '''Common attributes:''' `src`, `srcset`, `type`, `media`, `sizes`
    *
    * @example
    *   source("src" := "clip.webm", "type" := "video/webm")
    */
  @nowarn
  def source(
      id: String = null,
      `class`: String = null,
      src: String = null,
      srcset: String = null,
      `type`: String = null,
      media: String = null,
      sizes: String = null,
      attrs: HtmlFn*
  )(nested: HtmlFn)(using html: Html): Unit =
    html.append("<source")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("src", src)
    html.attrNotNull("srcset", srcset)
    html.attrNotNull("type", `type`)
    html.attrNotNull("media", media)
    html.attrNotNull("sizes", sizes)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</source>")

  /** The `<figure>` element: Illustration with optional caption.
    *
    * '''Common usage:''' Group self-contained media such as images, diagrams, or code listings.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   figure: img("src" := "/chart.png", "alt" := "Chart") figcaption: text("Quarterly growth")
    */
  @nowarn
  def figure(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<figure")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</figure>")

  /** The `<figcaption>` element: Figure caption.
    *
    * '''Common usage:''' Provide a caption or legend for a `figure`.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   figcaption: text("Figure 1: Architecture")
    */
  @nowarn
  def figcaption(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<figcaption")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</figcaption>")

  /** The `<audio>` element: Audio player.
    *
    * '''Common usage:''' Embed sound content with native browser controls.
    *
    * '''Common attributes:''' `src`, `controls`, `autoplay`, `loop`, `muted`, `preload`, `crossorigin`
    *
    * @example
    *   audio("controls" := "controls"): source("src" := "/sound.mp3", "type" := "audio/mpeg")
    */
  @nowarn
  def audio(
      id: String = null,
      `class`: String = null,
      src: String = null,
      controls: String = null,
      autoplay: String = null,
      loop: String = null,
      muted: String = null,
      preload: String = null,
      crossorigin: String = null,
      attrs: HtmlFn*
  )(nested: HtmlFn)(using html: Html): Unit =
    html.append("<audio")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("src", src)
    html.attrNotNull("controls", controls)
    html.attrNotNull("autoplay", autoplay)
    html.attrNotNull("loop", loop)
    html.attrNotNull("muted", muted)
    html.attrNotNull("preload", preload)
    html.attrNotNull("crossorigin", crossorigin)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</audio>")

  /** The `<video>` element: Video player.
    *
    * '''Common usage:''' Embed video content with native browser controls.
    *
    * '''Common attributes:''' `src`, `controls`, `width`, `height`, `poster`, `autoplay`, `loop`, `muted`, `preload`,
    * `playsinline`
    *
    * @example
    *   video("controls" := "controls", "width" := "640"): source("src" := "/clip.mp4", "type" := "video/mp4")
    */
  @nowarn
  def video(
      id: String = null,
      `class`: String = null,
      src: String = null,
      controls: String = null,
      width: String = null,
      height: String = null,
      poster: String = null,
      autoplay: String = null,
      loop: String = null,
      muted: String = null,
      preload: String = null,
      playsinline: String = null,
      attrs: HtmlFn*
  )(nested: HtmlFn)(using html: Html): Unit =
    html.append("<video")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("src", src)
    html.attrNotNull("controls", controls)
    html.attrNotNull("width", width)
    html.attrNotNull("height", height)
    html.attrNotNull("poster", poster)
    html.attrNotNull("autoplay", autoplay)
    html.attrNotNull("loop", loop)
    html.attrNotNull("muted", muted)
    html.attrNotNull("preload", preload)
    html.attrNotNull("playsinline", playsinline)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</video>")

  /** The `<track>` element: Media text track.
    *
    * '''Common usage:''' Provide captions, subtitles, or descriptions for `audio` or `video`.
    *
    * '''Common attributes:''' `src`, `kind`, `srclang`, `label`, `default`
    *
    * @example
    *   track("kind" := "captions", "src" := "/en.vtt", "srclang" := "en", "label" := "English")
    */
  @nowarn
  def track(
      id: String = null,
      `class`: String = null,
      src: String = null,
      kind: String = null,
      srclang: String = null,
      label: String = null,
      default: String = null,
      attrs: HtmlFn*
  )(nested: HtmlFn)(using html: Html): Unit =
    html.append("<track")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("src", src)
    html.attrNotNull("kind", kind)
    html.attrNotNull("srclang", srclang)
    html.attrNotNull("label", label)
    html.attrNotNull("default", default)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</track>")

  /** The `<iframe>` element: Inline frame.
    *
    * '''Common usage:''' Embed another HTML page such as a map, video, or widget.
    *
    * '''Common attributes:''' `src`, `name`, `width`, `height`, `sandbox`, `allow`, `loading`, `referrerpolicy`
    *
    * @example
    *   iframe("src" := "https://example.com/embed", "title" := "Embedded content")
    */
  @nowarn
  def iframe(
      id: String = null,
      `class`: String = null,
      src: String = null,
      name: String = null,
      width: String = null,
      height: String = null,
      sandbox: String = null,
      allow: String = null,
      loading: String = null,
      referrerpolicy: String = null,
      attrs: HtmlFn*
  )(nested: HtmlFn)(using html: Html): Unit =
    html.append("<iframe")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("src", src)
    html.attrNotNull("name", name)
    html.attrNotNull("width", width)
    html.attrNotNull("height", height)
    html.attrNotNull("sandbox", sandbox)
    html.attrNotNull("allow", allow)
    html.attrNotNull("loading", loading)
    html.attrNotNull("referrerpolicy", referrerpolicy)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</iframe>")

  /** The `<embed>` element: Embedded external content.
    *
    * '''Common usage:''' Embed external content such as plugins or media.
    *
    * '''Common attributes:''' `src`, `type`, `width`, `height`
    *
    * @example
    *   embed("src" := "/animation.swf", "type" := "application/x-shockwave-flash")
    */
  @nowarn
  def embed(id: String = null, `class`: String = null, src: String = null, `type`: String = null, width: String = null, height: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<embed")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("src", src)
    html.attrNotNull("type", `type`)
    html.attrNotNull("width", width)
    html.attrNotNull("height", height)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</embed>")

  /** The `<object>` element: External resource object.
    *
    * '''Common usage:''' Embed external resources such as images, documents, or applets.
    *
    * '''Common attributes:''' `data`, `type`, `width`, `height`, `name`, `form`
    *
    * @example
    *   `object`("data" := "/file.pdf", "type" := "application/pdf")
    */
  @nowarn
  def `object`(id: String = null, `class`: String = null, data: String = null, `type`: String = null, width: String = null, height: String = null, name: String = null, form: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<object")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("data", data)
    html.attrNotNull("type", `type`)
    html.attrNotNull("width", width)
    html.attrNotNull("height", height)
    html.attrNotNull("name", name)
    html.attrNotNull("form", form)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</object>")

  /** The `<param>` element: Object parameter.
    *
    * '''Common usage:''' Pass a parameter to a parent `object` element.
    *
    * '''Common attributes:''' `name`, `value`
    *
    * @example
    *   param("name" := "autoplay", "value" := "true")
    */
  @nowarn
  def param(id: String = null, `class`: String = null, name: String = null, value: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<param")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("name", name)
    html.attrNotNull("value", value)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</param>")

  /** The `<canvas>` element: Scripted drawing surface.
    *
    * '''Common usage:''' Provide a bitmap canvas for graphics drawn with JavaScript.
    *
    * '''Common attributes:''' `width`, `height`, `id`, `class`
    *
    * @example
    *   canvas("id" := "chart", "width" := "400", "height" := "200")
    */
  @nowarn
  def canvas(id: String = null, `class`: String = null, width: String = null, height: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<canvas")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("width", width)
    html.attrNotNull("height", height)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</canvas>")

  /** The `<svg>` element: Scalable vector graphics.
    *
    * '''Common usage:''' Embed inline SVG graphics directly in the document.
    *
    * '''Common attributes:''' `viewBox`, `width`, `height`, `xmlns`, `role`, `aria-label`
    *
    * @example
    *   svg("viewBox" := "0 0 24 24", "width" := "24", "height" := "24"): html"<circle cx='12' cy='12' r='10' />"
    */
  @nowarn
  def svg(id: String = null, `class`: String = null, viewBox: String = null, width: String = null, height: String = null, xmlns: String = null, role: String = null, ariaLabel: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<svg")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("viewBox", viewBox)
    html.attrNotNull("width", width)
    html.attrNotNull("height", height)
    html.attrNotNull("xmlns", xmlns)
    html.attrNotNull("role", role)
    html.attrNotNull("aria-label", ariaLabel)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</svg>")

  /** The `<ul>` element: Unordered list.
    *
    * '''Common usage:''' Render a bulleted list of items.
    *
    * '''Common attributes:''' `id`, `class`, `role`
    *
    * @example
    *   ul("class" := "list-disc"): li: text("First") li: text("Second")
    */
  @nowarn
  def ul(id: String = null, `class`: String = null, role: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<ul")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("role", role)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</ul>")

  /** The `<ol>` element: Ordered list.
    *
    * '''Common usage:''' Render a numbered list of items.
    *
    * '''Common attributes:''' `start`, `reversed`, `type`, `id`, `class`
    *
    * @example
    *   ol("start" := "1"): li: text("Step one") li: text("Step two")
    */
  @nowarn
  def ol(id: String = null, `class`: String = null, start: String = null, reversed: String = null, `type`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<ol")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("start", start)
    html.attrNotNull("reversed", reversed)
    html.attrNotNull("type", `type`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</ol>")

  /** The `<li>` element: List item.
    *
    * '''Common usage:''' Represent one entry in an `ul`, `ol`, or `menu`.
    *
    * '''Common attributes:''' `value`, `id`, `class`
    *
    * @example
    *   li("class" := "item"): text("List entry")
    */
  @nowarn
  def li(id: String = null, `class`: String = null, value: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<li")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("value", value)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</li>")

  /** The `<dl>` element: Description list.
    *
    * '''Common usage:''' Associate terms (`dt`) with descriptions (`dd`).
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   dl: dt: text("HTML") dd: text("HyperText Markup Language")
    */
  @nowarn
  def dl(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<dl")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</dl>")

  /** The `<dt>` element: Description term.
    *
    * '''Common usage:''' Name a term in a description list.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   dt: text("API")
    */
  @nowarn
  def dt(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<dt")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</dt>")

  /** The `<dd>` element: Description details.
    *
    * '''Common usage:''' Describe or define a term in a description list.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   dd: text("Application Programming Interface")
    */
  @nowarn
  def dd(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<dd")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</dd>")
    elem("dd")(attrs*)(nested)

  /** The `<table>` element: Tabular data.
    *
    * '''Common usage:''' Present rows and columns of data.
    *
    * '''Common attributes:''' `id`, `class`, `border`, `role`
    *
    * @example
    *   table("class" := "min-w-full"): thead: tr: th: text("Name") tbody: tr: td: text("Ada")
    */
  @nowarn
  def table(id: String = null, `class`: String = null, border: String = null, role: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<table")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("border", border)
    html.attrNotNull("role", role)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</table>")

  /** The `<caption>` element: Table caption.
    *
    * '''Common usage:''' Provide a title or explanation for a `table`.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   caption: text("Monthly totals")
    */
  @nowarn
  def caption(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<caption")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</caption>")

  /** The `<thead>` element: Table head.
    *
    * '''Common usage:''' Group header rows in a `table`.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   thead: tr: th: text("Column")
    */
  @nowarn
  def thead(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<thead")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</thead>")

  /** The `<tbody>` element: Table body.
    *
    * '''Common usage:''' Group the main data rows of a `table`.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   tbody: tr: td: text("Value")
    */
  @nowarn
  def tbody(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<tbody")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</tbody>")

  /** The `<tfoot>` element: Table footer.
    *
    * '''Common usage:''' Group summary rows at the bottom of a `table`.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   tfoot: tr: td: text("Total")
    */
  @nowarn
  def tfoot(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<tfoot")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</tfoot>")

  /** The `<tr>` element: Table row.
    *
    * '''Common usage:''' Represent one row of cells in a table.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   tr: td: text("Cell")
    */
  @nowarn
  def tr(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<tr")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</tr>")

  /** The `<th>` element: Table header cell.
    *
    * '''Common usage:''' Label a column or row in a table header.
    *
    * '''Common attributes:''' `scope`, `colspan`, `rowspan`, `headers`, `abbr`, `id`, `class`
    *
    * @example
    *   th("scope" := "col"): text("Price")
    */
  @nowarn
  def th(id: String = null, `class`: String = null, scope: String = null, colspan: String = null, rowspan: String = null, headers: String = null, abbr: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<th")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("scope", scope)
    html.attrNotNull("colspan", colspan)
    html.attrNotNull("rowspan", rowspan)
    html.attrNotNull("headers", headers)
    html.attrNotNull("abbr", abbr)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</th>")

  /** The `<td>` element: Table data cell.
    *
    * '''Common usage:''' Hold one data cell in a table row.
    *
    * '''Common attributes:''' `colspan`, `rowspan`, `headers`, `id`, `class`
    *
    * @example
    *   td("colspan" := "2"): text("Merged cell")
    */
  @nowarn
  def td(id: String = null, `class`: String = null, colspan: String = null, rowspan: String = null, headers: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<td")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("colspan", colspan)
    html.attrNotNull("rowspan", rowspan)
    html.attrNotNull("headers", headers)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</td>")

  /** The `<col>` element: Table column.
    *
    * '''Common usage:''' Apply column-wide properties inside a `colgroup`.
    *
    * '''Common attributes:''' `span`, `width`, `id`, `class`
    *
    * @example
    *   col("span" := "2", "class" := "numeric")
    */
  @nowarn
  def col(id: String = null, `class`: String = null, span: String = null, width: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<col")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("span", span)
    html.attrNotNull("width", width)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</col>")

  /** The `<colgroup>` element: Table column group.
    *
    * '''Common usage:''' Group and style one or more columns in a `table`.
    *
    * '''Common attributes:''' `span`, `id`, `class`
    *
    * @example
    *   colgroup("span" := "3"): col("class" := "wide")
    */
  @nowarn
  def colgroup(id: String = null, `class`: String = null, span: String = null, width: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<colgroup")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("span", span)
    html.attrNotNull("width", width)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</colgroup>")

  /** The `<form>` element: Form.
    *
    * '''Common usage:''' Collect and submit user input to a server or script.
    *
    * '''Common attributes:''' `action`, `method`, `enctype`, `name`, `novalidate`, `autocomplete`, `target`
    *
    * @example
    *   form("action" := "/login", "method" := "post"): input("type" := "text", "name" := "email")
    */
  @nowarn
  def form(id: String = null, `class`: String = null, action: String = null, method: String = null, enctype: String = null, name: String = null, novalidate: String = null, autocomplete: String = null, target: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<form")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("action", action)
    html.attrNotNull("method", method)
    html.attrNotNull("enctype", enctype)
    html.attrNotNull("name", name)
    html.attrNotNull("novalidate", novalidate)
    html.attrNotNull("autocomplete", autocomplete)
    html.attrNotNull("target", target)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</form>")

  /** The `<label>` element: Form label.
    *
    * '''Common usage:''' Describe a form control; associate with `for` or by wrapping the control.
    *
    * '''Common attributes:''' `for`, `form`, `id`, `class`
    *
    * @example
    *   label("for" := "email"): text("Email")
    */
  @nowarn
  def label(id: String = null, `class`: String = null, `for`: String = null, form: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<label")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("for", `for`)
    html.attrNotNull("form", form)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</label>")

  /** The `<input>` element: Form input control.
    *
    * '''Common usage:''' Collect text, choices, files, or buttons depending on `type`.
    *
    * '''Common attributes:''' `type`, `name`, `value`, `placeholder`, `required`, `disabled`, `checked`, `min`, `max`,
    * `step`, `pattern`, `autocomplete`
    *
    * @example
    *   input("type" := "email", "name" := "email", "required" := "required")
    */
  @nowarn
  def input(id: String = null, `class`: String = null, `type`: String = null, name: String = null, value: String = null, placeholder: String = null, required: String = null, disabled: String = null, checked: String = null, min: String = null, max: String = null, step: String = null, pattern: String = null, autocomplete: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<input")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("type", `type`)
    html.attrNotNull("name", name)
    html.attrNotNull("value", value)
    html.attrNotNull("placeholder", placeholder)
    html.attrNotNull("required", required)
    html.attrNotNull("disabled", disabled)
    html.attrNotNull("checked", checked)
    html.attrNotNull("min", min)
    html.attrNotNull("max", max)
    html.attrNotNull("step", step)
    html.attrNotNull("pattern", pattern)
    html.attrNotNull("autocomplete", autocomplete)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</input>")

  /** The `<textarea>` element: Multiline text input.
    *
    * '''Common usage:''' Collect longer free-form text from users.
    *
    * '''Common attributes:''' `name`, `rows`, `cols`, `placeholder`, `required`, `disabled`, `maxlength`, `wrap`
    *
    * @example
    *   textarea("name" := "message", "rows" := "4"): text("Hello")
    */
  @nowarn
  def textarea(id: String = null, `class`: String = null, name: String = null, rows: String = null, cols: String = null, placeholder: String = null, required: String = null, disabled: String = null, maxlength: String = null, wrap: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<textarea")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("name", name)
    html.attrNotNull("rows", rows)
    html.attrNotNull("cols", cols)
    html.attrNotNull("placeholder", placeholder)
    html.attrNotNull("required", required)
    html.attrNotNull("disabled", disabled)
    html.attrNotNull("maxlength", maxlength)
    html.attrNotNull("wrap", wrap)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</textarea>")

  /** The `<button>` element: Button control.
    *
    * '''Common usage:''' Trigger an action in a form or via scripting.
    *
    * '''Common attributes:''' `type`, `name`, `value`, `disabled`, `form`, `formaction`, `autofocus`
    *
    * @example
    *   button("type" := "submit", "class" := "btn"): text("Save")
    */
  @nowarn
  def button(id: String = null, `class`: String = null, `type`: String = null, name: String = null, value: String = null, disabled: String = null, form: String = null, formaction: String = null, autofocus: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<button")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("type", `type`)
    html.attrNotNull("name", name)
    html.attrNotNull("value", value)
    html.attrNotNull("disabled", disabled)
    html.attrNotNull("form", form)
    html.attrNotNull("formaction", formaction)
    html.attrNotNull("autofocus", autofocus)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</button>")

  /** The `<select>` element: Option list.
    *
    * '''Common usage:''' Present a dropdown list of options.
    *
    * '''Common attributes:''' `name`, `multiple`, `required`, `disabled`, `size`, `autocomplete`
    *
    * @example
    *   select("name" := "plan"): option("value" := "free"): text("Free") option("value" := "pro", "selected" :=
    *   "selected"): text("Pro")
    */
  @nowarn
  def select(id: String = null, `class`: String = null, name: String = null, multiple: String = null, required: String = null, disabled: String = null, size: String = null, autocomplete: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<select")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("name", name)
    html.attrNotNull("multiple", multiple)
    html.attrNotNull("required", required)
    html.attrNotNull("disabled", disabled)
    html.attrNotNull("size", size)
    html.attrNotNull("autocomplete", autocomplete)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</select>")

  /** The `<option>` element: Select option.
    *
    * '''Common usage:''' Define one selectable item in a `select` or `datalist`.
    *
    * '''Common attributes:''' `value`, `label`, `selected`, `disabled`
    *
    * @example
    *   option("value" := "ca"): text("Canada")
    */
  @nowarn
  def option(id: String = null, `class`: String = null, value: String = null, label: String = null, selected: String = null, disabled: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<option")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("value", value)
    html.attrNotNull("label", label)
    html.attrNotNull("selected", selected)
    html.attrNotNull("disabled", disabled)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</option>")

  /** The `<optgroup>` element: Grouped options.
    *
    * '''Common usage:''' Group related `option` elements inside a `select`.
    *
    * '''Common attributes:''' `label`, `disabled`
    *
    * @example
    *   optgroup("label" := "North America"): option("value" := "ca"): text("Canada")
    */
  @nowarn
  def optgroup(id: String = null, `class`: String = null, label: String = null, disabled: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<optgroup")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("label", label)
    html.attrNotNull("disabled", disabled)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</optgroup>")

  /** The `<fieldset>` element: Form field group.
    *
    * '''Common usage:''' Group related controls and labels in a form.
    *
    * '''Common attributes:''' `disabled`, `name`, `form`, `id`, `class`
    *
    * @example
    *   fieldset: legend: text("Shipping") input("type" := "text", "name" := "address")
    */
  @nowarn
  def fieldset(id: String = null, `class`: String = null, disabled: String = null, name: String = null, form: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<fieldset")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("disabled", disabled)
    html.attrNotNull("name", name)
    html.attrNotNull("form", form)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</fieldset>")

  /** The `<legend>` element: Fieldset caption.
    *
    * '''Common usage:''' Provide a caption for a `fieldset`.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   legend: text("Account details")
    */
  @nowarn
  def legend(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<legend")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</legend>")
    elem("legend")(attrs*)(nested)

  /** The `<datalist>` element: Input suggestions.
    *
    * '''Common usage:''' Offer predefined options for an associated `input`.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   datalist("id" := "browsers"): option("value" := "Chrome") option("value" := "Firefox")
    */
  @nowarn
  def datalist(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<datalist")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</datalist>")

  /** The `<output>` element: Calculation result.
    *
    * '''Common usage:''' Display the result of a calculation or user action.
    *
    * '''Common attributes:''' `for`, `name`, `form`, `id`, `class`
    *
    * @example
    *   output("name" := "total", "for" := "qty price"): text("0")
    */
  @nowarn
  def output(id: String = null, `class`: String = null, `for`: String = null, name: String = null, form: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<output")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("for", `for`)
    html.attrNotNull("name", name)
    html.attrNotNull("form", form)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</output>")

  /** The `<progress>` element: Task progress.
    *
    * '''Common usage:''' Show completion progress for a task.
    *
    * '''Common attributes:''' `value`, `max`, `id`, `class`
    *
    * @example
    *   progress("value" := "70", "max" := "100")
    */
  @nowarn
  def progress(id: String = null, `class`: String = null, value: String = null, max: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<progress")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("value", value)
    html.attrNotNull("max", max)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</progress>")

  /** The `<meter>` element: Scalar measurement.
    *
    * '''Common usage:''' Display a scalar value within a known range.
    *
    * '''Common attributes:''' `value`, `min`, `max`, `low`, `high`, `optimum`, `id`, `class`
    *
    * @example
    *   meter("value" := "0.6", "min" := "0", "max" := "1")
    */
  @nowarn
  def meter(id: String = null, `class`: String = null, value: String = null, min: String = null, max: String = null, low: String = null, high: String = null, optimum: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<meter")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("value", value)
    html.attrNotNull("min", min)
    html.attrNotNull("max", max)
    html.attrNotNull("low", low)
    html.attrNotNull("high", high)
    html.attrNotNull("optimum", optimum)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</meter>")

  /** The `<details>` element: Disclosure widget.
    *
    * '''Common usage:''' Create an expandable section toggled by a `summary`.
    *
    * '''Common attributes:''' `open`, `name`, `id`, `class`
    *
    * @example
    *   details: summary: text("More info") p: text("Hidden details.")
    */
  @nowarn
  def details(id: String = null, `class`: String = null, open: String = null, name: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<details")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("open", open)
    html.attrNotNull("name", name)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</details>")

  /** The `<summary>` element: Details summary.
    *
    * '''Common usage:''' Provide the visible label for a `details` element.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   summary: text("Click to expand")
    */
  @nowarn
  def summary(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<summary")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</summary>")

  /** The `<dialog>` element: Modal or non-modal dialog.
    *
    * '''Common usage:''' Represent a dialog box or other interactive component.
    *
    * '''Common attributes:''' `open`, `id`, `class`
    *
    * @example
    *   dialog("id" := "confirm"): p: text("Are you sure?") button("type" := "button"): text("Close")
    */
  @nowarn
  def dialog(id: String = null, `class`: String = null, open: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<dialog")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("open", open)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</dialog>")

  /** The `<template>` element: Inert template content.
    *
    * '''Common usage:''' Hold HTML fragments that are not rendered until cloned by script.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   template("id" := "row-template"): tr: td: text("Placeholder")
    */
  @nowarn
  def template(id: String = null, `class`: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<template")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</template>")

  /** The `<menu>` element: Toolbar or context menu.
    *
    * '''Common usage:''' Present a list of commands or actions.
    *
    * '''Common attributes:''' `type`, `label`, `id`, `class`
    *
    * @example
    *   menu("type" := "toolbar"): button("type" := "button"): text("Copy")
    */
  @nowarn
  def menu(id: String = null, `class`: String = null, `type`: String = null, label: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<menu")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("type", `type`)
    html.attrNotNull("label", label)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</menu>")

  /** The `<slot>` element: Shadow DOM slot.
    *
    * '''Common usage:''' Define a placeholder in a web component shadow tree.
    *
    * '''Common attributes:''' `name`, `id`, `class`
    *
    * @example
    *   slot("name" := "title"): text("Default title")
    */
  @nowarn
  def slot(id: String = null, `class`: String = null, name: String = null, attrs: HtmlFn*)(nested: HtmlFn)(using html: Html): Unit =
    html.append("<slot")
    html.attrNotNull("id", id)
    html.attrNotNull("class", `class`)
    html.attrNotNull("name", name)
    html.foreach(attrs)
    html.append('>')
    nested.apply
    html.append("</slot>")

end HtmlElements
