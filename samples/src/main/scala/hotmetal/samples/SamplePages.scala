package hotmetal.samples

import hotmetal.Html

object SamplePages:
  def dashboard(): Html = DashboardPage.render()

  def checkout(): Html = CheckoutPage.render()

  def settings(): Html = SettingsPage.render()

  def landing(): Html = LandingPage.render()

  def landingInto(using Html): Unit = LandingPage.renderInto

  def all: Seq[SamplePage] =
    Seq(
      SamplePage("dashboard", "Operations dashboard", () => dashboard()),
      SamplePage("checkout", "Checkout workspace", () => checkout()),
      SamplePage("settings", "Account settings", () => settings()),
      SamplePage("landing", "Launch with Hotmetal", () => landing())
    )
