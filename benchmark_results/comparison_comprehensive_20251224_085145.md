# Comprehensive Terminal Benchmark Comparison

**Generated:** 2025-12-24T08:51:45.456136

## Terminals Compared

- **bossterm** (Darwin 25.0.0 arm64)
- **iterm2** (Darwin 25.0.0 arm64)
- **terminal** (Darwin 25.0.0 arm64)
- **alacritty** (Darwin 25.0.0 arm64)

## Ansi: ansi_attributes

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| attributes/sequences | 2800 | 2800 | 2800 | 2800 |
| attributes/time_ms_mean | 3.37 | 3.22 | 3.26 | 3.19 |

## Ansi: ansi_colors

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| 16_colors/sequences | 5120 | 5120 | 5120 | 5120 |
| 16_colors/sequences_per_sec | 1685740.00 | 1739376.57 | 1602353.46 | 1470859.29 |
| 16_colors/time_ms_mean | 3.04 | 2.94 | 3.20 | 3.48 |
| 256_colors/sequences | 10240 | 10240 | 10240 | 10240 |
| 256_colors/sequences_per_sec | 3154797.68 | 3453266.45 | 3009360.04 | 2818206.32 |
| 256_colors/time_ms_mean | 3.25 | 2.97 | 3.40 | 3.63 |
| truecolor/sequences | 5960 | 5960 | 5960 | 5960 |
| truecolor/sequences_per_sec | 1908273.76 | 1989922.58 | 1862611.52 | 1881590.26 |
| truecolor/time_ms_mean | 3.12 | 3.00 | 3.20 | 3.17 |

## Ansi: ansi_cursor

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| cursor_movements/sequences | 400 | 400 | 400 | 400 |
| cursor_movements/time_ms_mean | 3.32 | 3.11 | 2.87 | 3.31 |

## Latency: latency_echo

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| echo/max_ms | 4.10 | 3.02 | 3.71 | 3.95 |
| echo/mean_ms | 3.64 | 2.92 | 3.50 | 3.55 |
| echo/median_ms | 3.68 | 2.92 | 3.41 | 3.34 |
| echo/min_ms | 3.07 | 2.82 | 3.36 | 3.23 |
| echo/p95_ms | 4.10 | 3.02 | 3.71 | 3.95 |
| echo/p99_ms | 4.10 | 3.02 | 3.71 | 3.95 |
| echo/stdev_ms | 0.46 | 0.09 | 0.16 | 0.34 |
| printf_10chars/max_ms | 3.28 | 3.62 | 4.25 | 3.83 |
| printf_10chars/mean_ms | 3.04 | 3.33 | 3.80 | 3.46 |
| printf_10chars/median_ms | 3.05 | 3.25 | 3.80 | 3.34 |
| printf_10chars/min_ms | 2.77 | 3.14 | 3.10 | 3.27 |
| printf_10chars/p95_ms | 3.28 | 3.62 | 4.25 | 3.83 |
| printf_10chars/p99_ms | 3.28 | 3.62 | 4.25 | 3.83 |
| printf_10chars/stdev_ms | 0.20 | 0.19 | 0.44 | 0.24 |
| printf_1chars/max_ms | 3.27 | 4.67 | 3.16 | 3.75 |
| printf_1chars/mean_ms | 3.11 | 3.63 | 3.02 | 3.53 |
| printf_1chars/median_ms | 3.12 | 3.52 | 2.96 | 3.71 |
| printf_1chars/min_ms | 2.97 | 3.16 | 2.92 | 3.05 |
| printf_1chars/p95_ms | 3.27 | 4.67 | 3.16 | 3.75 |
| printf_1chars/p99_ms | 3.27 | 4.67 | 3.16 | 3.75 |
| printf_1chars/stdev_ms | 0.13 | 0.61 | 0.11 | 0.30 |
| printf_200chars/max_ms | 3.08 | 3.72 | 3.22 | 3.85 |
| printf_200chars/mean_ms | 2.91 | 3.24 | 3.07 | 3.68 |
| printf_200chars/median_ms | 2.88 | 3.16 | 3.10 | 3.69 |
| printf_200chars/min_ms | 2.83 | 2.92 | 2.93 | 3.45 |
| printf_200chars/p95_ms | 3.08 | 3.72 | 3.22 | 3.85 |
| printf_200chars/p99_ms | 3.08 | 3.72 | 3.22 | 3.85 |
| printf_200chars/stdev_ms | 0.10 | 0.30 | 0.13 | 0.15 |
| printf_80chars/max_ms | 4.24 | 3.51 | 3.69 | 3.66 |
| printf_80chars/mean_ms | 3.42 | 3.19 | 3.25 | 3.42 |
| printf_80chars/median_ms | 3.04 | 3.13 | 3.19 | 3.55 |
| printf_80chars/min_ms | 2.88 | 2.91 | 2.95 | 3.08 |
| printf_80chars/p95_ms | 4.24 | 3.51 | 3.69 | 3.66 |
| printf_80chars/p99_ms | 4.24 | 3.51 | 3.69 | 3.66 |
| printf_80chars/stdev_ms | 0.65 | 0.24 | 0.29 | 0.24 |

