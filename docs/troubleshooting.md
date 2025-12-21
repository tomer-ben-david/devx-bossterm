# Troubleshooting Guide

This guide covers common issues when integrating BossTerm into your application.

## Focus Management Issues

### Problem: Terminal Not Receiving Keyboard Input

**Symptom**: After embedding `EmbeddableTerminal` in your application, clicking on the terminal doesn't give it keyboard focus. Keys are either ignored or handled by the parent container.

**Root Cause**: Parent Compose containers with focus modifiers (`.focusable()`, `.focusRequester()`, `.clickable()` with `requestFocus()`) intercept focus before the terminal can receive it.

**Example of problematic code**:

```kotlin
// DON'T DO THIS - parent steals focus from terminal
val parentFocusRequester = remember { FocusRequester() }

Column(
    modifier = Modifier
        .focusRequester(parentFocusRequester)
        .focusable()
        .clickable {
            parentFocusRequester.requestFocus()  // Steals focus!
        }
) {
    EmbeddableTerminal()  // Never receives focus
}
```

The issue is that `.clickable()` fires its action before the terminal's internal click handler can request focus. The parent's `requestFocus()` wins, forcing focus onto the Column instead of the terminal.

### Solution: Let Focus Flow Naturally

Remove competing focus modifiers from parent containers. Use `.onFocusChanged()` to track focus state instead:

```kotlin
// CORRECT - parent observes focus without competing
Column(
    modifier = Modifier
        .onFocusChanged { focusState ->
            // Track whether terminal (or any child) has focus
            if (focusState.hasFocus) {
                // A child has focus (terminal is focused)
            }
        }
        // NO .focusable()
        // NO .clickable() with requestFocus()
) {
    EmbeddableTerminal()  // Can receive focus naturally
}
```

### Understanding Compose Focus Propagation

In Compose, focus events bubble up from children to parents:

1. When `EmbeddableTerminal` receives focus, it calls `focusRequester.requestFocus()` internally
2. The parent's `onFocusChanged` callback fires with `hasFocus = true`
3. You can track this without stealing focus from the terminal

**Key insight**: `focusState.hasFocus` is true when the component OR any of its children has focus. This lets parents know when focus is "inside" them without competing for it.

### Complex Layout Example

For applications with multiple panels (sidebar, toolbar, content area):

```kotlin
@Composable
fun SplitPaneApp() {
    var terminalHasFocus by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxSize()) {
        // Sidebar - can have its own focus management
        Sidebar(
            modifier = Modifier.width(200.dp)
        )

        // Terminal container - observes focus, doesn't compete
        Box(
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { state ->
                    terminalHasFocus = state.hasFocus
                }
                // Optionally style based on focus
                .border(
                    width = 2.dp,
                    color = if (terminalHasFocus) Color.Blue else Color.Transparent
                )
        ) {
            EmbeddableTerminal(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
```

### Focus with Toolbar Buttons

If your app has a toolbar with buttons that interact with the terminal:

```kotlin
@Composable
fun TerminalWithToolbar() {
    val terminalState = rememberEmbeddableTerminalState()
    val terminalFocusRequester = remember { FocusRequester() }

    Column {
        // Toolbar - clicks here will naturally take focus away
        Row(modifier = Modifier.height(40.dp)) {
            Button(onClick = {
                terminalState.write("ls -la\n")
                // Return focus to terminal after button action
                terminalFocusRequester.requestFocus()
            }) {
                Text("List Files")
            }
        }

        // Terminal with explicit focus requester
        Box(
            modifier = Modifier
                .weight(1f)
                .focusRequester(terminalFocusRequester)
        ) {
            EmbeddableTerminal(
                state = terminalState,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
```

### Debugging Focus Issues

To debug focus problems, add logging to track focus flow:

```kotlin
Box(
    modifier = Modifier
        .onFocusChanged { state ->
            println("Container focus: hasFocus=${state.hasFocus}, isFocused=${state.isFocused}")
            // hasFocus: true if this OR any child has focus
            // isFocused: true only if THIS component has focus (not children)
        }
) {
    EmbeddableTerminal()
}
```

---

## Terminal Process Issues

### Problem: Shell Process Not Starting

**Symptom**: Terminal shows blank screen, no prompt appears.

**Possible causes**:

1. **Shell not found**: BossTerm uses the `SHELL` environment variable. Ensure it's set correctly.
2. **Working directory issues**: Default working directory might not exist.

**Solution**: Check your environment:

```kotlin
// Verify SHELL is set
println("Shell: ${System.getenv("SHELL")}")

// Specify explicit shell if needed (via settings)
EmbeddableTerminal(
    settings = TerminalSettings(
        // Settings are loaded from ~/.bossterm/settings.json by default
    )
)
```

### Problem: Terminal Exits Immediately

**Symptom**: Terminal shows briefly then closes or shows exit code.

**Possible causes**:

1. Shell configuration error (`.bashrc`, `.zshrc` syntax error)
2. Interactive shell not configured properly

**Solution**: Check shell configuration files for errors:

```bash
# Test your shell configuration
bash -l -c "echo 'Shell OK'"
zsh -l -c "echo 'Shell OK'"
```

---

## Rendering Issues

### Problem: Missing Characters or Glyphs

**Symptom**: Some characters display as boxes or are missing entirely.

**Possible causes**:

1. Font doesn't have required glyphs
2. Emoji or special characters not supported

**Solution**: BossTerm includes MesloLGS Nerd Font with powerline symbols. For custom fonts:

```kotlin
EmbeddableTerminal(
    settings = TerminalSettings(
        // Use a font with good Unicode coverage
        fontName = "JetBrains Mono"  // Or another Nerd Font
    )
)
```

### Problem: Emoji Not Rendering Correctly

**Symptom**: Emoji appear as separate characters or wrong symbols.

BossTerm handles emoji with variation selectors (like ☁️) specially. If you see issues:

1. Ensure you're using a recent version of BossTerm
2. Some complex emoji sequences may have rendering limitations

---

## Performance Issues

### Problem: High CPU Usage During Output

**Symptom**: CPU spikes when terminal has heavy output (e.g., `cat` large file).

BossTerm includes adaptive debouncing to handle this. If you still see issues:

1. Check `scrollbackLines` setting - very large values (>50000) may impact performance
2. Consider if your use case needs all output displayed in real-time

---

## Integration Checklist

When embedding BossTerm, verify:

- [ ] No `.focusable()` on parent containers that wrap the terminal
- [ ] No `.clickable { requestFocus() }` competing with terminal
- [ ] Terminal container uses `.onFocusChanged()` for focus tracking
- [ ] `SHELL` environment variable is set correctly
- [ ] Terminal has sufficient size (not zero width/height)

---

## Getting Help

If you're still experiencing issues:

1. Check the [GitHub Issues](https://github.com/kshivang/BossTerm/issues) for similar problems
2. Enable debug mode with `Ctrl/Cmd+Shift+D` to inspect terminal state
3. Open a new issue with reproduction steps and relevant code snippets
