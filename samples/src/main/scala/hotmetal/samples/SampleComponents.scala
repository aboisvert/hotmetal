package hotmetal.samples

import hotmetal.Html
import hotmetal.Html.*

final case class NavItem( //
    label: String,
    href: String,
    badge: Option[String] = None
)

final case class FlashMessage(
    kind: String,
    title: String,
    body: String,
    dismissible: Boolean = true
)

final case class TextField(
    id: String,
    name: String,
    label: String,
    value: String = "",
    inputType: String = "text",
    placeholder: String = "",
    helpText: Option[String] = None,
    error: Option[String] = None,
    required: Boolean = false
)

final case class SelectOption(value: String, label: String)

final case class SelectField(
    id: String,
    name: String,
    label: String,
    options: Seq[SelectOption],
    selectedValue: String = "",
    helpText: Option[String] = None,
    error: Option[String] = None,
    required: Boolean = false
)

final case class CheckboxField(
    id: String,
    name: String,
    label: String,
    checked: Boolean = false,
    helpText: Option[String] = None
)

final case class RadioOption(
    value: String,
    label: String,
    helpText: Option[String] = None
)

final case class RadioGroup(
    name: String,
    label: String,
    options: Seq[RadioOption],
    selectedValue: String = "",
    helpText: Option[String] = None
)

final case class MetricCard(
    title: String,
    value: String,
    change: String,
    tone: String,
    description: String
)

final case class Product(
    name: String,
    price: String,
    description: String,
    quantity: Int,
    tag: String
)

final case class TableColumn(label: String)

final case class TableRow( //
    cells: Seq[String],
    status: String
)

final case class PricingTier(
    name: String,
    price: String,
    description: String,
    features: Seq[String],
    featured: Boolean = false
)

final case class FaqItem(question: String, answer: String)

final case class SamplePage(
    name: String,
    title: String,
    renderHtml: () => Html
):
  def render(): Html = renderHtml()

