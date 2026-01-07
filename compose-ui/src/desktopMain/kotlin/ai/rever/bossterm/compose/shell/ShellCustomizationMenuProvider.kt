package ai.rever.bossterm.compose.shell

import ai.rever.bossterm.compose.ContextMenuElement
import ai.rever.bossterm.compose.ContextMenuItem
import ai.rever.bossterm.compose.ContextMenuSection
import ai.rever.bossterm.compose.ContextMenuSubmenu
import ai.rever.bossterm.compose.ai.AIAssistantLauncher
import ai.rever.bossterm.compose.util.UrlOpener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

/**
 * Provides context menu items for shell customization tools (Starship, etc.).
 *
 * Detects if starship is installed and shows appropriate menu items:
 * - If installed: Shows submenu with configuration and preset options
 * - If not installed: Shows install option with link to documentation
 */
class ShellCustomizationMenuProvider {

    /**
     * Cached installation status to avoid repeated `which` calls.
     */
    private var starshipInstalled: Boolean? = null

    /**
     * Detect if a command is installed by checking `which`.
     */
    private fun isCommandInstalled(command: String): Boolean {
        return try {
            val process = ProcessBuilder("which", command)
                .redirectErrorStream(true)
                .start()
            val completed = process.waitFor(2, TimeUnit.SECONDS)
            completed && process.exitValue() == 0
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Refresh installation status for shell customization tools.
     */
    suspend fun refreshStatus() = withContext(Dispatchers.IO) {
        starshipInstalled = isCommandInstalled("starship")
    }

    /**
     * Get cached installation status for Starship.
     */
    fun getStatus(): Boolean? = starshipInstalled

    /**
     * Get context menu items for shell customization.
     *
     * @param terminalWriter Function to write commands to terminal
     * @param onInstallRequest Callback for install requests (toolId, command, npmCommand)
     * @param statusOverride Override for installation status (for testing)
     * @return List of context menu elements
     */
    fun getMenuItems(
        terminalWriter: (String) -> Unit,
        onInstallRequest: ((String, String, String?) -> Unit)? = null,
        statusOverride: Map<String, Boolean>? = null
    ): List<ContextMenuElement> {
        val isStarshipInstalled = statusOverride?.get("starship")
            ?: (starshipInstalled ?: isCommandInstalled("starship"))

        val shellItems = mutableListOf<ContextMenuElement>()

        // Starship menu
        if (!isStarshipInstalled) {
            // Not installed: Install + Learn More submenu
            shellItems.add(
                ContextMenuSubmenu(
                    id = "starship_submenu",
                    label = "Starship",
                    items = listOf(
                        ContextMenuItem(
                            id = "starship_install",
                            label = "Install",
                            action = {
                                if (onInstallRequest != null) {
                                    onInstallRequest("starship", AIAssistantLauncher.getStarshipInstallCommand(), null)
                                } else {
                                    UrlOpener.open("https://starship.rs/")
                                }
                            }
                        ),
                        ContextMenuItem(
                            id = "starship_learnmore",
                            label = "Learn More",
                            action = { UrlOpener.open("https://starship.rs/") }
                        )
                    )
                )
            )
        } else {
            // Installed: Configuration submenu
            shellItems.add(buildStarshipMenu(terminalWriter))
        }

        return if (shellItems.isEmpty()) {
            emptyList()
        } else {
            listOf(
                ContextMenuSubmenu(
                    id = "shell_submenu",
                    label = "Shell",
                    items = shellItems
                )
            )
        }
    }

    /**
     * Build Starship submenu with configuration options.
     */
    private fun buildStarshipMenu(terminalWriter: (String) -> Unit): ContextMenuSubmenu {
        return ContextMenuSubmenu(
            id = "starship_submenu",
            label = "Starship",
            items = listOf(
                // Configuration section
                ContextMenuSection(id = "starship_config_section", label = "Configuration"),
                ContextMenuItem(
                    id = "starship_config_edit",
                    label = "Edit Config",
                    action = { terminalWriter("\${EDITOR:-nano} ~/.config/starship.toml\n") }
                ),
                ContextMenuItem(
                    id = "starship_config_init",
                    label = "Create Default Config",
                    action = { terminalWriter("mkdir -p ~/.config && starship preset -o ~/.config/starship.toml\n") }
                ),

                // Presets section
                ContextMenuSection(id = "starship_presets_section", label = "Apply Preset"),
                ContextMenuSubmenu(
                    id = "starship_presets_submenu",
                    label = "Presets",
                    items = listOf(
                        ContextMenuItem(
                            id = "starship_preset_nerd",
                            label = "Nerd Font Symbols",
                            action = { terminalWriter("starship preset nerd-font-symbols -o ~/.config/starship.toml\n") }
                        ),
                        ContextMenuItem(
                            id = "starship_preset_plain",
                            label = "Plain Text",
                            action = { terminalWriter("starship preset plain-text-symbols -o ~/.config/starship.toml\n") }
                        ),
                        ContextMenuItem(
                            id = "starship_preset_nonerd",
                            label = "No Nerd Font",
                            action = { terminalWriter("starship preset no-nerd-font -o ~/.config/starship.toml\n") }
                        ),
                        ContextMenuItem(
                            id = "starship_preset_pastel",
                            label = "Pastel Powerline",
                            action = { terminalWriter("starship preset pastel-powerline -o ~/.config/starship.toml\n") }
                        ),
                        ContextMenuItem(
                            id = "starship_preset_bracketed",
                            label = "Bracketed Segments",
                            action = { terminalWriter("starship preset bracketed-segments -o ~/.config/starship.toml\n") }
                        ),
                        ContextMenuItem(
                            id = "starship_preset_gruvbox",
                            label = "Gruvbox Rainbow",
                            action = { terminalWriter("starship preset gruvbox-rainbow -o ~/.config/starship.toml\n") }
                        ),
                        ContextMenuItem(
                            id = "starship_preset_tokyo",
                            label = "Tokyo Night",
                            action = { terminalWriter("starship preset tokyo-night -o ~/.config/starship.toml\n") }
                        )
                    )
                ),

                // Shell Setup section
                ContextMenuSection(id = "starship_setup_section", label = "Shell Setup"),
                ContextMenuItem(
                    id = "starship_setup_bash",
                    label = "Setup for Bash",
                    action = {
                        terminalWriter("echo 'eval \"\$(starship init bash)\"' >> ~/.bashrc && echo '✓ Added to ~/.bashrc - restart shell or run: source ~/.bashrc'\n")
                    }
                ),
                ContextMenuItem(
                    id = "starship_setup_zsh",
                    label = "Setup for Zsh",
                    action = {
                        terminalWriter("echo 'eval \"\$(starship init zsh)\"' >> ~/.zshrc && echo '✓ Added to ~/.zshrc - restart shell or run: source ~/.zshrc'\n")
                    }
                ),
                ContextMenuItem(
                    id = "starship_setup_fish",
                    label = "Setup for Fish",
                    action = {
                        terminalWriter("echo 'starship init fish | source' >> ~/.config/fish/config.fish && echo '✓ Added to config.fish - restart shell'\n")
                    }
                ),

                // Help section
                ContextMenuSection(id = "starship_help_section"),
                ContextMenuItem(
                    id = "starship_help",
                    label = "Help",
                    action = { terminalWriter("starship --help\n") }
                ),
                ContextMenuItem(
                    id = "starship_docs",
                    label = "Documentation",
                    action = { UrlOpener.open("https://starship.rs/config/") }
                )
            )
        )
    }
}
