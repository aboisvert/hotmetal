package hotmetal

import Html.*

/** HtmlElements provides a set of inline methods for each HTML element.
  *
  * These methods are used to generate HTML elements in a more functional style.
  * Each method accepts attribute fragments and nested content that append into the
  * current `Html` context.
  *
  * @example
  *   Html:
  *     head():
  *       title("class" := "page-title"):
  *         text("My Page")
  */
object HtmlElements:

  /** The `<head>` element: Document metadata container.
    *
    * '''Common usage:''' Wrap `title`, `meta`, `link`, `style`, and `script` elements inside `html`.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   head():
    *     meta("charset" := "utf-8")
    *     title():
    *       text("My Page")
    */
  inline def head(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("head")(attrs*)(nested)

  /** The `<title>` element: Document title.
    *
    * '''Common usage:''' Set the page title shown in the browser tab and search results.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   title:
    *     text("Dashboard")
    */
  inline def title(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("title")(attrs*)(nested)

  /** The `<meta>` element: Metadata.
    *
    * '''Common usage:''' Declare charset, viewport, description, and other document or Open Graph metadata.
    *
    * '''Common attributes:''' `charset`, `name`, `content`, `http-equiv`, `property`, `media`
    *
    * @example
    *   meta("charset" := "utf-8")
    *   meta("name" := "viewport", "content" := "width=device-width, initial-scale=1")
    */
  inline def meta(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("meta")(attrs*)(nested)

  /** The `<link>` element: External resource link.
    *
    * '''Common usage:''' Load stylesheets, icons, manifests, or prefetch resources.
    *
    * '''Common attributes:''' `rel`, `href`, `type`, `media`, `sizes`, `crossorigin`, `as`
    *
    * @example
    *   link("rel" := "stylesheet", "href" := "/app.css")
    */
  inline def link(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("link")(attrs*)(nested)

  /** The `<style>` element: Embedded CSS.
    *
    * '''Common usage:''' Inline stylesheet rules inside the document.
    *
    * '''Common attributes:''' `media`, `type`, `nonce`, `id`, `class`
    *
    * @example
    *   style:
    *     html".card { padding: 1rem; }"
    */
  inline def style(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("style")(attrs*)(nested)

  /** The `<script>` element: Executable script.
    *
    * '''Common usage:''' Embed or reference JavaScript executed by the browser.
    *
    * '''Common attributes:''' `src`, `type`, `async`, `defer`, `nomodule`, `crossorigin`, `integrity`, `nonce`
    *
    * @example
    *   script("src" := "/app.js", "defer" := "defer")
    */
  inline def script(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("script")(attrs*)(nested)

  /** The `<noscript>` element: No-script fallback.
    *
    * '''Common usage:''' Provide alternate content when scripting is disabled.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   noscript:
    *     p:
    *       text("Please enable JavaScript.")
    */
  inline def noscript(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("noscript")(attrs*)(nested)

  /** The `<base>` element: Base URL.
    *
    * '''Common usage:''' Set the default URL for relative links and form actions in the document.
    *
    * '''Common attributes:''' `href`, `target`
    *
    * @example
    *   base("href" := "https://example.com/")
    */
  inline def base(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("base")(attrs*)(nested)

  /** The `<body>` element: Document body.
    *
    * '''Common usage:''' Contain all visible page content rendered in the browser.
    *
    * '''Common attributes:''' `id`, `class`, `style`
    *
    * @example
    *   body("class" := "min-h-screen"):
    *     main:
    *       text("Hello")
    */
  inline def body(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("body")(attrs*)(nested)

  /** The `<header>` element: Page or section header.
    *
    * '''Common usage:''' Introduce a page, article, or section with branding, titles, or toolbars.
    *
    * '''Common attributes:''' `id`, `class`, `role`
    *
    * @example
    *   header("class" := "site-header"):
    *     h1:
    *       text("Hotmetal")
    */
  inline def header(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("header")(attrs*)(nested)

  /** The `<nav>` element: Navigation links.
    *
    * '''Common usage:''' Group primary or secondary navigation links.
    *
    * '''Common attributes:''' `id`, `class`, `aria-label`
    *
    * @example
    *   nav("aria-label" := "Main"):
    *     a("href" := "/"):
    *       text("Home")
    */
  inline def nav(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("nav")(attrs*)(nested)

  /** The `<main>` element: Main content.
    *
    * '''Common usage:''' Identify the dominant content of the document; use once per page.
    *
    * '''Common attributes:''' `id`, `class`, `role`
    *
    * @example
    *   main("id" := "content"):
    *     p:
    *       text("Primary page content.")
    */
  inline def main(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("main")(attrs*)(nested)

  /** The `<section>` element: Thematic grouping.
    *
    * '''Common usage:''' Organize related content with an optional heading.
    *
    * '''Common attributes:''' `id`, `class`, `aria-labelledby`
    *
    * @example
    *   section("id" := "features"):
    *     h2:
    *       text("Features")
    */
  inline def section(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("section")(attrs*)(nested)

  /** The `<article>` element: Self-contained composition.
    *
    * '''Common usage:''' Mark up blog posts, comments, cards, or news items that can stand alone.
    *
    * '''Common attributes:''' `id`, `class`, `itemscope`, `itemtype`
    *
    * @example
    *   article("class" := "post"):
    *     h2:
    *       text("Release notes")
    */
  inline def article(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("article")(attrs*)(nested)

  /** The `<aside>` element: Sidebar or tangential content.
    *
    * '''Common usage:''' Hold related notes, ads, or navigation complementary to main content.
    *
    * '''Common attributes:''' `id`, `class`, `aria-label`
    *
    * @example
    *   aside("class" := "sidebar"):
    *     nav:
    *       text("Related links")
    */
  inline def aside(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("aside")(attrs*)(nested)

  /** The `<footer>` element: Footer for page or section.
    *
    * '''Common usage:''' Display copyright, links, or metadata at the bottom of a page or section.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   footer("class" := "site-footer"):
    *     p:
    *       text("© 2026 Example")
    */
  inline def footer(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("footer")(attrs*)(nested)

  /** The `<address>` element: Contact information.
    *
    * '''Common usage:''' Mark up contact details for a person or organization.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   address:
    *     text("Contact us at support@example.com")
    */
  inline def address(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("address")(attrs*)(nested)

  /** The `<h1>` element: Heading level 1.
    *
    * '''Common usage:''' Top-level document or page heading; typically one per page.
    *
    * '''Common attributes:''' `id`, `class`, `title`
    *
    * @example
    *   h1("id" := "page-title"):
    *     text("Welcome")
    */
  inline def h1(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("h1")(attrs*)(nested)

  /** The `<h2>` element: Heading level 2.
    *
    * '''Common usage:''' Major section heading beneath an `h1`.
    *
    * '''Common attributes:''' `id`, `class`, `title`
    *
    * @example
    *   h2:
    *     text("Overview")
    */
  inline def h2(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("h2")(attrs*)(nested)

  /** The `<h3>` element: Heading level 3.
    *
    * '''Common usage:''' Subsection heading beneath an `h2`.
    *
    * '''Common attributes:''' `id`, `class`, `title`
    *
    * @example
    *   h3:
    *     text("Details")
    */
  inline def h3(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("h3")(attrs*)(nested)

  /** The `<h4>` element: Heading level 4.
    *
    * '''Common usage:''' Nested subsection heading beneath an `h3`.
    *
    * '''Common attributes:''' `id`, `class`, `title`
    *
    * @example
    *   h4:
    *     text("Subsection")
    */
  inline def h4(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("h4")(attrs*)(nested)

  /** The `<h5>` element: Heading level 5.
    *
    * '''Common usage:''' Minor heading beneath an `h4`.
    *
    * '''Common attributes:''' `id`, `class`, `title`
    *
    * @example
    *   h5:
    *     text("Note")
    */
  inline def h5(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("h5")(attrs*)(nested)

  /** The `<h6>` element: Heading level 6.
    *
    * '''Common usage:''' Lowest-level heading beneath an `h5`.
    *
    * '''Common attributes:''' `id`, `class`, `title`
    *
    * @example
    *   h6:
    *     text("Fine print")
    */
  inline def h6(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("h6")(attrs*)(nested)

  /** The `<p>` element: Paragraph.
    *
    * '''Common usage:''' Render a block of text content.
    *
    * '''Common attributes:''' `id`, `class`, `lang`, `dir`
    *
    * @example
    *   p("class" := "lead"):
    *     text("A short introduction.")
    */
  inline def p(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("p")(attrs*)(nested)

  /** The `<a>` element: Hyperlink.
    *
    * '''Common usage:''' Link to another page, fragment, file, or email address.
    *
    * '''Common attributes:''' `href`, `target`, `rel`, `download`, `hreflang`, `type`, `referrerpolicy`
    *
    * @example
    *   a("href" := "/docs", "class" := "link"):
    *     text("Documentation")
    */
  inline def a(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("a")(attrs*)(nested)

  /** The `<span>` element: Generic inline container.
    *
    * '''Common usage:''' Wrap inline text or phrasing content for styling or scripting hooks.
    *
    * '''Common attributes:''' `id`, `class`, `lang`, `title`
    *
    * @example
    *   span("class" := "badge"):
    *     text("New")
    */
  inline def span(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("span")(attrs*)(nested)

  /** The `<div>` element: Generic block container.
    *
    * '''Common usage:''' Group block-level content for layout, styling, or behavior.
    *
    * '''Common attributes:''' `id`, `class`, `role`, `tabindex`
    *
    * @example
    *   div("class" := "card"):
    *     p:
    *       text("Card body")
    */
  inline def div(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("div")(attrs*)(nested)

  /** The `<br>` element: Line break.
    *
    * '''Common usage:''' Insert a line break within text content.
    *
    * '''Common attributes:''' `id`, `class`, `clear`
    *
    * @example
    *   p:
    *     text("Line one")
    *     br()
    *     text("Line two")
    */
  inline def br(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("br")(attrs*)(nested)

  /** The `<hr>` element: Thematic break.
    *
    * '''Common usage:''' Separate sections with a horizontal rule.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   section:
    *     p:
    *       text("Part one")
    *     hr()
    *     p:
    *       text("Part two")
    */
  inline def hr(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("hr")(attrs*)(nested)

  /** The `<strong>` element: Strong importance.
    *
    * '''Common usage:''' Indicate strongly important text; typically rendered bold.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p:
    *     text("Please ")
    *     strong:
    *       text("save")
    *     text(" your work.")
    */
  inline def strong(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("strong")(attrs*)(nested)

  /** The `<em>` element: Emphasis.
    *
    * '''Common usage:''' Stress emphasis in text; typically rendered italic.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p:
    *     text("This is ")
    *     em:
    *       text("important")
    *     text(".")
    */
  inline def em(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("em")(attrs*)(nested)

  /** The `<b>` element: Bold text (presentational).
    *
    * '''Common usage:''' Draw attention without implying extra importance; prefer `strong` for semantics.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p:
    *     b:
    *       text("Bold")
    *     text(" label")
    */
  inline def b(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("b")(attrs*)(nested)

  /** The `<i>` element: Italic text (presentational).
    *
    * '''Common usage:''' Offset text such as technical terms; prefer `em` for emphasis.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p:
    *     i:
    *       text("italic")
    *     text(" styling")
    */
  inline def i(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("i")(attrs*)(nested)

  /** The `<u>` element: Unarticulated annotation.
    *
    * '''Common usage:''' Mark text with a non-textual annotation such as a spelling error.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p:
    *     u:
    *       text("annotated")
    */
  inline def u(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("u")(attrs*)(nested)

  /** The `<small>` element: Side comment or fine print.
    *
    * '''Common usage:''' Represent disclaimers, copyright, or secondary information.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   small:
    *     text("Prices may vary.")
    */
  inline def small(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("small")(attrs*)(nested)

  /** The `<mark>` element: Highlighted text.
    *
    * '''Common usage:''' Highlight text referenced in another context, such as search results.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p:
    *     text("Find the ")
    *     mark:
    *       text("keyword")
    *     text(" here.")
    */
  inline def mark(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("mark")(attrs*)(nested)

  /** The `<sub>` element: Subscript.
    *
    * '''Common usage:''' Render subscript text such as chemical formulas.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p:
    *     text("H")
    *     sub:
    *       text("2")
    *     text("O")
    */
  inline def sub(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("sub")(attrs*)(nested)

  /** The `<sup>` element: Superscript.
    *
    * '''Common usage:''' Render superscript text such as footnote markers or exponents.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p:
    *     text("x")
    *     sup:
    *       text("2")
    */
  inline def sup(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("sup")(attrs*)(nested)

  /** The `<code>` element: Inline code.
    *
    * '''Common usage:''' Mark a fragment of computer code within a sentence.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p:
    *     text("Use ")
    *     code:
    *       text("Html.text")
    *     text(" for escaping.")
    */
  inline def code(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("code")(attrs*)(nested)

  /** The `<pre>` element: Preformatted text.
    *
    * '''Common usage:''' Display text that preserves whitespace, often paired with `code`.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   pre:
    *     code:
    *       text("val x = 1")
    */
  inline def pre(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("pre")(attrs*)(nested)

  /** The `<kbd>` element: Keyboard input.
    *
    * '''Common usage:''' Represent user keyboard input.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p:
    *     text("Press ")
    *     kbd:
    *       text("Ctrl")
    *     text("+")
    *     kbd:
    *       text("S")
    *     text(" to save.")
    */
  inline def kbd(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("kbd")(attrs*)(nested)

  /** The `<samp>` element: Sample output.
    *
    * '''Common usage:''' Represent sample or program output.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p:
    *     text("Output: ")
    *     samp:
    *       text("OK")
    */
  inline def samp(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("samp")(attrs*)(nested)

  /** The `<var>` element: Variable.
    *
    * '''Common usage:''' Mark the name of a variable in mathematical or programming notation.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p:
    *     text("Let ")
    *     `var`:
    *       text("n")
    *     text(" be the count.")
    */
  inline def `var`(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("var")(attrs*)(nested)

  /** The `<blockquote>` element: Block quotation.
    *
    * '''Common usage:''' Quote content from another source.
    *
    * '''Common attributes:''' `cite`, `id`, `class`
    *
    * @example
    *   blockquote("cite" := "https://example.com"):
    *     p:
    *       text("A quoted passage.")
    */
  inline def blockquote(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("blockquote")(attrs*)(nested)

  /** The `<q>` element: Inline quotation.
    *
    * '''Common usage:''' Quote short text inline.
    *
    * '''Common attributes:''' `cite`, `id`, `class`
    *
    * @example
    *   p:
    *     text("As they said, ")
    *     q("cite" := "https://example.com"):
    *       text("hello")
    *     text(".")
    */
  inline def q(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("q")(attrs*)(nested)

  /** The `<cite>` element: Citation.
    *
    * '''Common usage:''' Reference the title of a creative work.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   p:
    *     cite:
    *       text("The Pragmatic Programmer")
    */
  inline def cite(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("cite")(attrs*)(nested)

  /** The `<abbr>` element: Abbreviation.
    *
    * '''Common usage:''' Expand or annotate an abbreviation or acronym.
    *
    * '''Common attributes:''' `title`, `id`, `class`
    *
    * @example
    *   abbr("title" := "HyperText Markup Language"):
    *     text("HTML")
    */
  inline def abbr(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("abbr")(attrs*)(nested)

  /** The `<time>` element: Machine-readable datetime.
    *
    * '''Common usage:''' Represent dates, times, or durations with an optional `datetime` attribute.
    *
    * '''Common attributes:''' `datetime`, `id`, `class`
    *
    * @example
    *   time("datetime" := "2026-05-18"):
    *     text("May 18, 2026")
    */
  inline def time(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("time")(attrs*)(nested)

  /** The `<img>` element: Image.
    *
    * '''Common usage:''' Embed an image into the document.
    *
    * '''Common attributes:''' `src`, `alt`, `width`, `height`, `loading`, `decoding`, `srcset`, `sizes`, `crossorigin`
    *
    * @example
    *   img("src" := "/logo.svg", "alt" := "Logo")
    */
  inline def img(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("img")(attrs*)(nested)

  /** The `<picture>` element: Responsive image container.
    *
    * '''Common usage:''' Offer multiple image sources for different viewports or formats.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   picture:
    *     source("media" := "(min-width: 800px)", "srcset" := "large.jpg")
    *     img("src" := "small.jpg", "alt" := "Hero")
    */
  inline def picture(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("picture")(attrs*)(nested)

  /** The `<source>` element: Media source.
    *
    * '''Common usage:''' Specify alternative media for `picture`, `audio`, or `video` elements.
    *
    * '''Common attributes:''' `src`, `srcset`, `type`, `media`, `sizes`
    *
    * @example
    *   source("src" := "clip.webm", "type" := "video/webm")
    */
  inline def source(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("source")(attrs*)(nested)

  /** The `<figure>` element: Illustration with optional caption.
    *
    * '''Common usage:''' Group self-contained media such as images, diagrams, or code listings.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   figure:
    *     img("src" := "/chart.png", "alt" := "Chart")
    *     figcaption:
    *       text("Quarterly growth")
    */
  inline def figure(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("figure")(attrs*)(nested)

  /** The `<figcaption>` element: Figure caption.
    *
    * '''Common usage:''' Provide a caption or legend for a `figure`.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   figcaption:
    *     text("Figure 1: Architecture")
    */
  inline def figcaption(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("figcaption")(attrs*)(nested)

  /** The `<audio>` element: Audio player.
    *
    * '''Common usage:''' Embed sound content with native browser controls.
    *
    * '''Common attributes:''' `src`, `controls`, `autoplay`, `loop`, `muted`, `preload`, `crossorigin`
    *
    * @example
    *   audio("controls" := "controls"):
    *     source("src" := "/sound.mp3", "type" := "audio/mpeg")
    */
  inline def audio(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("audio")(attrs*)(nested)

  /** The `<video>` element: Video player.
    *
    * '''Common usage:''' Embed video content with native browser controls.
    *
    * '''Common attributes:''' `src`, `controls`, `width`, `height`, `poster`, `autoplay`, `loop`, `muted`, `preload`, `playsinline`
    *
    * @example
    *   video("controls" := "controls", "width" := "640"):
    *     source("src" := "/clip.mp4", "type" := "video/mp4")
    */
  inline def video(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("video")(attrs*)(nested)

  /** The `<track>` element: Media text track.
    *
    * '''Common usage:''' Provide captions, subtitles, or descriptions for `audio` or `video`.
    *
    * '''Common attributes:''' `src`, `kind`, `srclang`, `label`, `default`
    *
    * @example
    *   track("kind" := "captions", "src" := "/en.vtt", "srclang" := "en", "label" := "English")
    */
  inline def track(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("track")(attrs*)(nested)

  /** The `<iframe>` element: Inline frame.
    *
    * '''Common usage:''' Embed another HTML page such as a map, video, or widget.
    *
    * '''Common attributes:''' `src`, `name`, `width`, `height`, `sandbox`, `allow`, `loading`, `referrerpolicy`
    *
    * @example
    *   iframe("src" := "https://example.com/embed", "title" := "Embedded content")
    */
  inline def iframe(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("iframe")(attrs*)(nested)

  /** The `<embed>` element: Embedded external content.
    *
    * '''Common usage:''' Embed external content such as plugins or media.
    *
    * '''Common attributes:''' `src`, `type`, `width`, `height`
    *
    * @example
    *   embed("src" := "/animation.swf", "type" := "application/x-shockwave-flash")
    */
  inline def embed(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("embed")(attrs*)(nested)

  /** The `<object>` element: External resource object.
    *
    * '''Common usage:''' Embed external resources such as images, documents, or applets.
    *
    * '''Common attributes:''' `data`, `type`, `width`, `height`, `name`, `form`
    *
    * @example
    *   `object`("data" := "/file.pdf", "type" := "application/pdf")
    */
  inline def `object`(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("object")(attrs*)(nested)

  /** The `<param>` element: Object parameter.
    *
    * '''Common usage:''' Pass a parameter to a parent `object` element.
    *
    * '''Common attributes:''' `name`, `value`
    *
    * @example
    *   param("name" := "autoplay", "value" := "true")
    */
  inline def param(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("param")(attrs*)(nested)

  /** The `<canvas>` element: Scripted drawing surface.
    *
    * '''Common usage:''' Provide a bitmap canvas for graphics drawn with JavaScript.
    *
    * '''Common attributes:''' `width`, `height`, `id`, `class`
    *
    * @example
    *   canvas("id" := "chart", "width" := "400", "height" := "200")
    */
  inline def canvas(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("canvas")(attrs*)(nested)

  /** The `<svg>` element: Scalable vector graphics.
    *
    * '''Common usage:''' Embed inline SVG graphics directly in the document.
    *
    * '''Common attributes:''' `viewBox`, `width`, `height`, `xmlns`, `role`, `aria-label`
    *
    * @example
    *   svg("viewBox" := "0 0 24 24", "width" := "24", "height" := "24"):
    *     html"<circle cx='12' cy='12' r='10' />"
    */
  inline def svg(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("svg")(attrs*)(nested)

  /** The `<ul>` element: Unordered list.
    *
    * '''Common usage:''' Render a bulleted list of items.
    *
    * '''Common attributes:''' `id`, `class`, `role`
    *
    * @example
    *   ul("class" := "list-disc"):
    *     li:
    *       text("First")
    *     li:
    *       text("Second")
    */
  inline def ul(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("ul")(attrs*)(nested)

  /** The `<ol>` element: Ordered list.
    *
    * '''Common usage:''' Render a numbered list of items.
    *
    * '''Common attributes:''' `start`, `reversed`, `type`, `id`, `class`
    *
    * @example
    *   ol("start" := "1"):
    *     li:
    *       text("Step one")
    *     li:
    *       text("Step two")
    */
  inline def ol(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("ol")(attrs*)(nested)

  /** The `<li>` element: List item.
    *
    * '''Common usage:''' Represent one entry in an `ul`, `ol`, or `menu`.
    *
    * '''Common attributes:''' `value`, `id`, `class`
    *
    * @example
    *   li("class" := "item"):
    *     text("List entry")
    */
  inline def li(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("li")(attrs*)(nested)

  /** The `<dl>` element: Description list.
    *
    * '''Common usage:''' Associate terms (`dt`) with descriptions (`dd`).
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   dl:
    *     dt:
    *       text("HTML")
    *     dd:
    *       text("HyperText Markup Language")
    */
  inline def dl(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("dl")(attrs*)(nested)

  /** The `<dt>` element: Description term.
    *
    * '''Common usage:''' Name a term in a description list.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   dt:
    *     text("API")
    */
  inline def dt(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("dt")(attrs*)(nested)

  /** The `<dd>` element: Description details.
    *
    * '''Common usage:''' Describe or define a term in a description list.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   dd:
    *     text("Application Programming Interface")
    */
  inline def dd(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("dd")(attrs*)(nested)

  /** The `<table>` element: Tabular data.
    *
    * '''Common usage:''' Present rows and columns of data.
    *
    * '''Common attributes:''' `id`, `class`, `border`, `role`
    *
    * @example
    *   table("class" := "min-w-full"):
    *     thead:
    *       tr:
    *         th:
    *           text("Name")
    *     tbody:
    *       tr:
    *         td:
    *           text("Ada")
    */
  inline def table(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("table")(attrs*)(nested)

  /** The `<caption>` element: Table caption.
    *
    * '''Common usage:''' Provide a title or explanation for a `table`.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   caption:
    *     text("Monthly totals")
    */
  inline def caption(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("caption")(attrs*)(nested)

  /** The `<thead>` element: Table head.
    *
    * '''Common usage:''' Group header rows in a `table`.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   thead:
    *     tr:
    *       th:
    *         text("Column")
    */
  inline def thead(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("thead")(attrs*)(nested)

  /** The `<tbody>` element: Table body.
    *
    * '''Common usage:''' Group the main data rows of a `table`.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   tbody:
    *     tr:
    *       td:
    *         text("Value")
    */
  inline def tbody(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("tbody")(attrs*)(nested)

  /** The `<tfoot>` element: Table footer.
    *
    * '''Common usage:''' Group summary rows at the bottom of a `table`.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   tfoot:
    *     tr:
    *       td:
    *         text("Total")
    */
  inline def tfoot(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("tfoot")(attrs*)(nested)

  /** The `<tr>` element: Table row.
    *
    * '''Common usage:''' Represent one row of cells in a table.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   tr:
    *     td:
    *       text("Cell")
    */
  inline def tr(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("tr")(attrs*)(nested)

  /** The `<th>` element: Table header cell.
    *
    * '''Common usage:''' Label a column or row in a table header.
    *
    * '''Common attributes:''' `scope`, `colspan`, `rowspan`, `headers`, `abbr`, `id`, `class`
    *
    * @example
    *   th("scope" := "col"):
    *     text("Price")
    */
  inline def th(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("th")(attrs*)(nested)

  /** The `<td>` element: Table data cell.
    *
    * '''Common usage:''' Hold one data cell in a table row.
    *
    * '''Common attributes:''' `colspan`, `rowspan`, `headers`, `id`, `class`
    *
    * @example
    *   td("colspan" := "2"):
    *     text("Merged cell")
    */
  inline def td(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("td")(attrs*)(nested)

  /** The `<col>` element: Table column.
    *
    * '''Common usage:''' Apply column-wide properties inside a `colgroup`.
    *
    * '''Common attributes:''' `span`, `width`, `id`, `class`
    *
    * @example
    *   col("span" := "2", "class" := "numeric")
    */
  inline def col(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("col")(attrs*)(nested)

  /** The `<colgroup>` element: Table column group.
    *
    * '''Common usage:''' Group and style one or more columns in a `table`.
    *
    * '''Common attributes:''' `span`, `id`, `class`
    *
    * @example
    *   colgroup("span" := "3"):
    *     col("class" := "wide")
    */
  inline def colgroup(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("colgroup")(attrs*)(nested)

  /** The `<form>` element: Form.
    *
    * '''Common usage:''' Collect and submit user input to a server or script.
    *
    * '''Common attributes:''' `action`, `method`, `enctype`, `name`, `novalidate`, `autocomplete`, `target`
    *
    * @example
    *   form("action" := "/login", "method" := "post"):
    *     input("type" := "text", "name" := "email")
    */
  inline def form(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("form")(attrs*)(nested)

  /** The `<label>` element: Form label.
    *
    * '''Common usage:''' Describe a form control; associate with `for` or by wrapping the control.
    *
    * '''Common attributes:''' `for`, `form`, `id`, `class`
    *
    * @example
    *   label("for" := "email"):
    *     text("Email")
    */
  inline def label(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("label")(attrs*)(nested)

  /** The `<input>` element: Form input control.
    *
    * '''Common usage:''' Collect text, choices, files, or buttons depending on `type`.
    *
    * '''Common attributes:''' `type`, `name`, `value`, `placeholder`, `required`, `disabled`, `checked`, `min`, `max`, `step`, `pattern`, `autocomplete`
    *
    * @example
    *   input("type" := "email", "name" := "email", "required" := "required")
    */
  inline def input(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("input")(attrs*)(nested)

  /** The `<textarea>` element: Multiline text input.
    *
    * '''Common usage:''' Collect longer free-form text from users.
    *
    * '''Common attributes:''' `name`, `rows`, `cols`, `placeholder`, `required`, `disabled`, `maxlength`, `wrap`
    *
    * @example
    *   textarea("name" := "message", "rows" := "4"):
    *     text("Hello")
    */
  inline def textarea(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("textarea")(attrs*)(nested)

  /** The `<button>` element: Button control.
    *
    * '''Common usage:''' Trigger an action in a form or via scripting.
    *
    * '''Common attributes:''' `type`, `name`, `value`, `disabled`, `form`, `formaction`, `autofocus`
    *
    * @example
    *   button("type" := "submit", "class" := "btn"):
    *     text("Save")
    */
  inline def button(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("button")(attrs*)(nested)

  /** The `<select>` element: Option list.
    *
    * '''Common usage:''' Present a dropdown list of options.
    *
    * '''Common attributes:''' `name`, `multiple`, `required`, `disabled`, `size`, `autocomplete`
    *
    * @example
    *   select("name" := "plan"):
    *     option("value" := "free"):
    *       text("Free")
    *     option("value" := "pro", "selected" := "selected"):
    *       text("Pro")
    */
  inline def select(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("select")(attrs*)(nested)

  /** The `<option>` element: Select option.
    *
    * '''Common usage:''' Define one selectable item in a `select` or `datalist`.
    *
    * '''Common attributes:''' `value`, `label`, `selected`, `disabled`
    *
    * @example
    *   option("value" := "ca"):
    *     text("Canada")
    */
  inline def option(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("option")(attrs*)(nested)

  /** The `<optgroup>` element: Grouped options.
    *
    * '''Common usage:''' Group related `option` elements inside a `select`.
    *
    * '''Common attributes:''' `label`, `disabled`
    *
    * @example
    *   optgroup("label" := "North America"):
    *     option("value" := "ca"):
    *       text("Canada")
    */
  inline def optgroup(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("optgroup")(attrs*)(nested)

  /** The `<fieldset>` element: Form field group.
    *
    * '''Common usage:''' Group related controls and labels in a form.
    *
    * '''Common attributes:''' `disabled`, `name`, `form`, `id`, `class`
    *
    * @example
    *   fieldset:
    *     legend:
    *       text("Shipping")
    *     input("type" := "text", "name" := "address")
    */
  inline def fieldset(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("fieldset")(attrs*)(nested)

  /** The `<legend>` element: Fieldset caption.
    *
    * '''Common usage:''' Provide a caption for a `fieldset`.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   legend:
    *     text("Account details")
    */
  inline def legend(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("legend")(attrs*)(nested)

  /** The `<datalist>` element: Input suggestions.
    *
    * '''Common usage:''' Offer predefined options for an associated `input`.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   datalist("id" := "browsers"):
    *     option("value" := "Chrome")
    *     option("value" := "Firefox")
    */
  inline def datalist(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("datalist")(attrs*)(nested)

  /** The `<output>` element: Calculation result.
    *
    * '''Common usage:''' Display the result of a calculation or user action.
    *
    * '''Common attributes:''' `for`, `name`, `form`, `id`, `class`
    *
    * @example
    *   output("name" := "total", "for" := "qty price"):
    *     text("0")
    */
  inline def output(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("output")(attrs*)(nested)

  /** The `<progress>` element: Task progress.
    *
    * '''Common usage:''' Show completion progress for a task.
    *
    * '''Common attributes:''' `value`, `max`, `id`, `class`
    *
    * @example
    *   progress("value" := "70", "max" := "100")
    */
  inline def progress(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("progress")(attrs*)(nested)

  /** The `<meter>` element: Scalar measurement.
    *
    * '''Common usage:''' Display a scalar value within a known range.
    *
    * '''Common attributes:''' `value`, `min`, `max`, `low`, `high`, `optimum`, `id`, `class`
    *
    * @example
    *   meter("value" := "0.6", "min" := "0", "max" := "1")
    */
  inline def meter(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("meter")(attrs*)(nested)

  /** The `<details>` element: Disclosure widget.
    *
    * '''Common usage:''' Create an expandable section toggled by a `summary`.
    *
    * '''Common attributes:''' `open`, `name`, `id`, `class`
    *
    * @example
    *   details:
    *     summary:
    *       text("More info")
    *     p:
    *       text("Hidden details.")
    */
  inline def details(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("details")(attrs*)(nested)

  /** The `<summary>` element: Details summary.
    *
    * '''Common usage:''' Provide the visible label for a `details` element.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   summary:
    *     text("Click to expand")
    */
  inline def summary(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("summary")(attrs*)(nested)

  /** The `<dialog>` element: Modal or non-modal dialog.
    *
    * '''Common usage:''' Represent a dialog box or other interactive component.
    *
    * '''Common attributes:''' `open`, `id`, `class`
    *
    * @example
    *   dialog("id" := "confirm"):
    *     p:
    *       text("Are you sure?")
    *     button("type" := "button"):
    *       text("Close")
    */
  inline def dialog(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("dialog")(attrs*)(nested)

  /** The `<template>` element: Inert template content.
    *
    * '''Common usage:''' Hold HTML fragments that are not rendered until cloned by script.
    *
    * '''Common attributes:''' `id`, `class`
    *
    * @example
    *   template("id" := "row-template"):
    *     tr:
    *       td:
    *         text("Placeholder")
    */
  inline def template(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("template")(attrs*)(nested)

  /** The `<menu>` element: Toolbar or context menu.
    *
    * '''Common usage:''' Present a list of commands or actions.
    *
    * '''Common attributes:''' `type`, `label`, `id`, `class`
    *
    * @example
    *   menu("type" := "toolbar"):
    *     button("type" := "button"):
    *       text("Copy")
    */
  inline def menu(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("menu")(attrs*)(nested)

  /** The `<slot>` element: Shadow DOM slot.
    *
    * '''Common usage:''' Define a placeholder in a web component shadow tree.
    *
    * '''Common attributes:''' `name`, `id`, `class`
    *
    * @example
    *   slot("name" := "title"):
    *     text("Default title")
    */
  inline def slot(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using Html): Unit =
    elem("slot")(attrs*)(nested)

end HtmlElements
