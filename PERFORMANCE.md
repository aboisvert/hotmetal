# Performance Notes

This project includes a dedicated JMH benchmark project for measuring the runtime cost of
Html rendering and escaping.

## Run Benchmarks

- `just bench`
  Runs the full benchmark suite with the GC profiler enabled.
- `just bench-quick`
  Runs a shorter smoke benchmark with the GC profiler enabled.
- `just bench-json`
  Runs the full suite and writes machine-readable results to `benchmarks/results/latest.json`.

All numbers below were collected on:

- Java `21.0.11`
- sbt `1.11.7`
- Scala `3.3.7`
- JMH `1.37`

## Experimental Findings

The latest full run (`just bench-json`) confirms a few current trade-offs:

- the default `StringBuilder` capacity remains `256`
- `Html.writeInto()` and `Html.toInputStream()` now encode directly from the backing builder instead of calling `toString.getBytes(...)`
- `Html.reset()` is worth using when the same `Html` instance can be safely reused on a hot path

Representative numbers from the latest run:

- `escapeCleanString`: `59.60M ops/s`, effectively allocation-free
- `escapeDirtyString`: `11.52M ops/s`, `176 B/op`
- `escapeDirtyStringWithoutPrecheck`: `11.59M ops/s`, `176 B/op`
- `exportToString`: `2.90M ops/s`, `5,944 B/op`
- `exportToInputStream`: `129.2K ops/s`, `24,152 B/op`
- `exportWriteInto`: `134.3K ops/s`, `18,280 B/op`
- `landingToString`: `127.1K ops/s`, `84,136 B/op`
- `landingToStringWithReuse`: `246.2K ops/s`, `20,776 B/op`
- `landingWriteInto`: `40.7K ops/s`, `67,984 B/op`

The escape benchmarks still support keeping the current precheck in `HtmlUtils.escapeHtml()`: it makes clean-string rendering effectively allocation-free and does not introduce a meaningful penalty on already-dirty input.

## Regression Workflow

When changing rendering behavior, rerun:

```bash
just test
just bench-quick
```

For changes that may affect throughput or allocation behavior, rerun:

```bash
just bench
just bench-json
```

When comparing two revisions, focus on both `ops/s` and `gc.alloc.rate.norm`, since Hotmetal is
primarily sensitive to fixed allocation overhead.
