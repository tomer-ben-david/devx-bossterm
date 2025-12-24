# Comprehensive Terminal Benchmark Report

## System Information
- **Terminal:** bossterm
- **Host:** Shivangs-MacBook-Pro-Beast.local
- **OS:** Darwin 25.0.0 arm64
- **CPU:** Apple M3 Max
- **Memory:** 128.0 GB
- **Date:** 2025-12-24T08:50:41.149391

## Ansi Benchmarks

### ansi_colors

**16_colors:**
  - sequences: 5120
  - time_ms_mean: 3.037
  - sequences_per_sec: 1685740.000
**256_colors:**
  - sequences: 10240
  - time_ms_mean: 3.246
  - sequences_per_sec: 3154797.676
**truecolor:**
  - sequences: 5960
  - time_ms_mean: 3.123
  - sequences_per_sec: 1908273.760

### ansi_attributes

**attributes:**
  - sequences: 2800
  - time_ms_mean: 3.366

### ansi_cursor

**cursor_movements:**
  - sequences: 400
  - time_ms_mean: 3.319

## Latency Benchmarks

### latency_echo

**echo:**
  - min_ms: 3.068
  - max_ms: 4.103
  - mean_ms: 3.637
  - median_ms: 3.680
  - stdev_ms: 0.462
  - p95_ms: 4.103
  - p99_ms: 4.103
**printf_1chars:**
  - min_ms: 2.971
  - max_ms: 3.269
  - mean_ms: 3.112
  - median_ms: 3.123
  - stdev_ms: 0.127
  - p95_ms: 3.269
  - p99_ms: 3.269
**printf_10chars:**
  - min_ms: 2.770
  - max_ms: 3.281
  - mean_ms: 3.041
  - median_ms: 3.046
  - stdev_ms: 0.201
  - p95_ms: 3.281
  - p99_ms: 3.281
**printf_80chars:**
  - min_ms: 2.875
  - max_ms: 4.241
  - mean_ms: 3.417
  - median_ms: 3.043
  - stdev_ms: 0.649
  - p95_ms: 4.241
  - p99_ms: 4.241
**printf_200chars:**
  - min_ms: 2.834
  - max_ms: 3.082
  - mean_ms: 2.911
  - median_ms: 2.885
  - stdev_ms: 0.100
  - p95_ms: 3.082
  - p99_ms: 3.082

### latency_sequential

- min: 28.158
- max: 31.629
- mean: 29.619
- median: 29.585
- stdev: 1.314
- p50: 29.585
- p90: 31.629
- p95: 31.629
- p99: 31.629
- unit: ms

## Resources Benchmarks

### memory_usage

**processes:**
  - java: {'memory_mb': 726.1875}
  - BossTerm: {'memory_mb': 943.671875}

### cpu_usage

- cpu_before_percent: 6.900
- cpu_after_percent: 6.900
- output_time_ms: 11.233

## Simulation Benchmarks

### simulation_compiler

**compiler_output:**
  - lines: 1500
  - bytes: 63773
  - time_ms_mean: 3.474

### simulation_logs

**log_output:**
  - lines: 1000
  - bytes: 75227
  - time_ms_mean: 2.886

### simulation_git_diff

**git_diff:**
  - lines: 683
  - bytes: 21318
  - time_ms_mean: 2.933

### simulation_htop

**htop_simulation:**
  - lines: 620
  - bytes: 39340
  - time_ms_mean: 3.092

### simulation_vim

**vim_simulation:**
  - lines: 505
  - bytes: 19460
  - time_ms_mean: 3.053

### simulation_mixed

**mixed_workload:**
  - bytes: 13708
  - time_ms_mean: 3.146

## Special Benchmarks

### box_drawing

**box_drawing:**
  - chars: 19000
  - time_ms_mean: 3.082

### block_elements

**block_elements:**
  - chars: 34080
  - time_ms_mean: 2.973

### powerline

**powerline:**
  - chars: 1200
  - time_ms_mean: 2.833

### braille

**braille:**
  - chars: 5120
  - time_ms_mean: 3.099

### math_symbols

**math_symbols:**
  - chars: 5880
  - time_ms_mean: 3.011

## Throughput Benchmarks

### throughput_raw

**1MB:**
  - throughput_mbps_mean: 363.587
  - throughput_mbps_stdev: 29.632
  - time_ms_mean: 2.766
**5MB:**
  - throughput_mbps_mean: 955.451
  - throughput_mbps_stdev: 145.294
  - time_ms_mean: 5.334
**10MB:**
  - throughput_mbps_mean: 1186.530
  - throughput_mbps_stdev: 98.400
  - time_ms_mean: 8.474
**25MB:**
  - throughput_mbps_mean: 1571.477
  - throughput_mbps_stdev: 100.667
  - time_ms_mean: 15.960
**50MB:**
  - throughput_mbps_mean: 1645.107
  - throughput_mbps_stdev: 112.191
  - time_ms_mean: 30.498

### throughput_lines

**1000_lines:**
  - lines_per_sec_mean: 287777.911
  - lines_per_sec_stdev: 46080.929
  - time_ms_mean: 3.565
**5000_lines:**
  - lines_per_sec_mean: 1500561.200
  - lines_per_sec_stdev: 89871.620
  - time_ms_mean: 3.342
**10000_lines:**
  - lines_per_sec_mean: 2923777.726
  - lines_per_sec_stdev: 265942.354
  - time_ms_mean: 3.444
**50000_lines:**
  - lines_per_sec_mean: 9920943.173
  - lines_per_sec_stdev: 455066.757
  - time_ms_mean: 5.048
**100000_lines:**
  - lines_per_sec_mean: 14132534.592
  - lines_per_sec_stdev: 1222547.159
  - time_ms_mean: 7.119

### throughput_varied

**varied_lines_10k:**
  - time_ms_mean: 3.860
  - time_ms_stdev: 0.369

## Unicode Benchmarks

### unicode_emoji

**basic:**
  - chars: 1880
  - bytes: 7440
  - time_ms_mean: 3.177
  - time_ms_stdev: 0.204
  - chars_per_sec: 591681.783
**variation_selectors:**
  - chars: 2950
  - bytes: 9350
  - time_ms_mean: 2.926
  - time_ms_stdev: 0.116
  - chars_per_sec: 1008139.275
**zwj_sequences:**
  - chars: 4920
  - bytes: 17460
  - time_ms_mean: 3.305
  - time_ms_stdev: 0.479
  - chars_per_sec: 1488860.150
**skin_tones:**
  - chars: 2100
  - bytes: 8175
  - time_ms_mean: 3.169
  - time_ms_stdev: 0.136
  - chars_per_sec: 662753.225
**flags:**
  - chars: 3000
  - bytes: 12000
  - time_ms_mean: 2.909
  - time_ms_stdev: 0.121
  - chars_per_sec: 1031317.748

### unicode_cjk

**cjk:**
  - chars: 8600
  - bytes: 25800
  - time_ms_mean: 2.873
  - chars_per_sec: 2993387.108

### unicode_surrogate

**surrogate_pairs:**
  - chars: 4900
  - bytes: 19500
  - time_ms_mean: 3.390
  - chars_per_sec: 1445527.167

### unicode_combining

**combining_diacritics:**
  - chars: 32400
  - bytes: 48800
  - time_ms_mean: 3.206
**grapheme_clusters:**
  - chars: 8600
  - bytes: 24200
  - time_ms_mean: 2.839
