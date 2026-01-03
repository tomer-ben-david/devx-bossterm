package ai.rever.bossterm.compose.hyperlinks

import kotlin.test.*

/**
 * Unit tests for SshUrlParser.
 * Tests URL parsing, command generation, and edge cases.
 */
class SshUrlParserTest {

    // ======================== Valid URL Parsing Tests ========================

    @Test
    fun testParseSimpleHost() {
        val result = SshUrlParser.parse("ssh://example.com")
        assertNotNull(result)
        assertNull(result.user)
        assertEquals("example.com", result.host)
        assertEquals(22, result.port)
        assertNull(result.path)
    }

    @Test
    fun testParseHostWithPort() {
        val result = SshUrlParser.parse("ssh://example.com:2222")
        assertNotNull(result)
        assertNull(result.user)
        assertEquals("example.com", result.host)
        assertEquals(2222, result.port)
        assertNull(result.path)
    }

    @Test
    fun testParseUserAndHost() {
        val result = SshUrlParser.parse("ssh://user@example.com")
        assertNotNull(result)
        assertEquals("user", result.user)
        assertEquals("example.com", result.host)
        assertEquals(22, result.port)
        assertNull(result.path)
    }

    @Test
    fun testParseUserHostAndPort() {
        val result = SshUrlParser.parse("ssh://user@example.com:22")
        assertNotNull(result)
        assertEquals("user", result.user)
        assertEquals("example.com", result.host)
        assertEquals(22, result.port)
        assertNull(result.path)
    }

    @Test
    fun testParseUserHostPortAndPath() {
        val result = SshUrlParser.parse("ssh://user@example.com:22/path/to/dir")
        assertNotNull(result)
        assertEquals("user", result.user)
        assertEquals("example.com", result.host)
        assertEquals(22, result.port)
        assertEquals("/path/to/dir", result.path)
    }

    @Test
    fun testParseHostWithPath() {
        val result = SshUrlParser.parse("ssh://example.com/home/user")
        assertNotNull(result)
        assertNull(result.user)
        assertEquals("example.com", result.host)
        assertEquals(22, result.port)
        assertEquals("/home/user", result.path)
    }

    @Test
    fun testParseIPv4Address() {
        val result = SshUrlParser.parse("ssh://192.168.1.100")
        assertNotNull(result)
        assertNull(result.user)
        assertEquals("192.168.1.100", result.host)
        assertEquals(22, result.port)
    }

    @Test
    fun testParseIPv4AddressWithPort() {
        val result = SshUrlParser.parse("ssh://root@192.168.1.100:2222")
        assertNotNull(result)
        assertEquals("root", result.user)
        assertEquals("192.168.1.100", result.host)
        assertEquals(2222, result.port)
    }

    @Test
    fun testParseLocalhost() {
        val result = SshUrlParser.parse("ssh://localhost")
        assertNotNull(result)
        assertEquals("localhost", result.host)
        assertEquals(22, result.port)
    }

    @Test
    fun testParseSubdomain() {
        val result = SshUrlParser.parse("ssh://git@github.com")
        assertNotNull(result)
        assertEquals("git", result.user)
        assertEquals("github.com", result.host)
    }

    @Test
    fun testParseComplexUsername() {
        val result = SshUrlParser.parse("ssh://user.name@example.com")
        assertNotNull(result)
        assertEquals("user.name", result.user)
        assertEquals("example.com", result.host)
    }

    // ======================== Command Generation Tests ========================

    @Test
    fun testToCommandSimpleHost() {
        val info = SshConnectionInfo(user = null, host = "example.com")
        assertEquals("ssh example.com", info.toCommand())
    }

    @Test
    fun testToCommandWithUser() {
        val info = SshConnectionInfo(user = "admin", host = "example.com")
        assertEquals("ssh admin@example.com", info.toCommand())
    }

    @Test
    fun testToCommandWithNonDefaultPort() {
        val info = SshConnectionInfo(user = null, host = "example.com", port = 2222)
        assertEquals("ssh example.com -p 2222", info.toCommand())
    }

    @Test
    fun testToCommandWithUserAndPort() {
        val info = SshConnectionInfo(user = "root", host = "server.local", port = 8022)
        assertEquals("ssh root@server.local -p 8022", info.toCommand())
    }

    @Test
    fun testToCommandDefaultPort() {
        val info = SshConnectionInfo(user = "user", host = "example.com", port = 22)
        // Default port 22 should NOT be included in command
        assertEquals("ssh user@example.com", info.toCommand())
    }

