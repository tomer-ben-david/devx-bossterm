package ai.rever.bossterm.compose.hyperlinks

/**
 * Parsed SSH connection information from an ssh:// URL.
 */
data class SshConnectionInfo(
    val user: String?,
    val host: String,
    val port: Int = 22,
    val path: String? = null
) {
    /**
     * Convert to SSH command string.
     * Examples:
     * - SshConnectionInfo(null, "example.com", 22) -> "ssh example.com"
     * - SshConnectionInfo("user", "example.com", 22) -> "ssh user@example.com"
     * - SshConnectionInfo("user", "example.com", 2222) -> "ssh user@example.com -p 2222"
     */
    fun toCommand(): String {
        val sb = StringBuilder("ssh ")
        user?.let { sb.append("$it@") }
        sb.append(host)
        if (port != 22) sb.append(" -p $port")
        return sb.toString()
    }
}

/**
 * Parser for SSH URLs.
 * Supports formats:
 * - ssh://host
 * - ssh://host:port
 * - ssh://user@host
 * - ssh://user@host:port
 * - ssh://user@host:port/path
 */
object SshUrlParser {

    private val SSH_URL_REGEX = Regex(
        """^ssh://(?:([^@/:]+)@)?([^/:]+)(?::(\d+))?(/.*)?$"""
    )

    /**
     * Parse an SSH URL into connection information.
     *
     * @param url The SSH URL to parse (e.g., "ssh://user@host:22/path")
     * @return SshConnectionInfo if valid, null otherwise
     */
    fun parse(url: String): SshConnectionInfo? {
        val match = SSH_URL_REGEX.matchEntire(url) ?: return null

        val user = match.groupValues[1].takeIf { it.isNotEmpty() }
        val host = match.groupValues[2]
        val port = match.groupValues[3].takeIf { it.isNotEmpty() }?.toIntOrNull() ?: 22
        val path = match.groupValues[4].takeIf { it.isNotEmpty() }

        // Host is required
        if (host.isEmpty()) return null

        return SshConnectionInfo(
            user = user,
            host = host,
            port = port,
            path = path
        )
    }

    /**
     * Check if a URL is an SSH URL.
     */
    fun isSshUrl(url: String): Boolean = url.startsWith("ssh://")
}