## Latency: latency_sequential

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| max | 31.63 | 31.60 | 32.66 | 35.55 |
| mean | 29.62 | 29.32 | 30.05 | 33.27 |
| median | 29.58 | 28.73 | 29.21 | 33.12 |
| min | 28.16 | 28.38 | 27.98 | 30.81 |
| p50 | 29.58 | 28.73 | 29.21 | 33.12 |
| p90 | 31.63 | 31.60 | 32.66 | 35.55 |
| p95 | 31.63 | 31.60 | 32.66 | 35.55 |
| p99 | 31.63 | 31.60 | 32.66 | 35.55 |
| stdev | 1.31 | 1.32 | 1.96 | 1.92 |
| unit | ms | ms | ms | ms |

## Resources: cpu_usage

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| cpu_after_percent | 6.90 | 13.90 | 18.60 | 9.30 |
| cpu_before_percent | 6.90 | 10.40 | 7.60 | 9.00 |
| output_time_ms | 11.23 | 10.54 | 10.39 | 10.68 |

## Resources: memory_usage

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| processes/BossTerm | {'memory_mb': 943.671875} | N/A | N/A | N/A |
| processes/java | {'memory_mb': 726.1875} | N/A | N/A | N/A |

## Simulation: simulation_compiler

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| compiler_output/bytes | 63773 | 63707 | 63659 | 63789 |
| compiler_output/lines | 1500 | 1500 | 1500 | 1500 |
| compiler_output/time_ms_mean | 3.47 | 3.11 | 3.32 | 3.48 |

## Simulation: simulation_git_diff

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| git_diff/bytes | 21318 | 20933 | 26626 | 17252 |
| git_diff/lines | 683 | 671 | 854 | 562 |
| git_diff/time_ms_mean | 2.93 | 3.07 | 3.06 | 3.48 |

## Simulation: simulation_htop

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| htop_simulation/bytes | 39340 | 39340 | 39340 | 39340 |
| htop_simulation/lines | 620 | 620 | 620 | 620 |
| htop_simulation/time_ms_mean | 3.09 | 3.55 | 3.21 | 3.72 |

## Simulation: simulation_logs

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| log_output/bytes | 75227 | 75163 | 75118 | 75295 |
| log_output/lines | 1000 | 1000 | 1000 | 1000 |
| log_output/time_ms_mean | 2.89 | 3.00 | 3.57 | 3.94 |

## Simulation: simulation_mixed

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| mixed_workload/bytes | 13708 | 13708 | 13708 | 13708 |
| mixed_workload/time_ms_mean | 3.15 | 2.98 | 2.92 | 3.67 |

## Simulation: simulation_vim

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| vim_simulation/bytes | 19460 | 19430 | 19440 | 19445 |
| vim_simulation/lines | 505 | 505 | 505 | 505 |
| vim_simulation/time_ms_mean | 3.05 | 3.13 | 3.33 | 3.54 |

## Special: block_elements

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| block_elements/chars | 34080 | 34080 | 34080 | 34080 |
| block_elements/time_ms_mean | 2.97 | 2.95 | 3.04 | 3.64 |

