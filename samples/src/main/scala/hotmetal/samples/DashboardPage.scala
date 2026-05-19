package hotmetal.samples

import hotmetal.Html
import hotmetal.Html.*
import hotmetal.HtmlElements.*
import SampleComponents.*
import SamplePageNav.primaryNav

object DashboardPage:
  private val workspaceNav = Seq(
    NavItem("Overview", "#/dashboard"),
    NavItem("Deployments", "#/dashboard/deployments", badge = Some("4")),
    NavItem("Alerts", "#/dashboard/alerts", badge = Some("2")),
    NavItem("Reports", "#/dashboard/reports")
  )

  private val dashboardFlashes = Seq(
    FlashMessage(
      kind = "success",
      title = "Deploy completed",
      body = "Release train 2026.05.18 shipped successfully to all starter regions."
    ),
    FlashMessage(
      kind = "info",
      title = "Shared layout in action",
      body = "Top navigation, footer, and flash messages are generated through reusable Hotmetal components."
    )
  )

  private val dashboardMetrics = Seq(
    MetricCard("Orders synced", "14,842", "+12.4%", "positive", "Steady growth across all storefronts."),
    MetricCard("Pending reviews", "38", "-4", "neutral", "Backlog dropped after automated triage."),
    MetricCard("Failed webhooks", "7", "+2", "warning", "Most failures come from a sandbox integration."),
    MetricCard("Escalations", "3", "-1", "positive", "Support volume is trending back to baseline.")
  )

  private val activityColumns = Seq(
    TableColumn("Account"),
    TableColumn("Owner"),
    TableColumn("Region"),
    TableColumn("Next step"),
    TableColumn("Status")
  )

  private val activityRows = Seq(
    TableRow(Seq("Northwind", "Ava Patel", "us-east-1", "Approve rollout window"), "Healthy"),
    TableRow(Seq("Pioneer Labs", "Malik Chen", "eu-west-2", "Rotate API credentials"), "Pending"),
    TableRow(Seq("Sunset Grocery", "Riley Brown", "ap-southeast-1", "Resolve tax mismatch"), "Escalated"),
    TableRow(Seq("Acme <Admin>", "Jordan Lee", "ca-central-1", "Validate notification copy"), "Pending")
  )

  def render(): Html =
    Html:
      baseLayout(
        title = "Operations dashboard",
        currentPath = "/dashboard",
        navItems = primaryNav,
        flashes = dashboardFlashes
      ):
        main(`class` = "grid gap-6 lg:grid-cols-[260px_minmax(0,1fr)]"):
          sideBar(workspaceNav, "/dashboard")
          div(`class` = "grid gap-6"):
            metricCards(dashboardMetrics)
            section(`class` = "grid gap-6 xl:grid-cols-[minmax(0,1.5fr)_minmax(0,1fr)]"):
              div(`class` = "space-y-4"):
                div(`class` = "flex items-center justify-between"):
                  div():
                    html"""
                    <p class="text-sm font-semibold uppercase tracking-[0.2em] text-slate-400">
                      Customer activity
                    </p>
                    <h1 class="mt-2 text-3xl font-black tracking-tight text-slate-900">
                      Live operations snapshot
                    </h1>
                    """

                  html"""
                  <button class="rounded-2xl bg-slate-900 px-4 py-3 text-sm font-semibold text-white">
                    Create campaign
                  </button>
                  """
                dataTable(activityColumns, activityRows)

              aside(`class` = "grid gap-4"):

                article(
                  `class` = "rounded-3xl border border-slate-200 bg-white p-5 shadow-sm",
                  "x-data" := "{ expanded: false }"
                ):
                  html"""
                  <div class="flex items-center justify-between">
                    <div>
                      <p class="text-sm text-slate-500">Owner notes</p>
                      <p class="mt-1 text-lg font-semibold text-slate-900">Weekly launch review</p>
                    </div>
                    <button
                      type="button"
                      class="rounded-2xl border border-slate-200 px-3 py-2 text-sm font-medium text-slate-700"
                      @click="expanded = !expanded"
                    >
                      Toggle details
                    </button>
                  </div>
                  <p class="mt-4 text-sm text-slate-600">
                    The dashboard page combines reusable cards, tables, flash banners, and navigation components into a fuller layout.
                  </p>
                  <div
                    class="mt-4 rounded-2xl bg-slate-50 p-4 text-sm text-slate-600"
                    x-show="expanded"
                    x-transition
                  >
                    <p>Escalation follow-up includes the escaped account name Acme &lt;Admin&gt; in the table data.</p>
                    <p class="mt-2">The note panel exists to showcase Alpine state in a content-heavy shell.</p>
                  </div>
                  """

                article(`class` = "rounded-3xl border border-slate-200 bg-white p-5 shadow-sm"):
                  html"""
                  <p class="text-sm font-semibold uppercase tracking-[0.2em] text-slate-400">
                    Shipping queue
                  </p>
                  <ul class="mt-4 grid gap-3">
                    ${ //
                    for item <- Seq(
                        "Warehouse restock posted",
                        "CSV import validated",
                        "Fraud review waiting on analyst"
                      )
                    do
                      html"""
                        <li class="rounded-2xl bg-slate-50 px-4 py-3 text-sm text-slate-600">
                          $item
                        </li>
                      """
                    }
                  </ul>
                  """