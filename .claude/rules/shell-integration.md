# Shell Integration Setup

BossTerm supports OSC 7 (working directory tracking) and OSC 133 (command completion notifications).

## OSC 7 - Working Directory Tracking

Enables new tabs to inherit CWD from active tab.

**Bash** (`~/.bashrc`):
```bash
PROMPT_COMMAND='echo -ne "\033]7;file://${HOSTNAME}${PWD}\007"'
```

**Zsh** (`~/.zshrc`):
```bash
precmd() { echo -ne "\033]7;file://${HOST}${PWD}\007" }
```

## OSC 133 - Command Completion Notifications

Enables system notifications when commands complete while window is unfocused (like iTerm2).

**Bash** (`~/.bashrc`):
```bash
__prompt_command() {
    local exit_code=$?
    echo -ne "\033]133;D;${exit_code}\007"  # Command finished
    echo -ne "\033]133;A\007"                # Prompt starting
}
PROMPT_COMMAND='__prompt_command'
trap 'echo -ne "\033]133;B\007"' DEBUG       # Command starting
```

**Zsh** (`~/.zshrc`):
```bash
precmd() {
    local exit_code=$?
    print -Pn "\e]133;D;${exit_code}\a"  # Command finished
    print -Pn "\e]133;A\a"                # Prompt starting
    print -Pn "\e]7;file://${HOST}${PWD}\a"  # Also emit OSC 7
}
preexec() { print -Pn "\e]133;B\a" }      # Command starting
```

## Related Settings

In `~/.bossterm/settings.json`:
- `notifyOnCommandComplete`: Enable notifications (default: true)
- `notifyMinDurationSeconds`: Min command duration (default: 5)
- `notifyShowExitCode`: Show exit code (default: true)
- `notifyWithSound`: Play sound (default: true)
