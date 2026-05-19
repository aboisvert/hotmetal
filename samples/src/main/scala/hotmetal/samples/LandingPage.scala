package hotmetal.samples

import hotmetal.Html
import hotmetal.Html.*
import hotmetal.HtmlElements.*
import SampleComponents.*
import SamplePageNav.primaryNav

object LandingPage:
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

  def renderInto(using Html): Unit =
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

  def render(): Html =
    Html:
      renderInto
