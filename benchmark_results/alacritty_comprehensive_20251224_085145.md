# Comprehensive Terminal Benchmark Report

## System Information
- **Terminal:** alacritty
- **Host:** Shivangs-MacBook-Pro-Beast.local
- **OS:** Darwin 25.0.0 arm64
- **CPU:** Apple M3 Max
- **Memory:** 128.0 GB
- **Date:** 2025-12-24T08:51:29.232977

## Ansi Benchmarks

### ansi_colors

**16_colors:**
  - sequences: 5120
  - time_ms_mean: 3.481
  - sequences_per_sec: 1470859.288
**256_colors:**
  - sequences: 10240
  - time_ms_mean: 3.634
  - sequences_per_sec: 2818206.317
**truecolor:**
  - sequences: 5960
  - time_ms_mean: 3.168
  - sequences_per_sec: 1881590.260

### ansi_attributes

**attributes:**
  - sequences: 2800
  - time_ms_mean: 3.191

### ansi_cursor

**cursor_movements:**
  - sequences: 400
  - time_ms_mean: 3.305

## Latency Benchmarks

### latency_echo

**echo:**
  - min_ms: 3.227
  - max_ms: 3.948
  - mean_ms: 3.546
  - median_ms: 3.336
  - stdev_ms: 0.345
  - p95_ms: 3.948
  - p99_ms: 3.948
**printf_1chars:**
  - min_ms: 3.054
  - max_ms: 3.751
  - mean_ms: 3.527
  - median_ms: 3.706
  - stdev_ms: 0.298
  - p95_ms: 3.751
  - p99_ms: 3.751
**printf_10chars:**
  - min_ms: 3.272
  - max_ms: 3.829
  - mean_ms: 3.460
  - median_ms: 3.345
  - stdev_ms: 0.236
  - p95_ms: 3.829
  - p99_ms: 3.829
**printf_80chars:**
  - min_ms: 3.082
  - max_ms: 3.659
  - mean_ms: 3.425
  - median_ms: 3.554
  - stdev_ms: 0.241
  - p95_ms: 3.659
  - p99_ms: 3.659
**printf_200chars:**
  - min_ms: 3.451
  - max_ms: 3.850
  - mean_ms: 3.684
  - median_ms: 3.689
  - stdev_ms: 0.154
  - p95_ms: 3.850
  - p99_ms: 3.850

### latency_sequential

- min: 30.814
- max: 35.553
- mean: 33.268
- median: 33.119
- stdev: 1.916
- p50: 33.119
- p90: 35.553
- p95: 35.553
- p99: 35.553
- unit: ms

## Resources Benchmarks

### memory_usage

**processes:**

### cpu_usage

- cpu_before_percent: 9.000
- cpu_after_percent: 9.300
- output_time_ms: 10.676

## Simulation Benchmarks

### simulation_compiler

**compiler_output:**
  - lines: 1500
  - bytes: 63789
  - time_ms_mean: 3.476

### simulation_logs

**log_output:**
  - lines: 1000
  - bytes: 75295
  - time_ms_mean: 3.942

### simulation_git_diff

**git_diff:**
  - lines: 562
  - bytes: 17252
  - time_ms_mean: 3.485

### simulation_htop

**htop_simulation:**
  - lines: 620
  - bytes: 39340
  - time_ms_mean: 3.724

### simulation_vim

**vim_simulation:**
  - lines: 505
  - bytes: 19445
  - time_ms_mean: 3.535

### simulation_mixed

**mixed_workload:**
  - bytes: 13708
  - time_ms_mean: 3.667

## Special Benchmarks

### box_drawing

**box_drawing:**
  - chars: 19000
  - time_ms_mean: 3.562

### block_elements

**block_elements:**
  - chars: 34080
  - time_ms_mean: 3.640

### powerline

**powerline:**
  - chars: 1200
  - time_ms_mean: 3.355

### braille

**braille:**
  - chars: 5120
  - time_ms_mean: 3.666

### math_symbols

**math_symbols:**
  - chars: 5880
  - time_ms_mean: 3.465

## Throughput Benchmarks

### throughput_raw

**1MB:**
  - throughput_mbps_mean: 232.566
  - throughput_mbps_stdev: 33.379
  - time_ms_mean: 4.383
**5MB:**
  - throughput_mbps_mean: 769.664
  - throughput_mbps_stdev: 113.302
  - time_ms_mean: 6.613
**10MB:**
  - throughput_mbps_mean: 1060.835
  - throughput_mbps_stdev: 53.618
  - time_ms_mean: 9.446
**25MB:**
  - throughput_mbps_mean: 1399.533
  - throughput_mbps_stdev: 43.499
  - time_ms_mean: 17.877
**50MB:**
  - throughput_mbps_mean: 1632.796
  - throughput_mbps_stdev: 85.408
  - time_ms_mean: 30.688

### throughput_lines

**1000_lines:**
  - lines_per_sec_mean: 266144.119
  - lines_per_sec_stdev: 51337.637
  - time_ms_mean: 3.912
**5000_lines:**
  - lines_per_sec_mean: 1432967.637
  - lines_per_sec_stdev: 33171.782
  - time_ms_mean: 3.491
**10000_lines:**
  - lines_per_sec_mean: 2744678.698
  - lines_per_sec_stdev: 142118.541
  - time_ms_mean: 3.651
**50000_lines:**
  - lines_per_sec_mean: 7882232.356
  - lines_per_sec_stdev: 559879.344
  - time_ms_mean: 6.368
**100000_lines:**
  - lines_per_sec_mean: 13056129.520
  - lines_per_sec_stdev: 1126844.517
  - time_ms_mean: 7.708

### throughput_varied

**varied_lines_10k:**
  - time_ms_mean: 4.332
  - time_ms_stdev: 0.642

## Unicode Benchmarks

### unicode_emoji

**basic:**
  - chars: 1880
  - bytes: 7440
  - time_ms_mean: 3.200
  - time_ms_stdev: 0.189
  - chars_per_sec: 587526.035
**variation_selectors:**
  - chars: 2950
  - bytes: 9350
  - time_ms_mean: 3.561
  - time_ms_stdev: 0.151
  - chars_per_sec: 828506.137
**zwj_sequences:**
  - chars: 4920
  - bytes: 17460
  - time_ms_mean: 3.848
  - time_ms_stdev: 0.523
  - chars_per_sec: 1278478.299
**skin_tones:**
  - chars: 2100
  - bytes: 8175
  - time_ms_mean: 3.243
  - time_ms_stdev: 0.072
  - chars_per_sec: 647536.905
**flags:**
  - chars: 3000
  - bytes: 12000
  - time_ms_mean: 3.548
  - time_ms_stdev: 0.256
  - chars_per_sec: 845574.575

### unicode_cjk

**cjk:**
  - chars: 8600
  - bytes: 25800
  - time_ms_mean: 4.026
  - chars_per_sec: 2136115.249

### unicode_surrogate

**surrogate_pairs:**
  - chars: 4900
  - bytes: 19500
  - time_ms_mean: 3.261
  - chars_per_sec: 1502591.261

### unicode_combining

**combining_diacritics:**
  - chars: 32400
  - bytes: 48800
  - time_ms_mean: 3.240
**grapheme_clusters:**
  - chars: 8600
  - bytes: 24200
  - time_ms_mean: 3.498
