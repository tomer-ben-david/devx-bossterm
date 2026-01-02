package ai.rever.bossterm.compose.selection

import ai.rever.bossterm.compose.SelectionMode
import ai.rever.bossterm.terminal.model.TerminalLine
import ai.rever.bossterm.terminal.model.TerminalTextBuffer

/**
 * Selection anchor tied to a TerminalLine object by identity.
 * Survives buffer scrolling because line objects move but their identity remains.
 *
 * @property line The line object (tracked by identity, not position)
 * @property column Column offset within the line
 * @property lineVersion Version of line at selection time (for change detection)
 */
data class SelectionAnchor(
    val line: TerminalLine,
    val column: Int,
    val lineVersion: Long
) {
    /**
     * Check if the line content has changed since selection was created.
     * Useful for optional visual indicators showing stale selection.
     */
    fun hasContentChanged(): Boolean = line.getSnapshotVersion() != lineVersion

    companion object {
        /**
         * Create an anchor from buffer coordinates.
         */
        fun fromBufferCoordinates(
            col: Int,
            row: Int,
            textBuffer: TerminalTextBuffer
        ): SelectionAnchor {
            val line = textBuffer.getLine(row)
            return SelectionAnchor(
                line = line,
                column = col,
                lineVersion = line.getSnapshotVersion()
            )
        }
    }
}

/**
 * Content-anchored selection that survives buffer scrolling.
 *
 * Instead of storing row indices (which shift during scroll), this stores
 * references to TerminalLine objects. When the buffer scrolls, lines move
 * in the cyclic buffer but their object identity remains the same.
 *
 * Resolution to screen coordinates happens at render time via SelectionTracker.
 */
data class ContentAnchoredSelection(
    val start: SelectionAnchor,
    val end: SelectionAnchor,
    val mode: SelectionMode
) {
    companion object {
        /**
         * Create a selection from buffer coordinates.
         */
        fun fromBufferCoordinates(
            startCol: Int,
            startRow: Int,
            endCol: Int,
            endRow: Int,
            mode: SelectionMode,
            textBuffer: TerminalTextBuffer
        ): ContentAnchoredSelection {
            return ContentAnchoredSelection(
                start = SelectionAnchor.fromBufferCoordinates(startCol, startRow, textBuffer),
                end = SelectionAnchor.fromBufferCoordinates(endCol, endRow, textBuffer),
                mode = mode
            )
        }
    }
}

/**
 * Resolved selection with concrete buffer coordinates.
 * Created by SelectionTracker.resolveToCoordinates() for rendering and text extraction.
 */
data class ResolvedSelection(
    val startCol: Int,
    val startRow: Int,
    val endCol: Int,
    val endRow: Int,
    val mode: SelectionMode,
    /** True if start line content changed since selection */
    val startContentChanged: Boolean = false,
    /** True if end line content changed since selection */
    val endContentChanged: Boolean = false,
    /** True if selection was truncated due to line eviction */
    val wasTruncated: Boolean = false
) {
    /**
     * Convert to legacy Pair format for compatibility with existing code.
     */
    fun toStartPair(): Pair<Int, Int> = Pair(startCol, startRow)

    /**
     * Convert to legacy Pair format for compatibility with existing code.
     */
    fun toEndPair(): Pair<Int, Int> = Pair(endCol, endRow)
}
