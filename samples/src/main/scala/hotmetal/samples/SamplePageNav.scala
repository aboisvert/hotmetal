package hotmetal.samples

object SamplePageNav:
  val primaryNav = Seq(
    NavItem("Dashboard", "dashboard.html"),
    NavItem("Checkout", "checkout.html", badge = Some("Live")),
    NavItem("Settings", "settings.html"),
    NavItem("Launch", "landing.html")
  )
