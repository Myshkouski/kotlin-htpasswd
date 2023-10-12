package dev.myshkouski.htpasswd

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@JsExport
interface PasswordVerifier {
    fun verify(password: String): Boolean
}
