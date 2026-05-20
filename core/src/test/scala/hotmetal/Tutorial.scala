package hotmetal

import munit.FunSuite

class Tutorial extends FunSuite:
  import Html.*

  /** WHAT'S WITH THE NAME???
    *
    * Hotmetal is a backronym and wanna-be portemanteau for Html.
    *
    * Oh sure, it could be written as HoTMetaL to highlight the letters H, T, M and L but it's
    * tedious to read and write weirdly capitalized words like that, so we'll stick to Hotmetal.
    *
    * (Yes, HoTMetaL was also the name of a cool WYSIWYG HTML editor in the 90s, so let's pretend
    * we're paying homage to it as well. The product ain't cool anymore but the name lives on!)
    */

  /** WHAT IS IT???
    *
    * Hotmetal is a library that provides an `html` interpolator as well as a lightweight DSL for
    * generating Html content (both static and dynamic) in Scala.
    *
    * Here's a first example containing literal tags:
    */

  given Html = new Html()

  html"""
    <div class="example">
      <p>Some text</p>
    </div>
  """

  /** In the example above, the given 'Html' (capitalized) is a context that allows `html`
    * (lowercased) interpolated fragments to append their content.
    *
    * The Html context is a builder that avoids creating many temporary objects. The generated
    * fragments directly write into a java.lang.StringBuilder that's part of the context.
    *
    * The Html context is typically created only at the root of a Html document.
    */

  given UserContext = new UserContext { def isLoggedIn: Boolean = true }

  Html:
    html"""
    <html>
      <head>
        $tailwindCss
        $htmxJs
      </head>
      <body>
        $standardHeader
        $mainSection
        $standardFooter
      </body>
    </html>
    """

  /** The code above is valid Hotmetal!
    *
    * All the elements such as `htmlRoot`, `head`, `body`, `standardHeader` are all fragments that
    * are defined either as `val`s (static html) or `def`s. (dynamic)
    *
    * Here is what `standardHeader` could look like:
    */

  def standardHeader(using user: UserContext, html: Html): Unit =
    html"""
      <header class="flex my-3">
        <div> ... </div>
        <nav>
          <a href="/">Home</a>
          <a href="/about">About</a>
          <a href="/contact">Contact</a>
        </nav>
        <div> $loginLogoutButton </div>
      </header>
    """

  // Dummy declarations
  def tailwindCss(using Html) = ???
  def htmxJs(using Html) = ???
  def mainSection(using Html) = ???
  def standardFooter(using Html) = ???

  /** Components are thus written as normal Scala code, although they require the Html context to be
    * passed in.
    */

  trait UserContext:
    def isLoggedIn: Boolean

  def loginLogoutButton(using user: UserContext, html: Html): Unit =
    html"""
      <button class="ui:button highlighted">${
        if !user.isLoggedIn then "Login" else "Logout"
      }</button>"""

    /** Not only can Html interpolation use any control structure (`if`, `for`, ...) but the code
      * can call other Html-generating fragments:
      *
      * Here's an example of a fragment that uses a `for` loop to generate a list of items:
      */
    html"""
      <ul>${for i <- 1 to 10 do //
        html"<li>Bullet $i</li>"
      }</div>
    """

    /** ... which is the same as if it had called a def producting the same Html:
      */

    def bullet(int: Int)(using Html): Unit =
      html"<li>Bullet $int</li>"

    html"""
      <ul>${for i <- 1 to 10 do //
        bullet(i)
      }</div>
    """

    /** You get the idea.
      *
      * Well, that's it for our quick introduction to Hotmetal. The library is still in early stages
      * of development and we're looking for feedback on how to improve it.  Let us know what you
      * think!
      */
