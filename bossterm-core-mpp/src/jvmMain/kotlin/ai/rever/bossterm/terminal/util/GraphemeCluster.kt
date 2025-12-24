package ai.rever.bossterm.terminal.util

/**
 * Represents a single grapheme cluster - the smallest unit of text that should be treated
 * as indivisible from a user's perspective.
 *
 * Examples:
 * - Single BMP character: "a"
 * - Surrogate pair: "𝕳" (U+1D573, Mathematical Bold H)
 * - Emoji with variation selector: "☁️" (cloud + U+FE0F)
 * - ZWJ sequence: "👨‍👩‍👧‍👦" (family emoji)
 * - Emoji with skin tone: "👍🏽" (thumbs up + medium skin tone)
 * - Combining characters: "á" (a + combining acute accent)
 *
 * @property text The string representation of this grapheme cluster
 * @property visualWidth The number of terminal cells this grapheme occupies (0, 1, or 2)
 * @property codePoints Array of Unicode code points that make up this grapheme
 */
data class GraphemeCluster(
    val text: String,
    val visualWidth: Int,
    val codePoints: IntArray
) {
    /**
     * Checks if this grapheme is a combining character sequence (width 0).
     * Combining characters overlay on the base character.
     */
    val isCombining: Boolean
        get() = visualWidth == 0

    /**
     * Checks if this grapheme contains emoji characters.
     * This includes emoji in supplementary planes (U+1F000+) and emoji presentation variants.
     */
    val isEmoji: Boolean
        get() {
            if (codePoints.isEmpty()) return false
            return GraphemeUtils.isEmojiPresentation(codePoints[0]) ||
                   hasVariationSelector(UnicodeConstants.VARIATION_SELECTOR_EMOJI)
        }

    /**
     * Checks if this grapheme contains a variation selector (U+FE0E for text, U+FE0F for emoji).
     */
    fun hasVariationSelector(selector: Int? = null): Boolean {
        return if (selector != null) {
            codePoints.contains(selector)
        } else {
            codePoints.any { UnicodeConstants.isVariationSelector(it) }
        }
    }

    /**
     * Checks if this grapheme contains a skin tone modifier (U+1F3FB-U+1F3FF).
     */
    val hasSkinTone: Boolean
        get() = codePoints.any { UnicodeConstants.isSkinToneModifier(it) }

    /**
     * Checks if this grapheme contains a Zero-Width Joiner (U+200D).
     * ZWJ is used to join multiple emoji into a single visual unit.
     */
    val hasZWJ: Boolean
        get() = codePoints.contains(UnicodeConstants.ZWJ)

    /**
     * Checks if this grapheme is a surrogate pair (outside BMP, U+10000+).
     */
    val isSurrogatePair: Boolean
        get() = codePoints.isNotEmpty() && codePoints[0] >= 0x10000

    /**
     * Checks if this grapheme is double-width (occupies 2 terminal cells).
     * Common for CJK characters, fullwidth forms, and emoji.
     */
    val isDoubleWidth: Boolean
        get() = visualWidth == 2

    /**
     * Returns a debug string representation showing code points.
     */
    fun toDebugString(): String {
        val codePointsStr = codePoints.joinToString(", ") { "U+%04X".format(it) }
        return "GraphemeCluster(text='$text', width=$visualWidth, codePoints=[$codePointsStr])"
    }

    // Custom equals/hashCode needed because IntArray doesn't have structural equality by default
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GraphemeCluster

        if (text != other.text) return false
        if (visualWidth != other.visualWidth) return false
        if (!codePoints.contentEquals(other.codePoints)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text.hashCode()
        result = 31 * result + visualWidth
        result = 31 * result + codePoints.contentHashCode()
        return result
    }

    companion object {
        /**
         * Pre-cached GraphemeCluster objects for ASCII characters (0-127).
         * Avoids allocation for the most common case in terminal output.
         * All ASCII characters have width 1 (except control characters which are typically not displayed).
         */
        private val ASCII_CLUSTERS: Array<GraphemeCluster> = Array(128) { code ->
            val c = code.toChar()
            GraphemeCluster(
                text = c.toString(),
                visualWidth = if (code < 32 || code == 127) 0 else 1,  // Control chars have width 0
                codePoints = intArrayOf(code)
            )
        }

        /**
         * Creates a GraphemeCluster from a single character.
         * Fast path for ASCII characters uses pre-cached objects.
         */
        fun fromChar(c: Char, width: Int): GraphemeCluster {
            val code = c.code
            // Fast path: use cached ASCII cluster if width matches
            if (code < 128) {
                val cached = ASCII_CLUSTERS[code]
                if (cached.visualWidth == width) return cached
            }
            // Fallback: create new cluster
            return GraphemeCluster(
                text = c.toString(),
                visualWidth = width,
                codePoints = intArrayOf(code)
            )
        }

        /**
         * Creates a GraphemeCluster from a string and calculated width.
         * Fast path for single ASCII character uses pre-cached objects.
         * Extracts code points from the string for other cases.
         */
        fun fromString(text: String, width: Int): GraphemeCluster {
            // Fast path: single ASCII character
            if (text.length == 1) {
                val code = text[0].code
                if (code < 128) {
                    val cached = ASCII_CLUSTERS[code]
                    if (cached.visualWidth == width) return cached
                }
            }

            // Full construction for complex graphemes
            val codePoints = extractCodePoints(text)
            return GraphemeCluster(
                text = text,
                visualWidth = width,
                codePoints = codePoints
            )
        }

        /**
         * Thread-local buffer for code point extraction.
         */
        private val codePointBuffer: ThreadLocal<IntArray> = ThreadLocal.withInitial {
            IntArray(16)
        }

        /**
         * Extracts code points from a string, using ThreadLocal buffer when possible.
         */
        private fun extractCodePoints(text: String): IntArray {
            if (text.isEmpty()) return IntArray(0)

            // Estimate code point count (chars / 1.5 is a reasonable estimate)
            val buffer = codePointBuffer.get()
            var count = 0
            var offset = 0

            while (offset < text.length && count < buffer.size) {
                val codePoint = text.codePointAt(offset)
                buffer[count++] = codePoint
                offset += Character.charCount(codePoint)
            }

            // If we fit in buffer, copy out
            if (offset >= text.length) {
                return buffer.copyOf(count)
            }

            // Otherwise, fall back to full extraction with larger array
            val codePoints = mutableListOf<Int>()
            offset = 0
            while (offset < text.length) {
                val codePoint = text.codePointAt(offset)
                codePoints.add(codePoint)
                offset += Character.charCount(codePoint)
            }
            return codePoints.toIntArray()
        }
    }
}