    @Test
    fun testToCommandPathIgnored() {
        // Path is stored but not included in SSH command
        val info = SshConnectionInfo(user = "user", host = "example.com", path = "/some/path")
        assertEquals("ssh user@example.com", info.toCommand())
    }

    // ======================== Invalid URL Tests ========================

    @Test
    fun testParseInvalidProtocol() {
        val result = SshUrlParser.parse("http://example.com")
        assertNull(result)
    }

    @Test
    fun testParseEmptyString() {
        val result = SshUrlParser.parse("")
        assertNull(result)
    }

    @Test
    fun testParseMissingHost() {
        val result = SshUrlParser.parse("ssh://")
        assertNull(result)
    }

    @Test
    fun testParseInvalidPort() {
        // Port must be numeric - this should still parse but with default port
        val result = SshUrlParser.parse("ssh://example.com:abc")
        assertNull(result)  // Invalid format
    }

    @Test
    fun testParseMalformedUrl() {
        val result = SshUrlParser.parse("ssh:/example.com")
        assertNull(result)
    }

    @Test
    fun testParseNoProtocol() {
        val result = SshUrlParser.parse("example.com")
        assertNull(result)
    }

    @Test
    fun testParseJustSsh() {
        val result = SshUrlParser.parse("ssh://")
        assertNull(result)
    }

    // ======================== isSshUrl Tests ========================

    @Test
    fun testIsSshUrlValid() {
        assertTrue(SshUrlParser.isSshUrl("ssh://example.com"))
        assertTrue(SshUrlParser.isSshUrl("ssh://user@host:22"))
        assertTrue(SshUrlParser.isSshUrl("ssh://localhost"))
    }

    @Test
    fun testIsSshUrlInvalid() {
        assertFalse(SshUrlParser.isSshUrl("http://example.com"))
        assertFalse(SshUrlParser.isSshUrl("https://example.com"))
        assertFalse(SshUrlParser.isSshUrl("ftp://example.com"))
        assertFalse(SshUrlParser.isSshUrl("example.com"))
        assertFalse(SshUrlParser.isSshUrl(""))
    }

    @Test
    fun testIsSshUrlCaseSensitive() {
        // ssh:// should be lowercase
        assertFalse(SshUrlParser.isSshUrl("SSH://example.com"))
        assertFalse(SshUrlParser.isSshUrl("Ssh://example.com"))
    }

    // ======================== Edge Cases ========================

    @Test
    fun testParsePortZero() {
        val result = SshUrlParser.parse("ssh://example.com:0")
        assertNotNull(result)
        assertEquals(0, result.port)
    }

    @Test
    fun testParseHighPort() {
        val result = SshUrlParser.parse("ssh://example.com:65535")
        assertNotNull(result)
        assertEquals(65535, result.port)
    }

    @Test
    fun testParseHostWithHyphen() {
        val result = SshUrlParser.parse("ssh://my-server.example.com")
        assertNotNull(result)
        assertEquals("my-server.example.com", result.host)
    }

    @Test
    fun testParseHostWithUnderscore() {
        val result = SshUrlParser.parse("ssh://my_server.local")
        assertNotNull(result)
        assertEquals("my_server.local", result.host)
    }

    @Test
    fun testParseUserWithNumbers() {
        val result = SshUrlParser.parse("ssh://user123@example.com")
        assertNotNull(result)
        assertEquals("user123", result.user)
    }

    @Test
    fun testParseRootPath() {
        val result = SshUrlParser.parse("ssh://example.com/")
        assertNotNull(result)
        assertEquals("/", result.path)
    }

    // ======================== Real-World Examples ========================

    @Test
    fun testParseGitHubSsh() {
        val result = SshUrlParser.parse("ssh://git@github.com:22/user/repo")
        assertNotNull(result)
        assertEquals("git", result.user)
        assertEquals("github.com", result.host)
        assertEquals(22, result.port)
        assertEquals("/user/repo", result.path)
    }

    @Test
    fun testParseAwsEc2() {
        val result = SshUrlParser.parse("ssh://ec2-user@ec2-12-34-56-78.compute-1.amazonaws.com")
        assertNotNull(result)
        assertEquals("ec2-user", result.user)
        assertEquals("ec2-12-34-56-78.compute-1.amazonaws.com", result.host)
    }

    @Test
    fun testParseRaspberryPi() {
        val result = SshUrlParser.parse("ssh://pi@raspberrypi.local")
        assertNotNull(result)
        assertEquals("pi", result.user)
        assertEquals("raspberrypi.local", result.host)
    }
}
