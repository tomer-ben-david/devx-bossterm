package ai.rever.bossterm.compose.selection

import ai.rever.bossterm.compose.SelectionMode
import ai.rever.bossterm.terminal.model.TerminalLine
import ai.rever.bossterm.terminal.model.TerminalTextBuffer
import java.lang.ref.WeakReference

/**
 * Selection anchor tied to a TerminalLine object by identity.
 * Survives buffer scrolling because line objects move but their identity remains.
 *
 * Uses WeakReference to prevent memory leaks when lines are evicted from the
 * cyclic buffer. When the referenced line is garbage collected, the anchor
 * is treated as invalid (similar to line eviction from history).
 *
 * @property lineRef Weak reference to the line object (tracked by identity, not position)
 * @property column Column offset within the line
 * @property lineVersion Version of line at selection time (for change detection)
 */
class SelectionAnchor private constructor(
    private val lineRef: WeakReference<TerminalLine>,
    val column: Int,
    val lineVersion: Long
) {
    /**
     * Get the referenced line, or null if it was garbage collected.
     */
    val line: TerminalLine?
        get() = lineRef.get()

    /**
     * Check if the anchor is still valid (line not garbage collected).
     */
    fun isValid(): Boolean = lineRef.get() != null

    /**
     * Check if the line content has changed since selection was created.
     * Returns true if line was garbage collected (conservatively assume changed).
     */
    fun hasContentChanged(): Boolean {
        val currentLine = lineRef.get() ?: return true
        return currentLine.getSnapshotVersion() != lineVersion
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SelectionAnchor) return false
        return lineRef.get() === other.lineRef.get() &&
                column == other.column &&
                lineVersion == other.lineVersion
    }

    override fun hashCode(): Int {
        var result = lineRef.get()?.let { System.identityHashCode(it) } ?: 0
        result = 31 * result + column
        result = 31 * result + lineVersion.hashCode()
        return result
    }

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
                lineRef = WeakReference(line),
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
