package ai.rever.bossterm.compose.ai

import ai.rever.bossterm.compose.EmbeddableTerminal
import ai.rever.bossterm.compose.settings.TerminalSettingsOverride
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * Dialog state for AI assistant installation.
 */
sealed class InstallDialogState {
    object Installing : InstallDialogState()
    object Success : InstallDialogState()
    data class Error(val exitCode: Int) : InstallDialogState()
}

/**
 * Dialog for installing AI assistants with an embedded terminal.
 *
 * Shows installation progress in a terminal, auto-closes on success,
 * and displays error with dismiss option on failure.
 *
 * @param assistant The AI assistant to install
 * @param installCommand The installation command to run
 * @param onDismiss Called when dialog should close
 * @param onInstallComplete Called when installation completes (success or failure)
 */
@Composable
fun AIAssistantInstallDialog(
    assistant: AIAssistantDefinition,
    installCommand: String,
    onDismiss: () -> Unit,
    onInstallComplete: (success: Boolean) -> Unit = {}
) {
    var dialogState by remember { mutableStateOf<InstallDialogState>(InstallDialogState.Installing) }

    Dialog(
        onDismissRequest = {
            // Only allow dismiss if not installing or if there's an error
            if (dialogState != InstallDialogState.Installing) {
                onDismiss()
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = dialogState != InstallDialogState.Installing,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .width(700.dp)
                .height(450.dp)
                .clip(RoundedCornerShape(12.dp)),
            color = Color(0xFF1E1E1E),
            elevation = 8.dp
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF2D2D2D))
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Installing ${assistant.displayName}",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )

                    // Close button (only enabled when not installing)
                    IconButton(
                        onClick = onDismiss,
                        enabled = dialogState != InstallDialogState.Installing,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = if (dialogState != InstallDialogState.Installing)
                                Color.White.copy(alpha = 0.7f)
                            else
                                Color.White.copy(alpha = 0.3f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                // Terminal or error content
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    when (val state = dialogState) {
                        is InstallDialogState.Installing -> {
                            // Embedded terminal running installation
                            EmbeddableTerminal(
                                initialCommand = installCommand,
                                onInitialCommandComplete = { success, exitCode ->
                                    if (success) {
                                        dialogState = InstallDialogState.Success
                                        onInstallComplete(true)
                                        // Auto-close on success after brief delay
                                        // (handled by LaunchedEffect below)
                                    } else {
                                        dialogState = InstallDialogState.Error(exitCode)
                                        onInstallComplete(false)
                                    }
                                },
                                settingsOverride = TerminalSettingsOverride(
                                    showScrollbar = true,
                                    scrollbarWidth = 8f
                                ),
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        is InstallDialogState.Success -> {
                            // Brief success state before auto-close
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "✓",
                                        color = Color(0xFF4CAF50),
                                        fontSize = 48.sp
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Installation complete!",
                                        color = Color(0xFF4CAF50),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }

                        is InstallDialogState.Error -> {
                            // Error state with dismiss button
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Error,
                                    contentDescription = null,
                                    tint = Color(0xFFE57373),
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Installation Failed",
                                    color = Color(0xFFE57373),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Exit code: ${state.exitCode}",
                                    color = Color.White.copy(alpha = 0.6f),
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                                Button(
                                    onClick = onDismiss,
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(0xFF3C3C3C)
                                    )
                                ) {
                                    Text("Dismiss", color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Auto-close on success after brief delay
    LaunchedEffect(dialogState) {
        if (dialogState == InstallDialogState.Success) {
            kotlinx.coroutines.delay(1500)
            onDismiss()
        }
    }
}
