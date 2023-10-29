package dev.myshkouski.htpasswd

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@JsExport
interface HtpasswdParser {
    /**
     * Parse
     *
     * @param lines sequence of lines from htpasswd file
     * @return map of username to [PasswordVerifier] pairs
     */
    fun parse(lines: Array<String>): Array<VerifiableCredentials>
}
