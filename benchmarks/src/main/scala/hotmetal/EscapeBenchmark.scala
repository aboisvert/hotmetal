package hotmetal

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit

@BenchmarkMode(Array(Mode.Throughput))
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 8, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Thread)
class EscapeState:
  val clean = "safe-profile-card-with-plain-text-123"
  val dirty = "<script>alert('XSS')</script>&/profile"

class EscapeBenchmark:

  @Benchmark
  def escapeCleanString(state: EscapeState): CharSequence =
    HtmlUtils.escapeHtml(state.clean)

  @Benchmark
  def escapeDirtyString(state: EscapeState): CharSequence =
    HtmlUtils.escapeHtml(state.dirty)

  @Benchmark
  def escapeDirtyStringWithoutPrecheck(state: EscapeState): CharSequence =
    HtmlUtils.escapeHtml(state.dirty, optimize = false)

end EscapeBenchmark
