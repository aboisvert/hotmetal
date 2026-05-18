package hotmetal.samples

import hotmetal.Html
import munit.FunSuite

class SamplePagesSuite extends FunSuite:
  import hotmetal.Html.*

  test("div helper matches elem div output"):
    val rendered = Html:
      SampleComponents.div(
        "class" := "grid gap-4",
        "id" := "demo"
      ):
        text("hello")

    assertEquals(
      rendered.toString,
      """<div class="grid gap-4" id="demo">hello</div>"""
    )

  test("top navigation highlights the current page from shared nav data"):
    val rendered = Html:
      SampleComponents.topNav(
        Seq(
          NavItem("Dashboard", "/dashboard"),
          NavItem("Settings", "/settings"),
          NavItem("Launch", "/landing")
        ),
        currentPath = "/settings"
      )

    assertEquals(count(rendered.toString, "data-current=\"true\""), 2)
    assert(rendered.toString.contains("href=\"&#47;settings\""))

  test("flashMessages renders nothing for empty state"):
    val rendered = Html:
      SampleComponents.flashMessages(Nil)

    assertEquals(rendered.toString, "")

  test("text fields escape labels values and error messages"):
    val rendered = Html:
      SampleComponents.textField(
        TextField(
          id = "owner",
          name = "owner",
          label = "Owner <Admin>",
          value = """<script>alert("x")</script>""",
          error = Some("Bad input <unsafe>")
        )
      )

    val html = rendered.toString
    assert(html.contains("Owner &lt;Admin&gt;"))
    assert(html.contains("""value="&lt;script&gt;alert(&quot;x&quot;)&lt;&#47;script&gt;""""))
    assert(html.contains("Bad input &lt;unsafe&gt;"))

  test("tables escape generated row data"):
    val rendered = Html:
      SampleComponents.dataTable(
        columns = Seq(
          TableColumn("Name"),
          TableColumn("Owner"),
          TableColumn("Region"),
          TableColumn("Action"),
          TableColumn("Status")
        ),
        rows = Seq(
          TableRow(
            cells = Seq("Acme <Admin>", "Taylor", "us-east-1", "Review <script>"),
            status = "Pending"
          )
        )
      )

    val html = rendered.toString
    assert(html.contains("Acme &lt;Admin&gt;"))
    assert(html.contains("Review &lt;script&gt;"))

  test("sample page registry exposes four complete pages"):
    val pages = SamplePages.all

    assertEquals(pages.map(_.name), Seq("dashboard", "checkout", "settings", "landing"))

    for page <- pages do
      val html = page.render().toString
      assert(html.startsWith("<!doctype html>"))
      assert(html.contains("<head>"))
      assert(html.contains("<body"))
      assert(html.contains("https://cdn.tailwindcss.com"))
      assert(html.contains("alpinejs"))

  test("dashboard and settings pages keep unsafe sample data escaped"):
    val dashboardHtml = SamplePages.dashboard().toString
    val settingsHtml = SamplePages.settings().toString

    assert(dashboardHtml.contains("Acme &lt;Admin&gt;"))
    assert(settingsHtml.contains("&lt;not-a-real-secret&gt;"))

  test("landing page includes interactive alpine hooks and shared components"):
    val html = SamplePages.landing().toString

    assert(html.contains("""x-data="{ modalOpen: false }""""))
    assert(html.contains("Generated through reusable data-driven components"))
    assert(html.contains("FAQ accordions with Alpine.js"))

  private def count(str: String, needle: String): Int =
    if needle.isEmpty then 0
    else str.sliding(needle.length).count(_ == needle)

