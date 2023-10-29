package dev.myshkouski.htpasswd

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@JsExport
interface VerifiableCredentials {
    val username: String
    val verifier: PasswordVerifier
}

operator fun VerifiableCredentials.component1() = username
operator fun VerifiableCredentials.component2() = verifier

internal data class DefaultVerifiableCredentials(
    override val username: String,
    override val verifier: PasswordVerifier
) : VerifiableCredentials
