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

## Experimental Findings (thus far)

- default initial capacity of `256` for StringBuilder
- added a cached rendered string that is invalidated on `append()` and `reset()`

The benchmarks also confirmed that `HtmlUtils.escapeHtml()` should keep its current precheck:

- `escapeCleanString`: `57.8M ops/s`, effectively allocation-free
- `escapeDirtyString`: `11.74M ops/s`, `176 B/op`
- `escapeDirtyStringWithoutPrecheck`: `10.57M ops/s`, `176 B/op`

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
