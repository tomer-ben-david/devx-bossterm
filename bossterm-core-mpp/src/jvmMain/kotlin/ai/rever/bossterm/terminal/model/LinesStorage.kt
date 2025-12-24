package ai.rever.bossterm.terminal.model

import ai.rever.bossterm.terminal.StyledTextConsumer
import kotlin.math.min

/**
 * Base interface for storing terminal lines.
 * For the first line of the storage, we use the term top, for the last line - bottom.
 * Supports adding/removing lines from the top and bottom.
 * Also, accessing lines by index.
 */
interface LinesStorage : Iterable<TerminalLine> {
  /** Count of the available lines in the storage */
  val size: Int

  /**
   * Throws [IndexOutOfBoundsException] of index is negative.
   * If index is grater than [size], storage should be filled with empty lines until [index] line.
   */
  operator fun get(index: Int): TerminalLine

  /** @return -1 if there is no such line */
  fun indexOf(line: TerminalLine): Int

  /**
   * Adds a new line to the start of the storage.
   * If the implementation limits the max capacity of the storage,
   * and storage is full, then the line should not be added.
   */
  fun addToTop(line: TerminalLine)

  /**
   * Adds a new line to the end of the storage.
   * If the implementation limits the max capacity of the storage
   * and storage is full, the first line from the top should be removed.
   */
  fun addToBottom(line: TerminalLine)

  /**
   * Removes a single line from the start of the storage.
   * @throws NoSuchElementException if storage is empty.
   */
  fun removeFromTop(): TerminalLine

  /**
   * Removes a single line from the end of the storage.
   * @throws NoSuchElementException if storage is empty.
   */
  fun removeFromBottom(): TerminalLine

  fun clear()

  /**
   * Inserts a line at the given index, shifting subsequent lines down.
   * More efficient than removeFromTop/addToTop pattern for targeted insertions.
   *
   * @param index Position to insert at (0-based)
   * @param line The line to insert
   * @throws IndexOutOfBoundsException if index < 0 or index > size
   */
  fun insertAt(index: Int, line: TerminalLine)

  /**
   * Removes and returns the line at the given index, shifting subsequent lines up.
   *
   * @param index Position to remove from (0-based)
   * @return The removed line
   * @throws IndexOutOfBoundsException if index < 0 or index >= size
   */
  fun removeAt(index: Int): TerminalLine

  companion object {
    const val DEFAULT_MAX_LINES_COUNT: Int = 5000
  }
}

fun LinesStorage.addAllToTop(lines: List<TerminalLine>) {
  for (ind in lines.lastIndex downTo 0) {
    addToTop(lines[ind])
  }
}

fun LinesStorage.addAllToBottom(lines: List<TerminalLine>) {
  for (line in lines) {
    addToBottom(line)
  }
}

fun LinesStorage.removeFromTop(count: Int): List<TerminalLine> {
  return perform(count, false) {
    removeFromTop()
  }
}

fun LinesStorage.removeFromBottom(count: Int): List<TerminalLine> {
  return perform(count, true) {
    removeFromBottom()
  }
}

private inline fun LinesStorage.perform(count: Int, reverse: Boolean, operation: (() -> TerminalLine)): List<TerminalLine> {
  if (count < 0) {
    throw IllegalArgumentException("Count must be >= 0")
  }
  if (count == 0) {
    return emptyList()
  }
  return when (val actualCount = min(count, size)) {
    0 -> emptyList()
    1 -> listOf(operation())
    else -> {
      val result = ArrayList<TerminalLine>(actualCount)
      repeat(actualCount) {
        result.add(operation())
      }
      if (reverse) {
        result.reverse()
      }
      result
    }
  }
}

fun LinesStorage.removeBottomEmptyLines(maxCount: Int): Int {
  var removedCount = 0
  var ind: Int = size - 1
  while (removedCount < maxCount && ind >= 0 && this[ind].isNulOrEmpty) {
    ind--
    removedCount++
  }
  removeFromBottom(removedCount)
  return removedCount
}

/**
 * @param startRow value passed as a corresponding parameter of the [StyledTextConsumer] methods.
 */
fun LinesStorage.processLines(yStart: Int, count: Int, consumer: StyledTextConsumer, startRow: Int = -size) {
  require(yStart >= 0) { "yStart is $yStart, should be >0" }
  val maxY = min(yStart + count, size)
  for (y in yStart until maxY) {
    this[y].process(y, consumer, startRow)
  }
}

/**
 * Performs a cyclic shift:
 * adds [count] of lines at the position [y], then removes [count] of lines from the end of [y, lastLine] range.
 *
 * Optimized to use direct index operations instead of multiple list shuffles.
 * Before: O(m + n + count) where m = lines before y, n = lines after lastLine
 * After: O(count) direct index operations
 *
 * @param y        index of the insertion point, the operation does not affect all lines before this line.
 * @param count    number of lines to insert.
 * @param lastLine the operation does not affect all lines after this line.
 */
fun LinesStorage.insertLines(y: Int, count: Int, lastLine: Int, filler: TerminalLine.TextEntry) {
  if (count <= 0) return

  // First, remove count lines from the end of the scroll region (they scroll off)
  repeat(count) {
    val removeIndex = lastLine.coerceAtMost(size - 1)
    if (removeIndex >= y && size > 0) {
      removeAt(removeIndex)
    }
  }

  // Then, insert count blank lines at position y
  repeat(count) {
    insertAt(y, TerminalLine(filler))
  }
}

/**
 * Performs a cyclic shift:
 * removes [count] of lines after the position [y], then adds [count] of lines after the [lastLine].
 *
 * Optimized to use direct index operations instead of multiple list shuffles.
 * Before: O(m + n + count) where m = lines before y, n = lines after lastLine
 * After: O(count) direct index operations
 *
 * @param y        first index if the line to delete, the operation does not affect all lines before this line.
 * @param count    number of lines to delete.
 * @param lastLine the operation does not affect all lines after this line.
 */
fun LinesStorage.deleteLines(y: Int, count: Int, lastLine: Int, filler: TerminalLine.TextEntry): List<TerminalLine> {
  if (count <= 0) return emptyList()

  val removed = mutableListOf<TerminalLine>()

  // Remove count lines starting at position y
  repeat(count) {
    if (y < size && y <= lastLine) {
      removed.add(removeAt(y))
    }
  }

  // Insert count blank lines at the end of the scroll region
  // The insert position is (lastLine - count + 1) since we've already removed lines
  val actualRemoved = removed.size
  repeat(actualRemoved) { i ->
    val insertIndex = (lastLine - count + 1 + i).coerceIn(0, size)
    insertAt(insertIndex, TerminalLine(filler))
  }

  return removed
}

/**
 * @return a string where the line separator divides each terminal line text.
 */
fun LinesStorage.getLinesAsString(): String {
  val sb = StringBuilder()
  for (index in 0 until size) {
    sb.append(this[index].text)
    if (index != size - 1) {
      sb.append("\n")
    }
  }
  return sb.toString()
}
