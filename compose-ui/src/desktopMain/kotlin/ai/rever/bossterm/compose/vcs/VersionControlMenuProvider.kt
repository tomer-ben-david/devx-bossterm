package ai.rever.bossterm.compose.vcs

import ai.rever.bossterm.compose.ContextMenuElement
import ai.rever.bossterm.compose.ContextMenuItem
import ai.rever.bossterm.compose.ContextMenuSection
import ai.rever.bossterm.compose.ContextMenuSubmenu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.Desktop
import java.net.URI

/**
 * Provides context menu items for version control operations (Git and GitHub CLI).
 *
 * Detects if git and gh are installed and shows appropriate menu items:
 * - If installed: Shows submenu with common commands
 * - If not installed: Shows install option with link to download
 */
class VersionControlMenuProvider {

    /**
     * Cached installation status to avoid repeated `which` calls.
     * Updated each time getMenuItems is called with refresh=true.
     */
    private var gitInstalled: Boolean? = null
    private var ghInstalled: Boolean? = null

    /**
     * Detect if a command is installed by checking `which`.
     */
    private fun isCommandInstalled(command: String): Boolean {
        return try {
            val process = ProcessBuilder("which", command)
                .redirectErrorStream(true)
                .start()
            val exitCode = process.waitFor()
            exitCode == 0
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Refresh installation status for git and gh.
     */
    suspend fun refreshStatus() = withContext(Dispatchers.IO) {
        gitInstalled = isCommandInstalled("git")
        ghInstalled = isCommandInstalled("gh")
    }

    /**
     * Get cached or fresh installation status.
     */
    fun getStatus(): Pair<Boolean, Boolean> {
        if (gitInstalled == null) gitInstalled = isCommandInstalled("git")
        if (ghInstalled == null) ghInstalled = isCommandInstalled("gh")
        return Pair(gitInstalled ?: false, ghInstalled ?: false)
    }

    /**
     * Generate context menu items for version control.
     *
     * @param terminalWriter Function to write commands to the terminal
     * @param statusOverride Optional pre-computed status (gitInstalled, ghInstalled)
     * @return List of context menu elements for the Version Control section
     */
    fun getMenuItems(
        terminalWriter: (String) -> Unit,
        statusOverride: Pair<Boolean, Boolean>? = null
    ): List<ContextMenuElement> {
        val (isGitInstalled, isGhInstalled) = statusOverride ?: getStatus()

        val vcsItems = mutableListOf<ContextMenuElement>()

        // Git submenu
        vcsItems.add(buildGitMenu(isGitInstalled, terminalWriter))

        // GitHub CLI submenu
        vcsItems.add(buildGhMenu(isGhInstalled, terminalWriter))

        return listOf(
            ContextMenuSubmenu(
                id = "vcs_menu",
                label = "Version Control",
                items = vcsItems
            )
        )
    }

    /**
     * Build Git submenu with common commands or install option.
     */
    private fun buildGitMenu(isInstalled: Boolean, terminalWriter: (String) -> Unit): ContextMenuElement {
        if (!isInstalled) {
            return ContextMenuSubmenu(
                id = "git_submenu",
                label = "Git",
                items = listOf(
                    ContextMenuItem(
                        id = "git_install",
                        label = "Install Git",
                        action = {
                            openUrl("https://git-scm.com/downloads")
                        }
                    )
                )
            )
        }

        val gitCommands = listOf(
            ContextMenuSection(id = "git_status_section", label = "Status"),
            ContextMenuItem(
                id = "git_status",
                label = "git status",
                action = { terminalWriter("git status\n") }
            ),
            ContextMenuItem(
                id = "git_diff",
                label = "git diff",
                action = { terminalWriter("git diff\n") }
            ),
            ContextMenuItem(
                id = "git_log",
                label = "git log --oneline -10",
                action = { terminalWriter("git log --oneline -10\n") }
            ),
            ContextMenuSection(id = "git_staging_section", label = "Staging"),
            ContextMenuItem(
                id = "git_add_all",
                label = "git add .",
                action = { terminalWriter("git add .\n") }
            ),
            ContextMenuItem(
                id = "git_add_interactive",
                label = "git add -p",
                action = { terminalWriter("git add -p\n") }
            ),
            ContextMenuItem(
                id = "git_reset",
                label = "git reset HEAD",
                action = { terminalWriter("git reset HEAD\n") }
            ),
            ContextMenuSection(id = "git_commit_section", label = "Commit"),
            ContextMenuItem(
                id = "git_commit",
                label = "git commit",
                action = { terminalWriter("git commit\n") }
            ),
            ContextMenuItem(
                id = "git_commit_amend",
                label = "git commit --amend",
                action = { terminalWriter("git commit --amend\n") }
            ),
            ContextMenuSection(id = "git_remote_section", label = "Remote"),
            ContextMenuItem(
                id = "git_push",
                label = "git push",
                action = { terminalWriter("git push\n") }
            ),
            ContextMenuItem(
                id = "git_pull",
                label = "git pull",
                action = { terminalWriter("git pull\n") }
            ),
            ContextMenuItem(
                id = "git_fetch",
                label = "git fetch --all",
                action = { terminalWriter("git fetch --all\n") }
            ),
            ContextMenuSection(id = "git_branch_section", label = "Branches"),
            ContextMenuItem(
                id = "git_branch",
                label = "git branch -a",
                action = { terminalWriter("git branch -a\n") }
            ),
            ContextMenuSubmenu(
                id = "git_checkout_submenu",
                label = "git checkout",
                items = listOf(
                    ContextMenuItem(
                        id = "git_checkout_prev",
                        label = "Previous branch (-)",
                        action = { terminalWriter("git checkout -\n") }
                    ),
                    ContextMenuItem(
                        id = "git_checkout_main",
                        label = "main",
                        action = { terminalWriter("git checkout main\n") }
                    ),
                    ContextMenuItem(
                        id = "git_checkout_master",
                        label = "master",
                        action = { terminalWriter("git checkout master\n") }
                    ),
                    ContextMenuItem(
                        id = "git_checkout_dev",
                        label = "dev / develop",
                        action = { terminalWriter("git checkout dev || git checkout develop\n") }
                    ),
                    ContextMenuSection(id = "git_checkout_new_section"),
                    ContextMenuItem(
                        id = "git_checkout_new",
                        label = "New branch (-b) ...",
                        action = { terminalWriter("git checkout -b ") }
                    ),
                    ContextMenuItem(
                        id = "git_checkout_branch",
                        label = "Other branch ...",
                        action = { terminalWriter("git checkout ") }
                    ),
                    ContextMenuSection(id = "git_checkout_discard_section"),
                    ContextMenuItem(
                        id = "git_checkout_discard",
                        label = "Discard all changes (-- .)",
                        action = { terminalWriter("git checkout -- .\n") }
                    )
                )
            ),
            ContextMenuSubmenu(
                id = "git_switch_submenu",
                label = "git switch",
                items = listOf(
                    ContextMenuItem(
                        id = "git_switch_prev",
                        label = "Previous branch (-)",
                        action = { terminalWriter("git switch -\n") }
                    ),
                    ContextMenuItem(
                        id = "git_switch_main",
                        label = "main",
                        action = { terminalWriter("git switch main\n") }
                    ),
                    ContextMenuItem(
                        id = "git_switch_master",
                        label = "master",
                        action = { terminalWriter("git switch master\n") }
                    ),
                    ContextMenuItem(
                        id = "git_switch_dev",
                        label = "dev / develop",
                        action = { terminalWriter("git switch dev || git switch develop\n") }
                    ),
                    ContextMenuSection(id = "git_switch_new_section"),
                    ContextMenuItem(
                        id = "git_switch_create",
                        label = "Create new branch (-c) ...",
                        action = { terminalWriter("git switch -c ") }
                    ),
                    ContextMenuItem(
                        id = "git_switch_branch",
                        label = "Other branch ...",
                        action = { terminalWriter("git switch ") }
                    )
                )
            )
        )

        return ContextMenuSubmenu(
            id = "git_submenu",
            label = "Git",
            items = gitCommands
        )
    }

    /**
     * Build GitHub CLI submenu with common commands or install option.
     */
    private fun buildGhMenu(isInstalled: Boolean, terminalWriter: (String) -> Unit): ContextMenuElement {
        if (!isInstalled) {
            return ContextMenuSubmenu(
                id = "gh_submenu",
                label = "GitHub CLI",
                items = listOf(
                    ContextMenuItem(
                        id = "gh_install",
                        label = "Install GitHub CLI",
                        action = {
                            openUrl("https://cli.github.com/")
                        }
                    )
                )
            )
        }

        val ghCommands = listOf(
            ContextMenuSection(id = "gh_auth_section", label = "Auth"),
            ContextMenuItem(
                id = "gh_auth_status",
                label = "gh auth status",
                action = { terminalWriter("gh auth status\n") }
            ),
            ContextMenuItem(
                id = "gh_auth_login",
                label = "gh auth login",
                action = { terminalWriter("gh auth login\n") }
            ),
            ContextMenuSection(id = "gh_pr_section", label = "Pull Requests"),
            ContextMenuItem(
                id = "gh_pr_list",
                label = "gh pr list",
                action = { terminalWriter("gh pr list\n") }
            ),
            ContextMenuItem(
                id = "gh_pr_status",
                label = "gh pr status",
                action = { terminalWriter("gh pr status\n") }
            ),
            ContextMenuItem(
                id = "gh_pr_create",
                label = "gh pr create",
                action = { terminalWriter("gh pr create\n") }
            ),
            ContextMenuItem(
                id = "gh_pr_checkout",
                label = "gh pr checkout ...",
                action = { terminalWriter("gh pr checkout ") }
            ),
            ContextMenuItem(
                id = "gh_pr_view",
                label = "gh pr view --web",
                action = { terminalWriter("gh pr view --web\n") }
            ),
            ContextMenuSection(id = "gh_issue_section", label = "Issues"),
            ContextMenuItem(
                id = "gh_issue_list",
                label = "gh issue list",
                action = { terminalWriter("gh issue list\n") }
            ),
            ContextMenuItem(
                id = "gh_issue_create",
                label = "gh issue create",
                action = { terminalWriter("gh issue create\n") }
            ),
            ContextMenuItem(
                id = "gh_issue_view",
                label = "gh issue view ...",
                action = { terminalWriter("gh issue view ") }
            ),
            ContextMenuSection(id = "gh_repo_section", label = "Repository"),
            ContextMenuItem(
                id = "gh_repo_view",
                label = "gh repo view --web",
                action = { terminalWriter("gh repo view --web\n") }
            ),
            ContextMenuItem(
                id = "gh_repo_clone",
                label = "gh repo clone ...",
                action = { terminalWriter("gh repo clone ") }
            )
        )

        return ContextMenuSubmenu(
            id = "gh_submenu",
            label = "GitHub CLI",
            items = ghCommands
        )
    }

    /**
     * Open URL in default browser.
     */
    private fun openUrl(url: String) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(URI(url))
            }
        } catch (e: Exception) {
            // Silently fail - user can manually visit the URL
        }
    }
}
