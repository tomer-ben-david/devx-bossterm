package ai.rever.bossterm.compose.settings

import androidx.compose.runtime.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState

/**
 * Settings window (non-modal, allows terminal interaction).
 *
 * Uses a pending settings state for smooth slider interaction:
 * - onSettingsChange updates UI immediately (no I/O)
 * - onSettingsSave persists to disk (called on slider release)
 *
 * @param visible Whether the window is visible
 * @param onDismiss Called when the window should be closed
 * @param onRestartApp Called when app should restart (for settings that require restart)
 */
@Composable
fun SettingsWindow(
    visible: Boolean,
    onDismiss: () -> Unit,
    onRestartApp: (() -> Unit)? = null
) {
    if (!visible) return

    val settingsManager = remember { SettingsManager.instance }
    val savedSettings by settingsManager.settings.collectAsState()

    // Pending settings for smooth slider interaction (no I/O during drag)
    var pendingSettings by remember { mutableStateOf(savedSettings) }

    // Sync pending settings when saved settings change externally
    LaunchedEffect(savedSettings) {
        pendingSettings = savedSettings
    }

    Window(
        onCloseRequest = onDismiss,
        title = "BossTerm Settings",
        resizable = false,
        alwaysOnTop = false,
        state = rememberWindowState(
            size = DpSize(750.dp, 580.dp)
        )
    ) {
        SettingsPanel(
            settings = pendingSettings,
            onSettingsChange = { newSettings ->
                // Update UI immediately (no disk I/O)
                pendingSettings = newSettings
            },
            onSettingsSave = {
                // Save to disk only when slider is released
                settingsManager.updateSettings(pendingSettings)
            },
            onResetToDefaults = {
                settingsManager.resetToDefaults()
            },
            onRestartApp = onRestartApp
        )
    }
}
