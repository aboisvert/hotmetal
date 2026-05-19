# Hotmetal

Hotmetal is a Scala library for generating HTML with an `html` string interpolator and a lightweight DSL. It uses a mutable `Html` builder context so fragments write directly into a `StringBuilder` instead of allocating many intermediate strings.

## Tutorial

Start with the narrative walkthrough in [core/src/test/scala/hotmetal/Tutorial.scala](core/src/test/scala/hotmetal/Tutorial.scala). It explains the `Html` context, components written as `val`s and `def`s, and how to compose dynamic pages.

## Requirements

- [Scala](https://www.scala-lang.org/) 3.3.7
- [Java](https://openjdk.org/) 21
- [sbt](https://www.scala-sbt.org/)

## Quick example

```scala
import hotmetal.Html
import hotmetal.Html.*

given Html = new Html()

html"""
  <div class="example">
    <p>Some text</p>
    <p>2 + 2 is ${ 2 + 2}</p>
  </div>
"""
```

The `Html` context is typically created at the root of a document (or for a standalone fragment you plan to cache and reuse).

## Project layout

| Module | Path | Description |
|--------|------|-------------|
| **core** | `core/` | Main library (`hotmetal`) — `Html` interpolator, DSL, and element helpers |
| **samples** | `samples/` | Example pages (dashboard, checkout, settings, landing) built with Hotmetal |
| **benchmarks** | `benchmarks/` | JMH benchmarks (`hotmetal-benchmarks`) |

## Building and testing

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
