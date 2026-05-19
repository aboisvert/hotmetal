package hotmetal.samples

import hotmetal.Html
import hotmetal.Html.*
import hotmetal.HtmlElements.*
import SampleComponents.*

object SamplePages:
  private val primaryNav = Seq(
    NavItem("Dashboard", "dashboard.html"),
    NavItem("Checkout", "checkout.html", badge = Some("Live")),
    NavItem("Settings", "settings.html"),
    NavItem("Launch", "landing.html")
  )

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

  private val checkoutFlashes = Seq(
    FlashMessage(
      kind = "warning",
      title = "Demo inventory",
      body = "Inventory and totals are static demo values, but the quantity controls and promo state are wired with Alpine.js."
    )
  )

  private val checkoutProducts = Seq(
    Product("Alpine starter kit", "$89", "A compact bundle for interactive UI experiments.", 1, "Popular"),
    Product("Tailwind reporting pack", "$149", "Figma-ready layouts ported into Hotmetal-friendly components.", 2, "Bundle"),
    Product("Hotmetal support hours", "$299", "A sample line item with intentionally rich copy and repeated markup.", 1, "Service")
  )

  private val settingsFlashes = Seq(
    FlashMessage(
      kind = "error",
      title = "One field needs attention",
      body = "The webhook signing secret has example unsafe text so the suite can verify HTML escaping."
    )
  )

  private val landingFlashes = Seq(
    FlashMessage(
      kind = "success",
      title = "Interactive landing page",
      body = "Open the exported file directly in a browser to try the FAQ accordion and launch modal."
    )
  )

  private val pricingTiers = Seq(
    PricingTier(
      "Starter",
      "$19",
      "Enough structure to ship docs, forms, and a small dashboard.",
      Seq("Reusable layouts", "Tailwind-ready components", "Static page export")
    ),
    PricingTier(
      "Growth",
      "$79",
      "Adds larger samples and richer interactions for teams evaluating Hotmetal.",
      Seq("Advanced forms", "Generated tables and cards", "Alpine-powered states"),
      featured = true
    ),
    PricingTier(
      "Scale",
      "$199",
      "Designed for performance discussions and multi-page benchmark suites.",
      Seq("Benchmark fixtures", "Export verification", "Long-form sample pages")
    )
  )

  private val faqItems = Seq(
    FaqItem(
      "Can these pages be opened directly from disk?",
      "Yes. The samples project exports full HTML files that load Tailwind and Alpine from CDNs."
    ),
    FaqItem(
      "Why keep generation code out of benchmarks?",
      "Separating generation into the samples project makes the benchmark easier to understand and keeps sample pages reusable outside JMH."
    ),
    FaqItem(
      "Do the components demonstrate realistic backend data flow?",
      "They do. Shared Scala case classes drive navigation, flash messages, forms, tables, pricing cards, and list rendering."
    )
  )

  def dashboard(): Html =
    Html:
      baseLayout(
        title = "Operations dashboard",
        currentPath = "/dashboard",
        navItems = primaryNav,
        flashes = dashboardFlashes
      ):
        html"""<main class="grid gap-6 lg:grid-cols-[260px_minmax(0,1fr)]">"""
        sideBar(workspaceNav, "/dashboard")
        div(`class` = "grid gap-6"):
          metricCards(dashboardMetrics)
          html"""<section class="grid gap-6 xl:grid-cols-[minmax(0,1.5fr)_minmax(0,1fr)]">"""
          div(`class` = "space-y-4"):
            div(`class` = "flex items-center justify-between"):
              div():
                html"""<p class="text-sm font-semibold uppercase tracking-[0.2em] text-slate-400">Customer activity</p>"""
                html"""<h1 class="mt-2 text-3xl font-black tracking-tight text-slate-900">Live operations snapshot</h1>"""
              html"""<button class="rounded-2xl bg-slate-900 px-4 py-3 text-sm font-semibold text-white">Create campaign</button>"""
            dataTable(activityColumns, activityRows)
          html"""
            <aside class="grid gap-4">
              <article class="rounded-3xl border border-slate-200 bg-white p-5 shadow-sm" x-data="{ expanded: false }">
          """
          div(`class` = "flex items-center justify-between"):
            div():
              html"""<p class="text-sm text-slate-500">Owner notes</p>"""
              html"""<p class="mt-1 text-lg font-semibold text-slate-900">Weekly launch review</p>"""
            html"""
              <button
                type="button"
                class="rounded-2xl border border-slate-200 px-3 py-2 text-sm font-medium text-slate-700"
                @click="expanded = !expanded"
              >
                Toggle details
              </button>
            """
          html"""
                <p class="mt-4 text-sm text-slate-600">
                  The dashboard page combines reusable cards, tables, flash banners, and navigation components into a fuller layout.
                </p>
                <div x-show="expanded" x-transition class="mt-4 rounded-2xl bg-slate-50 p-4 text-sm text-slate-600">
                  <p>Escalation follow-up includes the escaped account name Acme &lt;Admin&gt; in the table data.</p>
                  <p class="mt-2">The note panel exists to showcase Alpine state in a content-heavy shell.</p>
                </div>
              </article>
              <article class="rounded-3xl border border-slate-200 bg-white p-5 shadow-sm">
                <p class="text-sm font-semibold uppercase tracking-[0.2em] text-slate-400">Shipping queue</p>
                <ul class="mt-4 grid gap-3">
                  ${
                    for item <- Seq(
                      "Warehouse restock posted",
                      "CSV import validated",
                      "Fraud review waiting on analyst"
                    ) do html"""<li class="rounded-2xl bg-slate-50 px-4 py-3 text-sm text-slate-600">$item</li>"""
                  }
                </ul>
              </article>
            </aside>
          </section>
          """
        html"</main>"

  def checkout(): Html =
    Html:
      baseLayout(
        title = "Checkout workspace",
        currentPath = "/checkout",
        navItems = primaryNav,
        flashes = checkoutFlashes
      ):
        html"""<main class="grid gap-6 lg:grid-cols-[minmax(0,1.1fr)_420px]" x-data="{ promoOpen: false, quantity: 3 }"><section class="space-y-6">"""
        div():
          html"""<p class="text-sm font-semibold uppercase tracking-[0.2em] text-slate-400">Checkout</p>"""
          html"""<h1 class="mt-2 text-3xl font-black tracking-tight text-slate-900">Compose a realistic purchase flow</h1>"""
          html"""
            <p class="mt-3 max-w-2xl text-sm leading-6 text-slate-600">
              This sample uses shared field components, repeated product cards, and Alpine controls for promo visibility and quantity previews.
            </p>
          """
        productList(checkoutProducts)
        html"""<section class="rounded-3xl border border-slate-200 bg-white p-6 shadow-sm">"""
        div(`class` = "grid gap-5 md:grid-cols-2"):
          textField(
            TextField(
              id = "shipping-name",
              name = "shippingName",
              label = "Contact name",
              value = "Jamie Rivera",
              placeholder = "Add the shipping contact",
              helpText = Some("This uses the shared text field renderer."),
              required = true
            )
          )
          textField(
            TextField(
              id = "shipping-email",
              name = "shippingEmail",
              label = "Email address",
              value = "jamie@example.com",
              inputType = "email",
              placeholder = "Add an order contact",
              required = true
            )
          )
          textField(
            TextField(
              id = "address",
              name = "address",
              label = "Street address",
              value = "1208 Benchmark Way",
              placeholder = "Start typing an address",
              required = true
            )
          )
          selectField(
            SelectField(
              id = "country",
              name = "country",
              label = "Country",
              options = Seq(
                SelectOption("ca", "Canada"),
                SelectOption("us", "United States"),
                SelectOption("uk", "United Kingdom")
              ),
              selectedValue = "ca",
              helpText = Some("Select menus are also rendered through a reusable component.")
            )
          )
        checkboxField(
          CheckboxField(
            id = "billing-same",
            name = "billingSame",
            label = "Billing address matches shipping",
            checked = true,
            helpText = Some("Useful for demonstrating repeated conditional field groups.")
          )
        )
        radioGroup(
          RadioGroup(
            name = "deliverySpeed",
            label = "Delivery speed",
            selectedValue = "express",
            helpText = Some("Radio groups show more complex form markup without adding benchmark-only abstractions."),
            options = Seq(
              RadioOption("standard", "Standard", Some("Arrives in 4 to 6 business days.")),
              RadioOption("express", "Express", Some("Arrives in 1 to 2 business days.")),
              RadioOption("pickup", "Store pickup", Some("Collect from the sample warehouse."))
            )
          )
        )
        html"""</section></section><aside class="space-y-6"><section class="rounded-3xl border border-slate-200 bg-white p-6 shadow-sm">"""
        div(`class` = "flex items-center justify-between"):
          div():
            html"""<p class="text-sm font-semibold text-slate-500">Order total</p>"""
            html"""<p class="mt-1 text-3xl font-black tracking-tight text-slate-900">${"$537"}</p>"""
          html"""
            <button
              type="button"
              class="rounded-2xl border border-slate-200 px-4 py-2 text-sm font-medium text-slate-700"
              @click="promoOpen = !promoOpen"
            >
              Promo code
            </button>
          """
        html"""<div x-show="promoOpen" x-transition class="mt-4 grid gap-3">"""
        textField(
          TextField(
            id = "promo",
            name = "promo",
            label = "Promotion code",
            value = "HOTMETAL15",
            placeholder = "Enter a code"
          )
        )
        html"""<button type="button" class="rounded-2xl bg-brand-500 px-4 py-3 text-sm font-semibold text-white">Apply code</button></div>"""
        div(`class` = "mt-6 rounded-2xl bg-slate-50 p-4 text-sm text-slate-600"):
          html"""<p>Package count preview: <span class="font-semibold text-slate-900" x-text="quantity"></span></p>"""
          div(`class` = "mt-3 flex items-center gap-3"):
            html"""<button type="button" class="rounded-xl border border-slate-200 px-3 py-2" @click="quantity = Math.max(1, quantity - 1)">-</button>"""
            html"""<button type="button" class="rounded-xl border border-slate-200 px-3 py-2" @click="quantity = quantity + 1">+</button>"""
            html"""<span class="text-xs uppercase tracking-[0.2em] text-slate-400">Static Alpine demo</span>"""
        html"""</section></aside></main>"""

  def settings(): Html =
    Html:
      baseLayout(
        title = "Account settings",
        currentPath = "/settings",
        navItems = primaryNav,
        flashes = settingsFlashes
      ):
        html"""
          <main class="grid gap-6 lg:grid-cols-[260px_minmax(0,1fr)]" x-data="{ tab: 'profile' }">
        """
        sideBar(
          Seq(
            NavItem("Profile", "#/settings"),
            NavItem("Notifications", "#/settings/notifications"),
            NavItem("Security", "#/settings/security", badge = Some("Alert"))
          ),
          "/settings"
        )
        html"""<section class="space-y-6">"""
        div(`class` = "flex flex-wrap gap-3"):
          html"""
            <button
              type="button"
              class="rounded-2xl px-4 py-3 text-sm font-semibold"
              :class="tab === 'profile' ? 'bg-slate-900 text-white' : 'border border-slate-200 bg-white text-slate-700'"
              @click="tab = 'profile'"
            >
              Profile
            </button>
            <button
              type="button"
              class="rounded-2xl px-4 py-3 text-sm font-semibold"
              :class="tab === 'preferences' ? 'bg-slate-900 text-white' : 'border border-slate-200 bg-white text-slate-700'"
              @click="tab = 'preferences'"
            >
              Preferences
            </button>
          """
        html"""<section x-show="tab === 'profile'" x-transition class="grid gap-6">"""
        div(`class` = "rounded-3xl border border-slate-200 bg-white p-6 shadow-sm"):
          html"""
            <h1 class="text-3xl font-black tracking-tight text-slate-900">Reusable settings form</h1>
            <p class="mt-3 max-w-2xl text-sm leading-6 text-slate-600">
              The settings page demonstrates macros for fields, checkboxes, radio groups, flash messages, and conditional tab content.
            </p>
          """
          div(`class` = "mt-6 grid gap-5 md:grid-cols-2"):
            textField(
              TextField(
                id = "display-name",
                name = "displayName",
                label = "Display name",
                value = "Taylor Morgan",
                required = true
              )
            )
            textField(
              TextField(
                id = "job-title",
                name = "jobTitle",
                label = "Job title",
                value = "Staff Developer Advocate"
              )
            )
            textField(
              TextField(
                id = "webhook-secret",
                name = "webhookSecret",
                label = "Webhook signing secret",
                value = "<not-a-real-secret>",
                error = Some("Unsafe sample text should render escaped in the generated HTML.")
              )
            )
            selectField(
              SelectField(
                id = "timezone",
                name = "timezone",
                label = "Timezone",
                selectedValue = "america-toronto",
                options = Seq(
                  SelectOption("america-toronto", "America/Toronto"),
                  SelectOption("america-vancouver", "America/Vancouver"),
                  SelectOption("europe-berlin", "Europe/Berlin")
                )
              )
            )
          checkboxField(
            CheckboxField(
              id = "marketing-updates",
              name = "marketingUpdates",
              label = "Send quarterly product update digests",
              checked = true,
              helpText = Some("A simple checkbox rendered via a shared helper.")
            )
          )
          radioGroup(
            RadioGroup(
              name = "incidentUpdates",
              label = "Incident alerts",
              selectedValue = "mentions",
              options = Seq(
                RadioOption("all", "All incidents", Some("Useful for production operators.")),
                RadioOption("mentions", "Only when my team is mentioned", Some("Keeps the sample realistic for most users.")),
                RadioOption("none", "No direct alerts")
              )
            )
          )
        html"""</section><section x-show="tab === 'preferences'" x-transition class="rounded-3xl border border-slate-200 bg-white p-6 shadow-sm"><h2 class="text-2xl font-bold text-slate-900">Notification preview</h2>"""
        div(`class` = "mt-5 grid gap-4 md:grid-cols-2"):
          for message <- Seq(
            "Weekly digest with performance summaries",
            "Billing notices for failed cards",
            "Security reports for rotated keys"
          ) do
            html"""
              <article class="rounded-2xl bg-slate-50 p-4">
                <p class="text-sm font-semibold text-slate-900">$message</p>
                <p class="mt-2 text-sm text-slate-500">Generated in a loop from shared page state.</p>
              </article>
            """
        html"""</section></section></main>"""

  def landingInto(using Html): Unit =
    baseLayout(
      title = "Launch with Hotmetal",
      currentPath = "/landing",
      navItems = primaryNav,
      flashes = landingFlashes
    ):
      html"""<main class="grid gap-8"><section class="grid gap-8 rounded-[2rem] bg-slate-900 px-8 py-10 text-white lg:grid-cols-[minmax(0,1.2fr)_420px]" x-data="{ modalOpen: false }">"""
      div():
        html"""<p class="text-sm font-semibold uppercase tracking-[0.3em] text-brand-200">Static pages that still feel alive</p>"""
        html"""<h1 class="mt-4 text-5xl font-black tracking-tight">Port realistic Tailwind and Alpine page patterns into Hotmetal.</h1>"""
        html"""
          <p class="mt-5 max-w-2xl text-base leading-7 text-slate-300">
            The landing page showcases shared layout components, repeated pricing cards, FAQ accordions, generated banners, and a small modal interaction.
          </p>
        """
        div(`class` = "mt-8 flex flex-wrap gap-3"):
          html"""<button type="button" class="rounded-2xl bg-white px-5 py-3 text-sm font-semibold text-slate-900" @click="modalOpen = true">Preview launch modal</button>"""
          html"""<a href="#pricing" class="rounded-2xl border border-white/20 px-5 py-3 text-sm font-semibold text-white">View pricing</a>"""
      div(`class` = "rounded-[2rem] bg-white p-6 text-slate-900 shadow-2xl"):
        html"""<p class="text-sm font-semibold uppercase tracking-[0.2em] text-brand-700">Why it matters</p>"""
        div(`class` = "mt-4 grid gap-4"):
          for bullet <- Seq(
            "Child pages extend a shared base layout.",
            "Navigation and alerts render from shared Scala data.",
            "Forms, lists, and cards exercise loops and reusable fragments."
          ) do
            div(`class` = "rounded-2xl bg-slate-50 px-4 py-3 text-sm text-slate-600"):
              html"$bullet"
      html"""
        <div
          x-show="modalOpen"
          x-transition
          class="fixed inset-0 z-30 flex items-center justify-center bg-slate-950/70 px-4"
        >
          <div class="w-full max-w-lg rounded-[2rem] bg-white p-6 text-slate-900 shadow-2xl" @click.outside="modalOpen = false">
            <p class="text-sm font-semibold uppercase tracking-[0.2em] text-brand-700">Launch checklist</p>
            <h2 class="mt-3 text-2xl font-bold">Ship generated pages with confidence</h2>
            <p class="mt-3 text-sm leading-6 text-slate-600">
              Benchmarks can measure rendering, while the exported sample pages make the output easy to review in a browser.
            </p>
            <button type="button" class="mt-6 rounded-2xl bg-slate-900 px-4 py-3 text-sm font-semibold text-white" @click="modalOpen = false">
              Close
            </button>
          </div>
        </div>
      </section>
      <section id="pricing" class="grid gap-4">
      """
      div():
        html"""<p class="text-sm font-semibold uppercase tracking-[0.2em] text-slate-400">Pricing cards</p>"""
        html"""<h2 class="mt-2 text-3xl font-black tracking-tight text-slate-900">Generated through reusable data-driven components</h2>"""
      pricingGrid(pricingTiers)
      html"""</section><section class="grid gap-4 lg:grid-cols-[minmax(0,1fr)_420px]">"""
      div(`class` = "space-y-4"):
        html"""<p class="text-sm font-semibold uppercase tracking-[0.2em] text-slate-400">Frequently asked questions</p>"""
        html"""<h2 class="text-3xl font-black tracking-tight text-slate-900">FAQ accordions with Alpine.js</h2>"""
        faqAccordion(faqItems)
      html"""<aside class="rounded-3xl border border-slate-200 bg-white p-6 shadow-sm"><h3 class="text-2xl font-bold text-slate-900">Join the demo newsletter</h3><p class="mt-3 text-sm leading-6 text-slate-600">This sidebar uses the same field macros as the checkout and settings pages.</p>"""
      div(`class` = "mt-5 grid gap-4"):
        textField(
          TextField(
            id = "newsletter-name",
            name = "newsletterName",
            label = "Your name",
            value = "Morgan Example"
          )
        )
        textField(
          TextField(
            id = "newsletter-email",
            name = "newsletterEmail",
            label = "Email address",
            inputType = "email",
            value = "morgan@example.com",
            required = true
          )
        )
        checkboxField(
          CheckboxField(
            id = "newsletter-opt-in",
            name = "newsletterOptIn",
            label = "Email me product updates and benchmark news",
            checked = true
          )
        )
        html"""<button type="button" class="rounded-2xl bg-slate-900 px-4 py-3 text-sm font-semibold text-white">Subscribe</button>"""
      html"""</aside></section></main>"""

  def landing(): Html =
    Html:
      landingInto

  def all: Seq[SamplePage] =
    Seq(
      SamplePage("dashboard", "Operations dashboard", () => dashboard()),
      SamplePage("checkout", "Checkout workspace", () => checkout()),
      SamplePage("settings", "Account settings", () => settings()),
      SamplePage("landing", "Launch with Hotmetal", () => landing())
    )

