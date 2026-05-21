# Hotmetal

Hotmetal is a Scala library for generating HTML with an `html` string interpolator and a lightweight DSL. It uses a mutable `Html` builder context so fragments write directly into a `StringBuilder` instead of allocating many intermediate strings.

[Github Home Page](https://github.com/aboisvert/hotmetal)

## Quick example

```scala
import hotmetal.Html
import hotmetal.Html.*

// ficticious imports
import com.example.app.context.Location
import com.example.app.context.{TimeZone, contextTimeZone}
import com.example.app.context.{User, userName}

// The `Html` given is a capability to generate (append) HTML content into a contextual buffer
def mainContent(using Html, User, Location, TimeZone) = html"""
  <main>
    <h1 class="text-3xl font-bold tracking-tight text-slate-900 mb-4">
      Welcome ${userName}!
    </h1>

    <article class="rounded-3xl border border-slate-200 bg-white p-5 shadow-sm">
      <title>Today<title>
      <p>Today is ${(LocalDate.now(contextTimeZone))}</p>
    </article>

    <article class="rounded-3xl border border-slate-200 bg-white p-5 shadow-sm">
      <title>Current weather<title>
      ${weatherComponent()}
    </article>
  </main>
"""
```

The `Html` context is typically created at the root of a document (or for a standalone fragment you plan to cache and reuse).

## Features

- High-performance, low-allocation rendering model designed for server-side HTML generation.
- Macro-based html interpolator for efficient HTML generation with direct StringBuilder writes.
- Automatic escaping for interpolated strings and text values to reduce XSS risk.
- Lightweight component model using ordinary Scala defs, vals, loops, and conditionals.  It's "plain old Scala".
- Supports composable Html fragments for reusable (static/dynamic) layouts, components, and cached snippets.
- Optional typed DSL helpers for common HTML elements and attributes.
- `Stringify` typeclass allows explicit support for rendering custom datatypes (e.g. dates, numbers, currencies, ...)
- Output helpers for rendering to String, CharSequence, InputStream, or OutputStream.

The project also features:
- Sample pages demonstrating layouts, forms, cards, tables, navigation, and interactive Tailwind/Alpine UI patterns.
- JMH benchmark module for measuring and tracking rendering performance.

## Tutorial

Start with the narrative walkthrough in [core/src/test/scala/hotmetal/Tutorial.scala](https://github.com/aboisvert/hotmetal/blob/main/core/src/test/scala/hotmetal/Tutorial.scala). It explains the `Html` context, components written as `val`s and `def`s, and how to compose dynamic pages.

Then take a look at some sample pages,
- [samples/src/main/scala/hotmetal/samples/CheckoutPage.scala](https://github.com/aboisvert/hotmetal/blob/main/samples/src/main/scala/hotmetal/samples/CheckoutPage.scala) [generated HTML](https://aboisvert.github.io/hotmetal/samples/generated-pages/checkout.html)
- [samples/src/main/scala/hotmetal/samples/DashboardPage.scala](https://github.com/aboisvert/hotmetal/blob/main/samples/src/main/scala/hotmetal/samples/DashboardPage.scala) [generated HTML](https://aboisvert.github.io/hotmetal/samples/generated-pages/dashboard.html)
- [samples/src/main/scala/hotmetal/samples/LandingPage.scala](https://github.com/aboisvert/hotmetal/blob/main/samples/src/main/scala/hotmetal/samples/LandingPage.scala) [generated HTML](https://aboisvert.github.io/hotmetal/samples/generated-pages/landing.html)
- [samples/src/main/scala/hotmetal/samples/SamplePageNav.scala](https://github.com/aboisvert/hotmetal/blob/main/samples/src/main/scala/hotmetal/samples/SamplePageNav.scala) [generated HTML](https://aboisvert.github.io/hotmetal/samples/generated-pages/settings.html)

## Requirements

- [Scala](https://www.scala-lang.org/) 3.3.7
- [Java](https://openjdk.org/) 21
- [sbt](https://www.scala-sbt.org/)


## Project layout

| Module | Path | Description |
|--------|------|-------------|
| **core** | `core/` | Main library (`hotmetal`) — `Html` interpolator, DSL, and element helpers |
| **samples** | `samples/` | Example pages (dashboard, checkout, settings, landing) built with Hotmetal |
| **benchmarks** | `benchmarks/` | JMH benchmarks (`hotmetal-benchmarks`) |

## Using Hotmetal as a dependency

Artifacts are published to [GitHub Packages](https://github.com/aboisvert/hotmetal/packages) as `com.github.aboisvert:hotmetal_3:<version>`.

A release jar is also available on GitHub (no token required):

`https://github.com/aboisvert/hotmetal/releases/download/v0.1.0/hotmetal_3-0.1.0.jar`

### sbt

#### Maven dependency

Add the resolver and dependency in `build.sbt` (or `project/*.sbt`):

```scala
resolvers += "GitHub Packages" at "https://maven.pkg.github.com/aboisvert/hotmetal"

credentials += Credentials(
  "GitHub Package Registry",
  "maven.pkg.github.com",
  sys.env.getOrElse("GITHUB_ACTOR", sys.env.getOrElse("GITHUB_USERNAME", "github")),
  sys.env.getOrElse("GITHUB_TOKEN", "")
)

libraryDependencies += "com.github.aboisvert" %% "hotmetal" % "<version>"
```

Replace `<version>` with a released version (for example `0.1.0`). GitHub Packages may require authentication even for reads; provide a personal access token with at least `read:packages` scope (and `repo` if the package is private).

#### Direct jar dependency

Add to `build.sbt` (downloads the jar on first compile):

```scala
val hotmetalJarUrl =
  "https://github.com/aboisvert/hotmetal/releases/download/v0.1.0/hotmetal_3-0.1.0.jar"

lazy val downloadHotmetalJar = taskKey[File]("Download Hotmetal jar")

downloadHotmetalJar := {
  val jar = (Compile / target).value / "hotmetal_3-0.1.0.jar"
  if (!jar.exists()) {
    import scala.sys.process._
    jar.getParentFile.mkdirs()
    url(hotmetalJarUrl) #> jar !
  }
  jar
}

Compile / unmanagedJars ++= {
  Seq(Attributed.blank(downloadHotmetalJar.value))
}
```

### scala-cli

#### Maven dependency

In your script or `project.scala`:

```scala
//> using scala 3.3.7
//> using repository https://maven.pkg.github.com/aboisvert/hotmetal
//> using dep com.github.aboisvert::hotmetal:<version>
```

Replace `<version>` with a released version (for example `0.1.0`). Configure Coursier credentials in `~/.config/coursier/credentials.properties` (or via environment variables):

```properties
maven.pkg.github.com=${GITHUB_ACTOR:-github}:${GITHUB_TOKEN}
```

#### Direct jar dependency

In your script or `project.scala`:

```scala
//> using scala 3.3.7
//> using jar https://github.com/aboisvert/hotmetal/releases/download/v0.1.0/hotmetal_3-0.1.0.jar
```

### Mill

#### Maven dependency

In `build.mill`:

```scala
import mill._
import mill.scalalib._

def hotmetalDep = ivy"com.github.aboisvert::hotmetal:<version>"

// In your module:
def repositoriesTask = Task {
  Seq(
    coursier.maven.MavenRepository("https://maven.pkg.github.com/aboisvert/hotmetal")
  ) ++ coursier.Resolve.defaultRepositories
}

def coursierCredentials = coursier.Credentials(
  "maven.pkg.github.com",
  sys.env.getOrElse("GITHUB_ACTOR", sys.env.getOrElse("GITHUB_USERNAME", "github")),
  sys.env.getOrElse("GITHUB_TOKEN", "")
)
```

Replace `<version>` with a released version (for example `0.1.0`). Set `GITHUB_TOKEN` in the environment before running Mill.

#### Direct jar dependency

In `build.mill` (downloads the jar on first build):

```scala
import mill._
import mill.scalalib._

object app extends ScalaModule {
  def scalaVersion = "3.3.7"

  def unmanagedClasspath = Task {
    val dest = Task.dest / "hotmetal_3-0.1.0.jar"
    if (!os.exists(dest)) {
      os.write(
        dest,
        requests.get.stream(
          "https://github.com/aboisvert/hotmetal/releases/download/v0.1.0/hotmetal_3-0.1.0.jar"
        )
      )
    }
    Seq(PathRef(dest))
  }
}
```

## Building and testing

It is recommended you use ASDF and install system dependencies using `just install-dependencies`.

From the repository root:

```bash
sbt test              # run tests in all aggregated modules
sbt core/test         # run core tests only
sbt samples/compile   # compile sample pages
```

To run JMH benchmarks:

```bash
sbt bench/jmh:run
```

## Releasing

Releases use [sbt-release](https://github.com/sbt/sbt-release). The version is managed in [`version.sbt`](version.sbt) at the repository root.

Prerequisites:

- A clean git working tree (commit or stash local changes first).
- `GITHUB_ACTOR` (or `GITHUB_USERNAME`) and `GITHUB_TOKEN` in the environment. The token needs `write:packages` (and `repo` if publishing from a private repository).

From the repository root:

```bash
export GITHUB_ACTOR=your-github-username
export GITHUB_TOKEN=ghp_...

sbt release
```

The default release process runs tests, sets the release version in `version.sbt`, tags the commit (`v<version>`), publishes the `core` module to GitHub Packages, bumps to the next snapshot version, and pushes commits and tags.

Non-interactive release with defaults:

```bash
sbt "release with-defaults"
```

To publish manually without a full release:

```bash
sbt core/publish
```

## Status

This project has been used in production for several high-volume websites (including https://fanstake.com).  It is reliable and stable however the pre-1.0 version number indicates that no backward/forward source compatibility promise is made at this time.

Feedback and contributions are welcome.
