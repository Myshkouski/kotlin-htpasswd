package dev.myshkouski.htpasswd

import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test

class HtpasswdParserTests {
    private fun parse(fileContent: String) {
        val parser = HtpasswdParser(allowPlainPasswords = true)
        // val fileContent = this::class.java.getResource("/1.htpasswd").readText()
        val credentials = parser.parse(fileContent.lines().toTypedArray())
        credentials.forEach {
            assert(it.username.isNotBlank())
        }
    }

    @Test
    fun parse() {
        for (i in 1..1) {
            val filename = "/$i.htpasswd"
            val fileContent = this::class.java.getResource(filename).readText()
            assertDoesNotThrow {
                parse(fileContent)
            }
        }
    }
}
