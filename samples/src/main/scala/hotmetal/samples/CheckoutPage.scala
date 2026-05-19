package hotmetal.samples

import hotmetal.Html
import hotmetal.Html.*
import SampleComponents.*
import SamplePageNav.primaryNav

object CheckoutPage:
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

  def render(): Html =
    Html:
      baseLayout(
        title = "Checkout workspace",
        currentPath = "/checkout",
        navItems = primaryNav,
        flashes = checkoutFlashes
      ):
        html"""
        <main
          class="grid gap-6 lg:grid-cols-[minmax(0,1.1fr)_420px]"
          x-data="{ promoOpen: false, quantity: 3 }"
        >
          <section class="space-y-6">
            <div>
              <p class="text-sm font-semibold uppercase tracking-[0.2em] text-slate-400">Checkout</p>
              <h1 class="mt-2 text-3xl font-black tracking-tight text-slate-900">Compose a realistic purchase flow</h1>
              <p class="mt-3 max-w-2xl text-sm leading-6 text-slate-600">
                This sample uses shared field components, repeated product cards, and Alpine controls for promo visibility and quantity previews.
              </p>
            </div>
            ${productList(checkoutProducts)}
            <section class="rounded-3xl border border-slate-200 bg-white p-6 shadow-sm">
              <div class="grid gap-5 md:grid-cols-2">
                ${textField(
                  TextField(
                    id = "shipping-name",
                    name = "shippingName",
                    label = "Contact name",
                    value = "Jamie Rivera",
                    placeholder = "Add the shipping contact",
                    helpText = Some("This uses the shared text field renderer."),
                    required = true
                  )
                )}
                ${textField(
                  TextField(
                    id = "shipping-email",
                    name = "shippingEmail",
                    label = "Email address",
                    value = "jamie@example.com",
                    inputType = "email",
                    placeholder = "Add an order contact",
                    required = true
                  )
                )}
                ${textField(
                  TextField(
                    id = "address",
                    name = "address",
                    label = "Street address",
                    value = "1208 Benchmark Way",
                    placeholder = "Start typing an address",
                    required = true
                  )
                )}
                ${selectField(
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
                )}
              </div>
              ${checkboxField(
                CheckboxField(
                  id = "billing-same",
                  name = "billingSame",
                  label = "Billing address matches shipping",
                  checked = true,
                  helpText = Some("Useful for demonstrating repeated conditional field groups.")
                )
              )}
              ${radioGroup(
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
              )}
            </section>
          </section>
          <aside class="space-y-6">
            <section class="rounded-3xl border border-slate-200 bg-white p-6 shadow-sm">
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm font-semibold text-slate-500">Order total</p>
                  <p class="mt-1 text-3xl font-black tracking-tight text-slate-900">${"$537"}</p>
                </div>
                <button
                  type="button"
                  class="rounded-2xl border border-slate-200 px-4 py-2 text-sm font-medium text-slate-700"
                  @click="promoOpen = !promoOpen"
                >
                  Promo code
                </button>
              </div>
              <div class="mt-4 grid gap-3" x-show="promoOpen" x-transition>
                ${textField(
                  TextField(
                    id = "promo",
                    name = "promo",
                    label = "Promotion code",
                    value = "HOTMETAL15",
                    placeholder = "Enter a code"
                  )
                )}
                <button type="button" class="rounded-2xl bg-brand-500 px-4 py-3 text-sm font-semibold text-white">Apply code</button>
              </div>
              <div class="mt-6 rounded-2xl bg-slate-50 p-4 text-sm text-slate-600">
                <p>Package count preview: <span class="font-semibold text-slate-900" x-text="quantity"></span></p>
                <div class="mt-3 flex items-center gap-3">
                  <button type="button" class="rounded-xl border border-slate-200 px-3 py-2" @click="quantity = Math.max(1, quantity - 1)">-</button>
                  <button type="button" class="rounded-xl border border-slate-200 px-3 py-2" @click="quantity = quantity + 1">+</button>
                  <span class="text-xs uppercase tracking-[0.2em] text-slate-400">Static Alpine demo</span>
                </div>
              </div>
            </section>
          </aside>
        </main>
        """
