# Comprehensive Terminal Benchmark Report

## System Information
- **Terminal:** terminal
- **Host:** Shivangs-MacBook-Pro-Beast.local
- **OS:** Darwin 25.0.0 arm64
- **CPU:** Apple M3 Max
- **Memory:** 128.0 GB
- **Date:** 2025-12-24T08:51:13.083683

## Ansi Benchmarks

### ansi_colors

**16_colors:**
  - sequences: 5120
  - time_ms_mean: 3.195
  - sequences_per_sec: 1602353.457
**256_colors:**
  - sequences: 10240
  - time_ms_mean: 3.403
  - sequences_per_sec: 3009360.045
**truecolor:**
  - sequences: 5960
  - time_ms_mean: 3.200
  - sequences_per_sec: 1862611.525

### ansi_attributes

**attributes:**
  - sequences: 2800
  - time_ms_mean: 3.262

### ansi_cursor

**cursor_movements:**
  - sequences: 400
  - time_ms_mean: 2.873

## Latency Benchmarks

### latency_echo

**echo:**
  - min_ms: 3.358
  - max_ms: 3.715
  - mean_ms: 3.497
  - median_ms: 3.409
  - stdev_ms: 0.157
  - p95_ms: 3.715
  - p99_ms: 3.715
**printf_1chars:**
  - min_ms: 2.917
  - max_ms: 3.163
  - mean_ms: 3.017
  - median_ms: 2.961
  - stdev_ms: 0.107
  - p95_ms: 3.163
  - p99_ms: 3.163
**printf_10chars:**
  - min_ms: 3.104
  - max_ms: 4.250
  - mean_ms: 3.799
  - median_ms: 3.801
  - stdev_ms: 0.441
  - p95_ms: 4.250
  - p99_ms: 4.250
**printf_80chars:**
  - min_ms: 2.947
  - max_ms: 3.693
  - mean_ms: 3.253
  - median_ms: 3.186
  - stdev_ms: 0.292
  - p95_ms: 3.693
  - p99_ms: 3.693
**printf_200chars:**
  - min_ms: 2.927
  - max_ms: 3.219
  - mean_ms: 3.071
  - median_ms: 3.098
  - stdev_ms: 0.133
  - p95_ms: 3.219
  - p99_ms: 3.219

### latency_sequential

- min: 27.975
- max: 32.659
- mean: 30.046
- median: 29.210
- stdev: 1.958
- p50: 29.210
- p90: 32.659
- p95: 32.659
- p99: 32.659
- unit: ms

## Resources Benchmarks

### memory_usage

**processes:**

### cpu_usage

- cpu_before_percent: 7.600
- cpu_after_percent: 18.600
- output_time_ms: 10.387

## Simulation Benchmarks

### simulation_compiler

**compiler_output:**
  - lines: 1500
  - bytes: 63659
  - time_ms_mean: 3.325

### simulation_logs

**log_output:**
  - lines: 1000
  - bytes: 75118
  - time_ms_mean: 3.567

### simulation_git_diff

**git_diff:**
  - lines: 854
  - bytes: 26626
  - time_ms_mean: 3.059

### simulation_htop

**htop_simulation:**
  - lines: 620
  - bytes: 39340
  - time_ms_mean: 3.206

### simulation_vim

**vim_simulation:**
  - lines: 505
  - bytes: 19440
  - time_ms_mean: 3.327

### simulation_mixed

**mixed_workload:**
  - bytes: 13708
  - time_ms_mean: 2.918

## Special Benchmarks

### box_drawing

**box_drawing:**
  - chars: 19000
  - time_ms_mean: 2.909

### block_elements

**block_elements:**
  - chars: 34080
  - time_ms_mean: 3.042

### powerline

**powerline:**
  - chars: 1200
  - time_ms_mean: 3.341

### braille

**braille:**
  - chars: 5120
  - time_ms_mean: 3.445

### math_symbols

**math_symbols:**
  - chars: 5880
  - time_ms_mean: 2.944

## Throughput Benchmarks

### throughput_raw

**1MB:**
  - throughput_mbps_mean: 248.924
  - throughput_mbps_stdev: 34.176
  - time_ms_mean: 4.088
**5MB:**
  - throughput_mbps_mean: 851.715
  - throughput_mbps_stdev: 97.502
  - time_ms_mean: 5.935
**10MB:**
  - throughput_mbps_mean: 1049.912
  - throughput_mbps_stdev: 51.718
  - time_ms_mean: 9.544
**25MB:**
  - throughput_mbps_mean: 1390.893
  - throughput_mbps_stdev: 42.867
  - time_ms_mean: 17.988
**50MB:**
  - throughput_mbps_mean: 1491.325
  - throughput_mbps_stdev: 67.571
  - time_ms_mean: 33.584

### throughput_lines

**1000_lines:**
  - lines_per_sec_mean: 234368.571
  - lines_per_sec_stdev: 33485.022
  - time_ms_mean: 4.349
**5000_lines:**
  - lines_per_sec_mean: 1140723.877
  - lines_per_sec_stdev: 72108.297
  - time_ms_mean: 4.397
**10000_lines:**
  - lines_per_sec_mean: 2260348.366
  - lines_per_sec_stdev: 171423.684
  - time_ms_mean: 4.444
**50000_lines:**
  - lines_per_sec_mean: 9732054.066
  - lines_per_sec_stdev: 509518.462
  - time_ms_mean: 5.149
**100000_lines:**
  - lines_per_sec_mean: 12906603.609
  - lines_per_sec_stdev: 630212.421
  - time_ms_mean: 7.763

### throughput_varied

**varied_lines_10k:**
  - time_ms_mean: 4.123
  - time_ms_stdev: 0.607

## Unicode Benchmarks

### unicode_emoji

**basic:**
  - chars: 1880
  - bytes: 7440
  - time_ms_mean: 3.958
  - time_ms_stdev: 0.825
  - chars_per_sec: 475035.399
**variation_selectors:**
  - chars: 2950
  - bytes: 9350
  - time_ms_mean: 3.356
  - time_ms_stdev: 0.172
  - chars_per_sec: 878992.159
**zwj_sequences:**
  - chars: 4920
  - bytes: 17460
  - time_ms_mean: 2.917
  - time_ms_stdev: 0.140
  - chars_per_sec: 1686837.971
**skin_tones:**
  - chars: 2100
  - bytes: 8175
  - time_ms_mean: 3.325
  - time_ms_stdev: 0.339
  - chars_per_sec: 631639.090
**flags:**
  - chars: 3000
  - bytes: 12000
  - time_ms_mean: 3.492
  - time_ms_stdev: 0.340
  - chars_per_sec: 859098.364

### unicode_cjk

**cjk:**
  - chars: 8600
  - bytes: 25800
  - time_ms_mean: 3.007
  - chars_per_sec: 2859787.358

### unicode_surrogate

**surrogate_pairs:**
  - chars: 4900
  - bytes: 19500
  - time_ms_mean: 2.832
  - chars_per_sec: 1729920.684

### unicode_combining

**combining_diacritics:**
  - chars: 32400
  - bytes: 48800
  - time_ms_mean: 2.973
**grapheme_clusters:**
  - chars: 8600
  - bytes: 24200
  - time_ms_mean: 2.887