## Special: box_drawing

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| box_drawing/chars | 19000 | 19000 | 19000 | 19000 |
| box_drawing/time_ms_mean | 3.08 | 2.86 | 2.91 | 3.56 |

## Special: braille

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| braille/chars | 5120 | 5120 | 5120 | 5120 |
| braille/time_ms_mean | 3.10 | 2.97 | 3.44 | 3.67 |

## Special: math_symbols

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| math_symbols/chars | 5880 | 5880 | 5880 | 5880 |
| math_symbols/time_ms_mean | 3.01 | 3.36 | 2.94 | 3.46 |

## Special: powerline

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| powerline/chars | 1200 | 1200 | 1200 | 1200 |
| powerline/time_ms_mean | 2.83 | 3.15 | 3.34 | 3.35 |

## Throughput: throughput_lines

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| 100000_lines/lines_per_sec_mean | 14132534.59 | 13436825.26 | 12906603.61 | 13056129.52 |
| 100000_lines/lines_per_sec_stdev | 1222547.16 | 1092073.98 | 630212.42 | 1126844.52 |
| 100000_lines/time_ms_mean | 7.12 | 7.48 | 7.76 | 7.71 |
| 10000_lines/lines_per_sec_mean | 2923777.73 | 3084858.23 | 2260348.37 | 2744678.70 |
| 10000_lines/lines_per_sec_stdev | 265942.35 | 134051.72 | 171423.68 | 142118.54 |
| 10000_lines/time_ms_mean | 3.44 | 3.25 | 4.44 | 3.65 |
| 1000_lines/lines_per_sec_mean | 287777.91 | 244082.42 | 234368.57 | 266144.12 |
| 1000_lines/lines_per_sec_stdev | 46080.93 | 48375.51 | 33485.02 | 51337.64 |
| 1000_lines/time_ms_mean | 3.56 | 4.24 | 4.35 | 3.91 |
| 50000_lines/lines_per_sec_mean | 9920943.17 | 8988935.07 | 9732054.07 | 7882232.36 |
| 50000_lines/lines_per_sec_stdev | 455066.76 | 387330.78 | 509518.46 | 559879.34 |
| 50000_lines/time_ms_mean | 5.05 | 5.57 | 5.15 | 6.37 |
| 5000_lines/lines_per_sec_mean | 1500561.20 | 1413624.90 | 1140723.88 | 1432967.64 |
| 5000_lines/lines_per_sec_stdev | 89871.62 | 119307.31 | 72108.30 | 33171.78 |
| 5000_lines/time_ms_mean | 3.34 | 3.56 | 4.40 | 3.49 |

## Throughput: throughput_raw

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| 10MB/throughput_mbps_mean | 1186.53 | 1182.02 | 1049.91 | 1060.83 |
| 10MB/throughput_mbps_stdev | 98.40 | 136.80 | 51.72 | 53.62 |
| 10MB/time_ms_mean | 8.47 | 8.55 | 9.54 | 9.45 |
| 1MB/throughput_mbps_mean | 363.59 | 255.38 | 248.92 | 232.57 |
| 1MB/throughput_mbps_stdev | 29.63 | 32.62 | 34.18 | 33.38 |
| 1MB/time_ms_mean | 2.77 | 3.98 | 4.09 | 4.38 |
| 25MB/throughput_mbps_mean | 1571.48 | 1472.65 | 1390.89 | 1399.53 |
| 25MB/throughput_mbps_stdev | 100.67 | 79.61 | 42.87 | 43.50 |
| 25MB/time_ms_mean | 15.96 | 17.02 | 17.99 | 17.88 |
| 50MB/throughput_mbps_mean | 1645.11 | 1598.69 | 1491.32 | 1632.80 |
| 50MB/throughput_mbps_stdev | 112.19 | 51.70 | 67.57 | 85.41 |
| 50MB/time_ms_mean | 30.50 | 31.30 | 33.58 | 30.69 |
| 5MB/throughput_mbps_mean | 955.45 | 769.00 | 851.72 | 769.66 |
| 5MB/throughput_mbps_stdev | 145.29 | 48.16 | 97.50 | 113.30 |
| 5MB/time_ms_mean | 5.33 | 6.52 | 5.93 | 6.61 |

