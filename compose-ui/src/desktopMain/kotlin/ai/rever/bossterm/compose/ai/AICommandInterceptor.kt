package ai.rever.bossterm.compose.ai

import ai.rever.bossterm.terminal.model.CommandStateListener

/**
 * Intercepts AI assistant commands before they are sent to the shell.
 *
 * Tracks user input to detect when an AI assistant command is typed,
 * and can intercept the Enter key to show an install prompt if the
 * assistant is not installed.
 *
 * Requires OSC 133 shell integration for reliable prompt detection.
 * When shell integration is not configured, no interception occurs
 * (graceful fallback to normal behavior).
 *
 * @param detector The AI assistant detector for checking installation status
 * @param onInstallConfirm Callback invoked when an uninstalled AI command is detected.
 *                         Receives the assistant definition, the original command typed,
 *                         and a clearLine callback that should be called to clear the
 *                         typed command from shell.
 */
class AICommandInterceptor(
    private val detector: AIAssistantDetector,
    private val onInstallConfirm: (assistant: AIAssistantDefinition, originalCommand: String, clearLine: () -> Unit) -> Unit
) : CommandStateListener {

    // Callback to clear the current line in the terminal (Ctrl+U)
    var clearLineCallback: (() -> Unit)? = null

    // Current input buffer (cleared on prompt start and after Enter)
    private val inputBuffer = StringBuilder()

    // Are we at shell prompt? (false when in vim, running a command, etc.)
    // Only true after receiving OSC 133;A (prompt started)
    private var isAtPrompt = false

    /**
     * Called before each character is sent to the PTY.
     *
     * @param char The character being typed
     * @return true if the character should be consumed (not sent to shell),
     *         false if it should pass through normally
     */
    fun onCharacterTyped(char: Char): Boolean {
        if (!isAtPrompt) return false

        when (char) {
            '\r', '\n' -> {
                // Enter pressed - check for AI command
                val consumed = checkAndIntercept()
                inputBuffer.clear()
                return consumed
            }
            '\u007F', '\b' -> {
                // Backspace - remove last character
                if (inputBuffer.isNotEmpty()) {
                    inputBuffer.deleteAt(inputBuffer.length - 1)
                }
            }
            '\u0003' -> {
                // Ctrl+C - clear buffer
                inputBuffer.clear()
            }
            '\u0015' -> {
                // Ctrl+U - clear line
                inputBuffer.clear()
            }
            '\u0017' -> {
                // Ctrl+W - delete last word
                deleteLastWord()
            }
            else -> {
                // Printable characters
                if (char >= ' ') {
                    inputBuffer.append(char)
                }
            }
        }
        return false
    }

    /**
     * Check if the current input buffer contains an AI assistant command
     * that is not installed, and trigger the install confirmation if so.
     *
     * Only intercepts if detection has confirmed the assistant is NOT installed.
     * If detection hasn't run yet, the command passes through normally.
     *
     * @return true if the command was intercepted (Enter should be consumed),
     *         false otherwise
     */
    private fun checkAndIntercept(): Boolean {
        val originalCommand = inputBuffer.toString().trim()
        val commandName = extractCommandName(originalCommand)
        if (commandName == null) return false

        val assistant = AIAssistants.findByCommand(commandName)
        // Only intercept if we have CONFIRMED the assistant is not installed
        // This prevents false positives when detection hasn't run yet
        if (assistant != null && detector.isConfirmedNotInstalled(assistant.id)) {
            // Assistant confirmed not installed - trigger install confirmation
            // Pass the original command so it can be run after installation
            // Pass clearLine callback so the caller can clear the typed command
            val clearLine: () -> Unit = {
                clearLineCallback?.invoke()
            }
            onInstallConfirm(assistant, originalCommand, clearLine)
            return true // Consume Enter
        }
        return false
    }

    /**
     * Extract the command name from input like "claude --help" or "  claude".
     *
     * @param input The raw input string
     * @return The command name, or null if input is empty
     */
    private fun extractCommandName(input: String): String? {
        val trimmed = input.trim()
        if (trimmed.isEmpty()) return null

        // Handle pipe: "echo hello | claude" -> "claude"
        val lastPipe = trimmed.lastIndexOf('|')
        val commandPart = if (lastPipe >= 0) {
            trimmed.substring(lastPipe + 1).trim()
        } else {
            trimmed
        }

        if (commandPart.isEmpty()) return null

        // Get first word (command name)
        val firstSpace = commandPart.indexOf(' ')
        return if (firstSpace > 0) {
            commandPart.substring(0, firstSpace)
        } else {
            commandPart
        }
    }

    /**
     * Delete the last word from the input buffer (Ctrl+W behavior).
     */
    private fun deleteLastWord() {
        if (inputBuffer.isEmpty()) return

        // Skip trailing whitespace
        var i = inputBuffer.length - 1
        while (i >= 0 && inputBuffer[i].isWhitespace()) {
            i--
        }

        // Delete word characters
        while (i >= 0 && !inputBuffer[i].isWhitespace()) {
            i--
        }

        // Keep up to (and including) index i
        if (i < 0) {
            inputBuffer.clear()
        } else {
            inputBuffer.delete(i + 1, inputBuffer.length)
        }
    }

    // CommandStateListener implementation

    /**
     * Called when shell prompt is displayed (OSC 133;A).
     * Shell is ready for user input.
     */
    override fun onPromptStarted() {
        isAtPrompt = true
        inputBuffer.clear()
    }

    /**
     * Called when command execution begins (OSC 133;B).
     * User has entered a command and it's now running.
     */
    override fun onCommandStarted() {
        isAtPrompt = false
        inputBuffer.clear()
    }

    /**
     * Called when command finishes (OSC 133;D).
     * We wait for onPromptStarted to know we're ready for input again.
     */
    override fun onCommandFinished(exitCode: Int) {
        // No action needed - wait for next prompt
    }
}
