package hotmetal

import hotmetal.samples.SamplePage
import hotmetal.samples.SamplePages
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
class FullPageState:
  var pages: Array[SamplePage] = uninitialized
  var largestPage: Html = uninitialized
  var reusableLanding: Html = uninitialized

  @Setup(Level.Trial)
  def setup(): Unit =
    pages = SamplePages.all.toArray
    largestPage = SamplePages.landing()
    reusableLanding = new Html()

class FullPageBenchmark:
  @Benchmark
  def dashboardToString(): String =
    SamplePages.dashboard().toString

  @Benchmark
  def checkoutToString(): String =
    SamplePages.checkout().toString

  @Benchmark
  def settingsToString(): String =
    SamplePages.settings().toString

  @Benchmark
  def landingToString(): String =
    SamplePages.landing().toString

  @Benchmark
  def landingToStringWithReuse(state: FullPageState): String =
    state.reusableLanding.reset()
    SamplePages.landingInto(using state.reusableLanding)
    state.reusableLanding.toString

  @Benchmark
  def allPagesToString(state: FullPageState, blackhole: Blackhole): Unit =
    var i = 0
    while i < state.pages.length do
      blackhole.consume(state.pages(i).render().toString)
      i += 1

  @Benchmark
  def landingWriteInto(state: FullPageState, blackhole: Blackhole): Unit =
    val out = ByteArrayOutputStream()
    state.largestPage.writeInto(out)
    blackhole.consume(out.toByteArray)

