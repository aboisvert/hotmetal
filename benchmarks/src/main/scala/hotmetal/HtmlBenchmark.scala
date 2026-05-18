package hotmetal

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole

import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

import scala.compiletime.uninitialized

@BenchmarkMode(Array(Mode.Throughput))
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 8, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Thread)
class HtmlRenderState:
  import Html.*

  val safeUserName = "alice-123"
  val safeBody = "Rendered with Hotmetal and plain text values."
  val unsafeBody = "<script>alert('XSS')</script>"
  val classes = Array("btn", "btn-primary", "shadow-sm", "rounded")

  var nestedItems: Html = uninitialized
  var exportFragment: Html = uninitialized

  @Setup(Level.Trial)
  def setup(): Unit =
    nestedItems = Html:
      var i = 0
      while i < 10 do
        html"<li>Item $i</li>"
        i += 1

    exportFragment = Html:
      html"<section>"
      var i = 0
      while i < 50 do
        html"""<article class="card card-$i"><h2>Card $i</h2><p>$unsafeBody</p></article>"""
        i += 1
      html"</section>"

class HtmlBenchmark:
  import Html.*

  @Benchmark
  def literalHeavyInterpolation(): String =
    val doc = Html:
      html"""<div class="card"><header><h1>Hotmetal</h1></header><main><p>Static fragment</p></main></div>"""
    doc.toString

  @Benchmark
  def safeStringInterpolation(state: HtmlRenderState): String =
    val doc = Html:
      html"""<div class="card"><span>${state.safeUserName}</span><span>${state.safeBody}</span></div>"""
    doc.toString

  @Benchmark
  def escapedStringInterpolation(state: HtmlRenderState): String =
    val doc = Html:
      html"""<div class="card"><span>${state.unsafeBody}</span></div>"""
    doc.toString

  @Benchmark
  def nestedHtmlFragments(state: HtmlRenderState): String =
    val doc = Html:
      html"""<ul>${state.nestedItems}</ul>"""
    doc.toString

  @Benchmark
  def repeatedAttributeGeneration(state: HtmlRenderState): String =
    val doc = Html:
      html"""<button class="${attrValues(state.classes*)}" data-user="${state.safeUserName}">Save</button>"""
    doc.toString

  @Benchmark
  def exportToString(state: HtmlRenderState): String =
    state.exportFragment.toString

  @Benchmark
  def exportToInputStream(state: HtmlRenderState, blackhole: Blackhole): Unit =
    val in = state.exportFragment.toInputStream
    try blackhole.consume(in.readAllBytes())
    finally in.close()

  @Benchmark
  def exportWriteInto(state: HtmlRenderState, blackhole: Blackhole): Unit =
    val out = ByteArrayOutputStream()
    state.exportFragment.writeInto(out)
    blackhole.consume(out.toByteArray)

end HtmlBenchmark
