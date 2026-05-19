package hotmetal.samples

import hotmetal.Html
import hotmetal.Html.*
import hotmetal.HtmlElements.*
import SampleComponents.*
import SamplePageNav.primaryNav

object SettingsPage:
  private val settingsFlashes = Seq(
    FlashMessage(
      kind = "error",
      title = "One field needs attention",
      body = "The webhook signing secret has example unsafe text so the suite can verify HTML escaping."
    )
  )

  private val sideNav = Seq(
    NavItem("Profile", "#/settings"),
    NavItem("Notifications", "#/settings/notifications"),
    NavItem("Security", "#/settings/security", badge = Some("Alert"))
  )

  def render(): Html =
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
        sideBar(sideNav, "/settings")
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
