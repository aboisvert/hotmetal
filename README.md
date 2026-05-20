# Hotmetal

Hotmetal is a Scala library for generating HTML with an `html` string interpolator and a lightweight DSL. It uses a mutable `Html` builder context so fragments write directly into a `StringBuilder` instead of allocating many intermediate strings.

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

## Tutorial

Start with the narrative walkthrough in [core/src/test/scala/hotmetal/Tutorial.scala](core/src/test/scala/hotmetal/Tutorial.scala). It explains the `Html` context, components written as `val`s and `def`s, and how to compose dynamic pages.

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

## Status

This project is at `0.1.0-SNAPSHOT` and is under active development. Feedback and contributions are welcome.
