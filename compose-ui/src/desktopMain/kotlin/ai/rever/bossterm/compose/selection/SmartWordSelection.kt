package ai.rever.bossterm.compose.selection

import java.util.concurrent.CopyOnWriteArrayList

/**
 * Pattern for smart word selection.
 * Similar to HyperlinkPattern but for double-click word selection.
 *
 * @property id Unique identifier for this pattern
 * @property regex The regex pattern to match
 * @property priority Higher priority patterns are matched first (default: 0)
 * @property quickCheck Optional fast check before applying regex (for performance)
 */
data class WordSelectionPattern(
    val id: String,
    val regex: Regex,
    val priority: Int = 0,
    val quickCheck: ((text: String) -> Boolean)? = null
)

/**
 * Registry for word selection patterns.
 * Thread-safe: All operations are safe to call from any thread.
 *
 * Built-in patterns (by priority):
 * - URL (100): HTTP/HTTPS URLs
 * - Unix path (90): /path/to/file
 * - Windows path (90): C:\path\to\file
 * - Email (85): user@domain.com
 * - Quoted double (80): "string"
 * - Quoted single (80): 'string'
 * - Backtick (80): `string`
 * - Default word (0): alphanumeric + underscore
 */
class WordSelectionPatternRegistry {
    private val patterns = CopyOnWriteArrayList<WordSelectionPattern>()

    init {
        addBuiltinPatterns()
    }

    private fun addBuiltinPatterns() {
        // HTTP/HTTPS URLs (highest priority)
        addPattern(WordSelectionPattern(
            id = "builtin:url",
            regex = Regex("""https?://[^\s<>"'`\[\](){}]+"""),
            priority = 100,
            quickCheck = { it.contains("://") }
        ))

        // Unix file paths
        addPattern(WordSelectionPattern(
            id = "builtin:path-unix",
            regex = Regex("""(?:^|(?<=[\s"'`]))(/[\w.+-]+)+/?"""),
            priority = 90,
            quickCheck = { it.contains('/') }
        ))

        // Windows file paths
        addPattern(WordSelectionPattern(
            id = "builtin:path-windows",
            regex = Regex("""[A-Za-z]:\\[\w\\.\-+]+"""),
            priority = 90,
            quickCheck = { it.contains(":\\") }
        ))

        // Email addresses
        addPattern(WordSelectionPattern(
            id = "builtin:email",
            regex = Regex("""[\w.+-]+@[\w.-]+\.[a-zA-Z]{2,}"""),
            priority = 85,
            quickCheck = { it.contains('@') }
        ))

        // Double-quoted strings
        addPattern(WordSelectionPattern(
            id = "builtin:quoted-double",
            regex = Regex(""""[^"\\]*(?:\\.[^"\\]*)*""""),
            priority = 80,
            quickCheck = { it.contains('"') }
        ))

        // Single-quoted strings
        addPattern(WordSelectionPattern(
            id = "builtin:quoted-single",
            regex = Regex("""'[^'\\]*(?:\\.[^'\\]*)*'"""),
            priority = 80,
            quickCheck = { it.contains('\'') }
        ))

        // Backtick strings (common in shells)
        addPattern(WordSelectionPattern(
            id = "builtin:backtick",
            regex = Regex("""`[^`]+`"""),
            priority = 80,
            quickCheck = { it.contains('`') }
        ))

        // Default word (alphanumeric + underscore) - lowest priority
        addPattern(WordSelectionPattern(
            id = "builtin:word",
            regex = Regex("""\w+"""),
            priority = 0
        ))
    }

    /**
     * Add a pattern to the registry.
     * If a pattern with the same ID exists, it will be replaced.
     */
    fun addPattern(pattern: WordSelectionPattern) {
        patterns.removeIf { it.id == pattern.id }
        patterns.add(pattern)
    }

    /**
     * Remove a pattern by ID.
     */
    fun removePattern(id: String): Boolean {
        return patterns.removeIf { it.id == id }
    }

    /**
     * Get all patterns sorted by priority (highest first).
     */
    fun getPatterns(): List<WordSelectionPattern> {
        return patterns.sortedByDescending { it.priority }
    }

    /**
     * Reset to default built-in patterns only.
     */
    fun resetToDefaults() {
        patterns.clear()
        addBuiltinPatterns()
    }
}

/**
 * Smart word selection for double-click.
 * Uses pattern registry to select URLs, paths, quoted strings, etc.
 */
object SmartWordSelection {
    val registry = WordSelectionPatternRegistry()

    /**
     * Find the word/pattern at the given column position in text.
     *
     * @param text The full line text
     * @param col Column position (0-based)
     * @return Pair of (startCol, endCol exclusive) or null if no match
     */
    fun selectWordAt(text: String, col: Int): Pair<Int, Int>? {
        if (text.isEmpty() || col < 0 || col >= text.length) {
            return null
        }

        // Try each pattern in priority order
        for (pattern in registry.getPatterns()) {
            // Skip if quick check fails
            if (pattern.quickCheck != null && !pattern.quickCheck.invoke(text)) {
                continue
            }

            // Find all matches and check if col falls within any
            for (match in pattern.regex.findAll(text)) {
                if (col >= match.range.first && col <= match.range.last) {
                    return Pair(match.range.first, match.range.last + 1)
                }
            }
        }

        return null
    }

    /**
     * Select word at position, returning buffer coordinates.
     * Handles the case where line text needs to be retrieved.
     *
     * @param lineText The line text
     * @param col Column position in line
     * @param row Row position (passed through for result)
     * @return Pair of start and end coordinates (col, row)
     */
    fun selectWordAtPosition(
        lineText: String,
        col: Int,
        row: Int
    ): Pair<Pair<Int, Int>, Pair<Int, Int>>? {
        val result = selectWordAt(lineText, col) ?: return null
        return Pair(Pair(result.first, row), Pair(result.second, row))
    }
}