## Throughput: throughput_varied

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| varied_lines_10k/time_ms_mean | 3.86 | 3.84 | 4.12 | 4.33 |
| varied_lines_10k/time_ms_stdev | 0.37 | 0.54 | 0.61 | 0.64 |

## Unicode: unicode_cjk

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| cjk/bytes | 25800 | 25800 | 25800 | 25800 |
| cjk/chars | 8600 | 8600 | 8600 | 8600 |
| cjk/chars_per_sec | 2993387.11 | 2709415.28 | 2859787.36 | 2136115.25 |
| cjk/time_ms_mean | 2.87 | 3.17 | 3.01 | 4.03 |

## Unicode: unicode_combining

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| combining_diacritics/bytes | 48800 | 48800 | 48800 | 48800 |
| combining_diacritics/chars | 32400 | 32400 | 32400 | 32400 |
| combining_diacritics/time_ms_mean | 3.21 | 3.55 | 2.97 | 3.24 |
| grapheme_clusters/bytes | 24200 | 24200 | 24200 | 24200 |
| grapheme_clusters/chars | 8600 | 8600 | 8600 | 8600 |
| grapheme_clusters/time_ms_mean | 2.84 | 3.38 | 2.89 | 3.50 |

## Unicode: unicode_emoji

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| basic/bytes | 7440 | 7440 | 7440 | 7440 |
| basic/chars | 1880 | 1880 | 1880 | 1880 |
| basic/chars_per_sec | 591681.78 | 589033.99 | 475035.40 | 587526.03 |
| basic/time_ms_mean | 3.18 | 3.19 | 3.96 | 3.20 |
| basic/time_ms_stdev | 0.20 | 0.12 | 0.82 | 0.19 |
| flags/bytes | 12000 | 12000 | 12000 | 12000 |
| flags/chars | 3000 | 3000 | 3000 | 3000 |
| flags/chars_per_sec | 1031317.75 | 997680.46 | 859098.36 | 845574.58 |
| flags/time_ms_mean | 2.91 | 3.01 | 3.49 | 3.55 |
| flags/time_ms_stdev | 0.12 | 0.20 | 0.34 | 0.26 |
| skin_tones/bytes | 8175 | 8175 | 8175 | 8175 |
| skin_tones/chars | 2100 | 2100 | 2100 | 2100 |
| skin_tones/chars_per_sec | 662753.22 | 716118.59 | 631639.09 | 647536.90 |
| skin_tones/time_ms_mean | 3.17 | 2.93 | 3.32 | 3.24 |
| skin_tones/time_ms_stdev | 0.14 | 0.17 | 0.34 | 0.07 |
| variation_selectors/bytes | 9350 | 9350 | 9350 | 9350 |
| variation_selectors/chars | 2950 | 2950 | 2950 | 2950 |
| variation_selectors/chars_per_sec | 1008139.28 | 903552.26 | 878992.16 | 828506.14 |
| variation_selectors/time_ms_mean | 2.93 | 3.26 | 3.36 | 3.56 |
| variation_selectors/time_ms_stdev | 0.12 | 0.15 | 0.17 | 0.15 |
| zwj_sequences/bytes | 17460 | 17460 | 17460 | 17460 |
| zwj_sequences/chars | 4920 | 4920 | 4920 | 4920 |
| zwj_sequences/chars_per_sec | 1488860.15 | 1515151.52 | 1686837.97 | 1278478.30 |
| zwj_sequences/time_ms_mean | 3.30 | 3.25 | 2.92 | 3.85 |
| zwj_sequences/time_ms_stdev | 0.48 | 0.48 | 0.14 | 0.52 |

## Unicode: unicode_surrogate

| Metric | bossterm | iterm2 | terminal | alacritty |
|--------|------ | ------ | ------ | ------|
| surrogate_pairs/bytes | 19500 | 19500 | 19500 | 19500 |
| surrogate_pairs/chars | 4900 | 4900 | 4900 | 4900 |
| surrogate_pairs/chars_per_sec | 1445527.17 | 1706549.19 | 1729920.68 | 1502591.26 |
| surrogate_pairs/time_ms_mean | 3.39 | 2.87 | 2.83 | 3.26 |