object SampleComponents:
  private val footerNav = Seq(
    NavItem("Docs", "#docs"),
    NavItem("Status", "#status"),
    NavItem("Support", "#support")
  )

  inline def div(inline attrs: (HtmlFn)*)(inline nested: HtmlFn = ())(using
      Html
  ): Unit =
    elem("div")(attrs*)(nested)

  def baseLayout(
      title: String,
      currentPath: String,
      navItems: Seq[NavItem],
      flashes: Seq[FlashMessage] = Nil,
      bodyClass: String = "min-h-screen bg-slate-50 text-slate-900"
  )(
      content: HtmlFn
  )(using Html): Unit =
    html"""<!doctype html>
      <html lang="en" class="h-full bg-slate-50">
        <head>
          <meta charset="utf-8" />
          <meta name="viewport" content="width=device-width, initial-scale=1" />
          <title>$title</title>
          <meta
            name="description"
            content="Sample Hotmetal pages that exercise layouts, forms, lists, cards, and Alpine.js interactions."
          />
          <script src="https://cdn.tailwindcss.com"></script>
          <script defer src="https://cdn.jsdelivr.net/npm/alpinejs@3.x.x/dist/cdn.min.js"></script>
          <script>
            tailwind.config = {
              theme: {
                extend: {
                  colors: {
                    brand: {
                      50: "#eef2ff",
                      500: "#6366f1",
                      700: "#4338ca"
                    }
                  }
                }
              }
            };
          </script>
        </head>
        <body class="$bodyClass">
    """
    topNav(navItems, currentPath)
    div(
      "class" := "mx-auto flex min-h-screen max-w-7xl flex-col px-4 py-6 sm:px-6 lg:px-8"
    ):
      if flashes.nonEmpty then flashMessages(flashes)
      content
      footer()
    html"""
        </body>
      </html>
    """

  def topNav(items: Seq[NavItem], currentPath: String)(using Html): Unit =
    html"""
      <header class="sticky top-0 z-20 mb-6 rounded-3xl border border-slate-200 bg-white/90 px-4 py-4 shadow-sm backdrop-blur">
        <div class="flex items-center gap-4" x-data="{ mobileOpen: false, profileOpen: false }">
          <a href="/" class="flex items-center gap-3">
            <div class="flex h-11 w-11 items-center justify-center rounded-2xl bg-brand-500 text-lg font-black text-white">H</div>
            <div>
              <p class="text-sm font-semibold uppercase tracking-[0.25em] text-brand-700">Hotmetal</p>
              <p class="text-xs text-slate-500">Samples and benchmarks</p>
            </div>
          </a>
    """

    html"""
          <nav class="ml-auto hidden items-center gap-2 md:flex">
            ${for item <- items do navLink(item, currentPath, compact = true)}
          </nav>
    """

    html"""
          <div class="relative hidden md:block">
            <button
              type="button"
              @click="profileOpen = !profileOpen"
              class="inline-flex items-center gap-2 rounded-full border border-slate-200 px-3 py-2 text-sm font-medium text-slate-700 transition hover:border-brand-200 hover:text-brand-700"
            >
              <span class="inline-flex h-8 w-8 items-center justify-center rounded-full bg-slate-900 text-xs font-semibold text-white">HM</span>
              Demo team
            </button>
            <div
              x-show="profileOpen"
              x-transition
              @click.outside="profileOpen = false"
              class="absolute right-0 mt-3 w-64 rounded-2xl border border-slate-200 bg-white p-4 shadow-xl"
            >
              <p class="text-sm font-semibold text-slate-900">Realistic static behavior</p>
              <p class="mt-1 text-sm text-slate-500">This menu is driven entirely by Alpine.js state.</p>
              <button
                type="button"
                @click="profileOpen = false"
                class="mt-4 w-full rounded-xl bg-slate-900 px-3 py-2 text-sm font-semibold text-white"
              >
                Close menu
              </button>
            </div>
          </div>
          <button
            type="button"
            @click="mobileOpen = !mobileOpen"
            class="ml-auto inline-flex h-11 w-11 items-center justify-center rounded-2xl border border-slate-200 text-slate-600 md:hidden"
          >
            Menu
          </button>
        </div>
        <nav x-show="mobileOpen" x-transition class="mt-4 grid gap-2 md:hidden">
    """
    for item <- items do navLink(item, currentPath, compact = false)
    html"""
        </nav>
      </header>
    """

  def sideBar(items: Seq[NavItem], currentPath: String)(using Html): Unit =
    html"""
      <aside class="rounded-3xl border border-slate-200 bg-white p-5 shadow-sm">
        <p class="text-xs font-semibold uppercase tracking-[0.3em] text-slate-400">Workspace</p>
        <nav class="mt-4 grid gap-2">
    """
    for item <- items do navLink(item, currentPath, compact = false)
    html"""
        </nav>
    """
    div("class" := "mt-6 rounded-2xl bg-slate-900 p-4 text-white"):
      html"""<p class="text-sm font-semibold">Component-driven UI</p>"""
      html"""
        <p class="mt-2 text-sm text-slate-300">
          Shared layouts, forms, flash messages, and generated lists all live in the samples project.
        </p>
      """
    html"</aside>"

  def flashMessages(messages: Seq[FlashMessage])(using Html): Unit =
    if messages.nonEmpty then
      html"""<section class="mb-6 grid gap-3">"""
      for message <- messages do
        val toneClass = flashToneClass(message.kind)
        html"""
          <div
            class="${attrValues(
            "rounded-2xl",
            "border",
            "px-4",
            "py-4",
            toneClass
          )}"
            x-data="{ open: true }"
            x-show="open"
            x-transition
          >
            <div class="flex items-start gap-3">
              <div class="min-w-0 flex-1">
                <p class="text-sm font-semibold">${message.title}</p>
                <p class="mt-1 text-sm">${message.body}</p>
              </div>
              ${if message.dismissible then html"""
                    <button
                      type="button"
                      @click="open = false"
                      class="rounded-lg px-2 py-1 text-sm font-medium"
                    >
                      Dismiss
                    </button>
                  """}
            </div>
          </div>
        """
      html"</section>"

  def textField(field: TextField)(using Html): Unit =
    div("class" := "space-y-2"):
      fieldLabel(field.id, field.label, field.required)
      html"""
        <input
          id="${field.id}"
          name="${field.name}"
          type="${field.inputType}"
          value="${field.value}"
          placeholder="${field.placeholder}"
          class="${attrValues(
          "block",
          "w-full",
          "rounded-2xl",
          "border",
          "px-4",
          "py-3",
          "text-sm",
          "shadow-sm",
          if field.error.nonEmpty then "border-rose-300 bg-rose-50 text-rose-900"
          else "border-slate-200 bg-white text-slate-900"
        )}"
          ${if field.required then "required" := "required"}
        />
      """
      fieldMessages(field.helpText, field.error)

  def selectField(field: SelectField)(using Html): Unit =
    div("class" := "space-y-2"):
      fieldLabel(field.id, field.label, field.required)
      html"""
        <select
          id="${field.id}"
          name="${field.name}"
          class="${attrValues(
          "block",
          "w-full",
          "rounded-2xl",
          "border",
          "bg-white",
          "px-4",
          "py-3",
          "text-sm",
          "shadow-sm",
          if field.error.nonEmpty then "border-rose-300 text-rose-900"
          else "border-slate-200 text-slate-900"
        )}"
          ${if field.required then "required" := "required"}
        >
      """
      for option <- field.options do
        html"""
          <option value="${option.value}" ${
            if option.value == field.selectedValue then "selected" := "selected"
          }>${option.label}</option>
        """
      html"</select>"
      fieldMessages(field.helpText, field.error)

  def checkboxField(field: CheckboxField)(using Html): Unit =
    html"""
      <label class="flex items-start gap-3 rounded-2xl border border-slate-200 bg-white px-4 py-3 text-sm shadow-sm">
        <input
          id="${field.id}"
          name="${field.name}"
          type="checkbox"
          class="mt-1 h-4 w-4 rounded border-slate-300 text-brand-500"
          ${if field.checked then "checked" := "checked"}
        />
        <span class="min-w-0 flex-1">
          <span class="block font-medium text-slate-900">${field.label}</span>
    """
    if field.helpText.nonEmpty then html"""<span class="mt-1 block text-slate-500">${field.helpText.get}</span>"""
    html"""
        </span>
      </label>
    """

  def radioGroup(group: RadioGroup)(using Html): Unit =
    html"""
      <fieldset class="space-y-3 rounded-3xl border border-slate-200 bg-white p-5 shadow-sm">
        <legend class="text-sm font-semibold text-slate-900">${group.label}</legend>
    """
    if group.helpText.nonEmpty then html"""<p class="text-sm text-slate-500">${group.helpText.get}</p>"""
    for option <- group.options do
      html"""
        <label class="flex items-start gap-3 rounded-2xl border border-slate-200 px-4 py-3">
          <input
            type="radio"
            name="${group.name}"
            value="${option.value}"
            class="mt-1 h-4 w-4 border-slate-300 text-brand-500"
            ${if option.value == group.selectedValue then "checked" := "checked"}
          />
          <span>
            <span class="block text-sm font-medium text-slate-900">${option.label}</span>
      """
      if option.helpText.nonEmpty then
        html"""<span class="mt-1 block text-sm text-slate-500">${option.helpText.get}</span>"""
      html"""
          </span>
        </label>
      """
    html"</fieldset>"

  def metricCards(cards: Seq[MetricCard])(using Html): Unit =
    html"""<section class="grid gap-4 md:grid-cols-2 xl:grid-cols-4">"""
    for card <- cards do
      html"""
        <article class="rounded-3xl border border-slate-200 bg-white p-5 shadow-sm">
      """
      div("class" := "flex items-start justify-between gap-3"):
        div():
          html"""<p class="text-sm text-slate-500">${card.title}</p>"""
          html"""<p class="mt-3 text-3xl font-semibold tracking-tight text-slate-900">${card.value}</p>"""
        html"""
          <span class="${attrValues(
            "rounded-full",
            "px-3",
            "py-1",
            "text-xs",
            "font-semibold",
            metricToneClass(card.tone)
          )}">
            ${card.change}
          </span>
        """
      html"""
          <p class="mt-4 text-sm text-slate-500">${card.description}</p>
        </article>
      """
    html"</section>"

  def dataTable(columns: Seq[TableColumn], rows: Seq[TableRow])(using
      Html
  ): Unit =
    div(
      "class" := "overflow-hidden rounded-3xl border border-slate-200 bg-white shadow-sm"
    ):
      div("class" := "overflow-x-auto"):
        html"""
          <table class="min-w-full divide-y divide-slate-200 text-sm">
            <thead class="bg-slate-50">
              <tr>
        """
        for column <- columns do
          html"""<th class="px-4 py-3 text-left font-semibold uppercase tracking-[0.2em] text-slate-500">${column.label}</th>"""
        html"""
              </tr>
            </thead>
            <tbody class="divide-y divide-slate-100">
        """
        for row <- rows do
          html"<tr>"
          var idx = 0
          while idx < row.cells.length do
            html"""<td class="px-4 py-3 text-slate-700">${row.cells(
                idx
              )}</td>"""
            idx += 1
          html"""
            <td class="px-4 py-3">
              <span class="${attrValues(
              "rounded-full",
              "px-3",
              "py-1",
              "text-xs",
              "font-semibold",
              statusToneClass(row.status)
            )}">
                ${row.status}
              </span>
            </td>
          """
          html"</tr>"
        html"""
            </tbody>
          </table>
        """

  def productList(products: Seq[Product])(using Html): Unit =
    html"""<section class="grid gap-4">"""
    for product <- products do
      html"""
        <article class="rounded-3xl border border-slate-200 bg-white p-5 shadow-sm">
      """
      div(
        "class" := "flex flex-col gap-4 sm:flex-row sm:items-start sm:justify-between"
      ):
        div("class" := "min-w-0 flex-1"):
          div("class" := "flex items-center gap-2"):
            html"""<h3 class="text-lg font-semibold text-slate-900">${product.name}</h3>"""
            html"""<span class="rounded-full bg-brand-50 px-3 py-1 text-xs font-semibold text-brand-700">${product.tag}</span>"""
          html"""<p class="mt-2 text-sm text-slate-500">${product.description}</p>"""
        div("class" := "grid gap-2 text-sm text-slate-600 sm:text-right"):
          html"""<span class="text-lg font-semibold text-slate-900">${product.price}</span>"""
          html"""<span>Qty ${product.quantity}</span>"""
      html"""
        </article>
      """
    html"</section>"

  def pricingGrid(tiers: Seq[PricingTier])(using Html): Unit =
    html"""<section class="grid gap-4 lg:grid-cols-3">"""
    for tier <- tiers do
      html"""
        <article class="${attrValues(
          "rounded-3xl",
          "border",
          "p-6",
          "shadow-sm",
          if tier.featured then "border-brand-200 bg-brand-50"
          else "border-slate-200 bg-white"
        )}">
          <p class="text-sm font-semibold uppercase tracking-[0.2em] text-brand-700">${tier.name}</p>
      """
      div("class" := "mt-4 flex items-end gap-2"):
        html"""<span class="text-4xl font-black tracking-tight text-slate-900">${tier.price}</span>"""
        html"""<span class="pb-1 text-sm text-slate-500">per month</span>"""
      html"""
          <p class="mt-4 text-sm text-slate-600">${tier.description}</p>
          <ul class="mt-6 grid gap-3 text-sm text-slate-600">
      """
      for feature <- tier.features do
        html"""<li class="flex items-start gap-2"><span class="mt-1 h-2 w-2 rounded-full bg-brand-500"></span><span>${feature}</span></li>"""
      html"""
          </ul>
          <button
            type="button"
            class="${attrValues(
          "mt-6",
          "w-full",
          "rounded-2xl",
          "px-4",
          "py-3",
          "text-sm",
          "font-semibold",
          if tier.featured then "bg-slate-900 text-white"
          else "border border-slate-200 bg-white text-slate-900"
        )}"
          >
            Start with ${tier.name}
          </button>
        </article>
      """
    html"</section>"

  def faqAccordion(items: Seq[FaqItem])(using Html): Unit =
    html"""<section class="grid gap-3">"""
    for item <- items do
      html"""
        <article class="rounded-3xl border border-slate-200 bg-white p-5 shadow-sm" x-data="{ open: false }">
          <button
            type="button"
            class="flex w-full items-center justify-between gap-4 text-left"
            @click="open = !open"
          >
            <span class="text-base font-semibold text-slate-900">${item.question}</span>
            <span class="rounded-full bg-slate-100 px-3 py-1 text-xs font-semibold text-slate-500" x-text="open ? 'Hide' : 'Show'"></span>
          </button>
          <div x-show="open" x-transition class="mt-3 text-sm leading-6 text-slate-600">
            ${item.answer}
          </div>
        </article>
      """
    html"</section>"

  private def navLink(item: NavItem, currentPath: String, compact: Boolean)(using
      Html
  ): Unit =
    val active = isCurrent(item.href, currentPath)
    html"""
      <a
        href="${item.href.u}"
        data-current="${if active then "true".u else "false".u}"
        class="${attrValues(
        "inline-flex",
        "items-center",
        "gap-2",
        "rounded-2xl",
        if compact then "px-4 py-2 text-sm" else "px-4 py-3 text-sm",
        if active then "bg-slate-900 text-white shadow-sm"
        else "text-slate-600 hover:bg-slate-100 hover:text-slate-900"
      )}"
      >
        <span>${item.label}</span>
    """
    if item.badge.nonEmpty then
      html"""<span class="rounded-full bg-white/20 px-2 py-0.5 text-xs font-semibold">${item.badge.get}</span>"""
    html"</a>"

  private def footer()(using Html): Unit =
    elem("footer")(
      "class" := "mt-10 border-t border-slate-200 px-1 py-6"
    ):
      div(
        "class" := "flex flex-col gap-4 text-sm text-slate-500 md:flex-row md:items-center md:justify-between"
      ):
        elem("p"):
          text("Generated with Hotmetal in the samples project. Open the exported files to see the full pages in a browser.")

        elem("nav")(
          "class" := "flex flex-wrap items-center gap-4"
        ):
          for link <- footerNav do
            elem("a")(
              "href" := link.href,
              "class" := "font-medium hover:text-slate-900"
            ):
              unescaped(link.label)

  private def fieldLabel(id: String, label: String, required: Boolean)(using
      Html
  ): Unit =
    elem("label")(
      "for" := id,
      "class" := "block text-sm font-semibold text-slate-900"
    ):
      text(label)
      if required then html""" <span class="font-medium text-rose-500">*</span>"""

  private def fieldMessages(helpText: Option[String], error: Option[String])(using
      Html
  ): Unit =
    if error.nonEmpty then html"""<p class="text-sm font-medium text-rose-600">${error.get}</p>"""
    else if helpText.nonEmpty then html"""<p class="text-sm text-slate-500">${helpText.get}</p>"""

  private def isCurrent(href: String, currentPath: String): Boolean =
    href == currentPath || (href != "/" && currentPath.startsWith(href + "/"))

  private def flashToneClass(kind: String): String =
    kind match
      case "success" => "border-emerald-200 bg-emerald-50 text-emerald-900"
      case "warning" => "border-amber-200 bg-amber-50 text-amber-900"
      case "error"   => "border-rose-200 bg-rose-50 text-rose-900"
      case _         => "border-sky-200 bg-sky-50 text-sky-900"

  private def metricToneClass(tone: String): String =
    tone match
      case "positive" => "bg-emerald-50 text-emerald-700"
      case "warning"  => "bg-amber-50 text-amber-700"
      case "neutral"  => "bg-slate-100 text-slate-700"
      case _          => "bg-rose-50 text-rose-700"

  private def statusToneClass(status: String): String =
    status match
      case "Healthy"   => "bg-emerald-50 text-emerald-700"
      case "Pending"   => "bg-amber-50 text-amber-700"
      case "Escalated" => "bg-rose-50 text-rose-700"
      case _           => "bg-slate-100 text-slate-700"

end SampleComponents
