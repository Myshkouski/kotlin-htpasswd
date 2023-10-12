package dev.myshkouski.htpasswd

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport
import kotlin.js.JsName

internal class DefaultHtpasswdParser(
    private val allowPlainPasswords: Boolean
) : HtpasswdParser, HtpasswdLineParser {
    override fun parse(lines: Array<String>): Array<VerifiableCredentials> {
        return lines.mapIndexed { index, line ->
            try {
                parseLine(line)
            } catch (e: IllegalStateException) {
                throw IllegalStateException("cannot parse line $index", e)
            }
        }.toTypedArray()
    }

    override fun parseLine(line: String): VerifiableCredentials {
        val (username, passwordOrHash) = htpasswdRegex.find(line)?.destructured ?: throw IllegalArgumentException("Invalid line format")
        val verifier = when {
            passwordOrHash.startsWith(BCRYPT_FORMAT) -> BcryptHashVerifier(passwordOrHash)
            allowPlainPasswords -> PlainPasswordVerifier(passwordOrHash)
            else -> throwUnknownFormatException()
        }
        return DefaultVerifiableCredentials(username, verifier)
    }

    private fun throwUnknownFormatException(): Nothing {
        val message = "Cannot recognize hash format." + (" Note that plain passwords not allowed.".takeUnless { allowPlainPasswords } ?: "")
        throw IllegalArgumentException(message)
    }

    companion object {
        private val htpasswdRegex = Regex("^([^:]+):(.+)")
        private const val BCRYPT_FORMAT = "$2y$"
    }
}

@OptIn(ExperimentalJsExport::class)
@JsExport
@JsName("createHtpasswdParser")
fun HtpasswdParser(allowPlainPasswords: Boolean): HtpasswdParser = DefaultHtpasswdParser(allowPlainPasswords)
