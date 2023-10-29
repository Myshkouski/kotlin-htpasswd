package dev.myshkouski.htpasswd.ktor

import dev.myshkouski.htpasswd.HtpasswdAuthenticator
import io.ktor.server.auth.*

fun HtpasswdAuthenticator.validate(credentials: UserPasswordCredential): Principal? {
    val username = credentials.name
    val authenticated = authenticate(username, credentials.password)
    return if (authenticated) UserIdPrincipal(username) else null
}
