# Comprehensive Terminal Benchmark Report

## System Information
- **Terminal:** iterm2
- **Host:** Shivangs-MacBook-Pro-Beast.local
- **OS:** Darwin 25.0.0 arm64
- **CPU:** Apple M3 Max
- **Memory:** 128.0 GB
- **Date:** 2025-12-24T08:50:57.116475

## Ansi Benchmarks

### ansi_colors

**16_colors:**
  - sequences: 5120
  - time_ms_mean: 2.944
  - sequences_per_sec: 1739376.572
**256_colors:**
  - sequences: 10240
  - time_ms_mean: 2.965
  - sequences_per_sec: 3453266.453
**truecolor:**
  - sequences: 5960
  - time_ms_mean: 2.995
  - sequences_per_sec: 1989922.584

### ansi_attributes

**attributes:**
  - sequences: 2800
  - time_ms_mean: 3.222

### ansi_cursor

**cursor_movements:**
  - sequences: 400
  - time_ms_mean: 3.107

## Latency Benchmarks

### latency_echo

**echo:**
  - min_ms: 2.819
  - max_ms: 3.025
  - mean_ms: 2.920
  - median_ms: 2.924
  - stdev_ms: 0.089
  - p95_ms: 3.025
  - p99_ms: 3.025
**printf_1chars:**
  - min_ms: 3.165
  - max_ms: 4.666
  - mean_ms: 3.631
  - median_ms: 3.525
  - stdev_ms: 0.607
  - p95_ms: 4.666
  - p99_ms: 4.666
**printf_10chars:**
  - min_ms: 3.139
  - max_ms: 3.617
  - mean_ms: 3.328
  - median_ms: 3.250
  - stdev_ms: 0.187
  - p95_ms: 3.617
  - p99_ms: 3.617
**printf_80chars:**
  - min_ms: 2.911
  - max_ms: 3.514
  - mean_ms: 3.193
  - median_ms: 3.127
  - stdev_ms: 0.236
  - p95_ms: 3.514
  - p99_ms: 3.514
**printf_200chars:**
  - min_ms: 2.919
  - max_ms: 3.719
  - mean_ms: 3.240
  - median_ms: 3.158
  - stdev_ms: 0.297
  - p95_ms: 3.719
  - p99_ms: 3.719

### latency_sequential

- min: 28.378
- max: 31.604
- mean: 29.323
- median: 28.734
- stdev: 1.317
- p50: 28.734
- p90: 31.604
- p95: 31.604
- p99: 31.604
- unit: ms

## Resources Benchmarks

### memory_usage

**processes:**

### cpu_usage

- cpu_before_percent: 10.400
- cpu_after_percent: 13.900
- output_time_ms: 10.543

## Simulation Benchmarks

### simulation_compiler

**compiler_output:**
  - lines: 1500
  - bytes: 63707
  - time_ms_mean: 3.109

### simulation_logs

**log_output:**
  - lines: 1000
  - bytes: 75163
  - time_ms_mean: 3.002

### simulation_git_diff

**git_diff:**
  - lines: 671
  - bytes: 20933
  - time_ms_mean: 3.075

### simulation_htop

**htop_simulation:**
  - lines: 620
  - bytes: 39340
  - time_ms_mean: 3.551

### simulation_vim

**vim_simulation:**
  - lines: 505
  - bytes: 19430
  - time_ms_mean: 3.129

### simulation_mixed

**mixed_workload:**
  - bytes: 13708
  - time_ms_mean: 2.981

## Special Benchmarks

### box_drawing

**box_drawing:**
  - chars: 19000
  - time_ms_mean: 2.860

### block_elements

**block_elements:**
  - chars: 34080
  - time_ms_mean: 2.955

### powerline

**powerline:**
  - chars: 1200
  - time_ms_mean: 3.147

### braille

**braille:**
  - chars: 5120
  - time_ms_mean: 2.967

### math_symbols

**math_symbols:**
  - chars: 5880
  - time_ms_mean: 3.355

## Throughput Benchmarks

### throughput_raw

**1MB:**
  - throughput_mbps_mean: 255.382
  - throughput_mbps_stdev: 32.623
  - time_ms_mean: 3.975
**5MB:**
  - throughput_mbps_mean: 769.004
  - throughput_mbps_stdev: 48.162
  - time_ms_mean: 6.524
**10MB:**
  - throughput_mbps_mean: 1182.021
  - throughput_mbps_stdev: 136.801
  - time_ms_mean: 8.552
**25MB:**
  - throughput_mbps_mean: 1472.652
  - throughput_mbps_stdev: 79.614
  - time_ms_mean: 17.018
**50MB:**
  - throughput_mbps_mean: 1598.692
  - throughput_mbps_stdev: 51.697
  - time_ms_mean: 31.302

### throughput_lines

**1000_lines:**
  - lines_per_sec_mean: 244082.419
  - lines_per_sec_stdev: 48375.507
  - time_ms_mean: 4.239
**5000_lines:**
  - lines_per_sec_mean: 1413624.901
  - lines_per_sec_stdev: 119307.306
  - time_ms_mean: 3.556
**10000_lines:**
  - lines_per_sec_mean: 3084858.232
  - lines_per_sec_stdev: 134051.719
  - time_ms_mean: 3.247
**50000_lines:**
  - lines_per_sec_mean: 8988935.074
  - lines_per_sec_stdev: 387330.777
  - time_ms_mean: 5.571
**100000_lines:**
  - lines_per_sec_mean: 13436825.258
  - lines_per_sec_stdev: 1092073.984
  - time_ms_mean: 7.481

### throughput_varied

**varied_lines_10k:**
  - time_ms_mean: 3.843
  - time_ms_stdev: 0.540

## Unicode Benchmarks

### unicode_emoji

**basic:**
  - chars: 1880
  - bytes: 7440
  - time_ms_mean: 3.192
  - time_ms_stdev: 0.117
  - chars_per_sec: 589033.991
**variation_selectors:**
  - chars: 2950
  - bytes: 9350
  - time_ms_mean: 3.265
  - time_ms_stdev: 0.152
  - chars_per_sec: 903552.257
**zwj_sequences:**
  - chars: 4920
  - bytes: 17460
  - time_ms_mean: 3.247
  - time_ms_stdev: 0.484
  - chars_per_sec: 1515151.516
**skin_tones:**
  - chars: 2100
  - bytes: 8175
  - time_ms_mean: 2.932
  - time_ms_stdev: 0.172
  - chars_per_sec: 716118.587
**flags:**
  - chars: 3000
  - bytes: 12000
  - time_ms_mean: 3.007
  - time_ms_stdev: 0.196
  - chars_per_sec: 997680.460

### unicode_cjk

**cjk:**
  - chars: 8600
  - bytes: 25800
  - time_ms_mean: 3.174
  - chars_per_sec: 2709415.283

### unicode_surrogate

**surrogate_pairs:**
  - chars: 4900
  - bytes: 19500
  - time_ms_mean: 2.871
  - chars_per_sec: 1706549.187

### unicode_combining

**combining_diacritics:**
  - chars: 32400
  - bytes: 48800
  - time_ms_mean: 3.553
**grapheme_clusters:**
  - chars: 8600
  - bytes: 24200
  - time_ms_mean: 3.376
