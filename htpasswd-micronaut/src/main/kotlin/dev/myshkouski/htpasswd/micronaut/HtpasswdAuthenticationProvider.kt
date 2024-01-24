package dev.myshkouski.htpasswd.micronaut

import dev.myshkouski.htpasswd.HtpasswdAuthenticator
import dev.myshkouski.htpasswd.HtpasswdParser
import io.micronaut.core.io.Readable
import io.micronaut.security.authentication.AuthenticationFailureReason
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.provider.AuthenticationProvider
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import java.io.InputStream
import java.util.*

class HtpasswdAuthenticationProvider<T, I, S>(
    private val authenticator: HtpasswdAuthenticator,
    private val response: AuthenticationResponseBuilder = defaultResponse,
) : AuthenticationProvider<T, I, S> {
    constructor(htpasswdParser: HtpasswdParser, htpasswdLines: Array<String>, response: AuthenticationResponseBuilder = defaultResponse): this(HtpasswdAuthenticator(htpasswdParser, htpasswdLines), response)
    constructor(htpasswdParser: HtpasswdParser, input: InputStream, response: AuthenticationResponseBuilder = defaultResponse): this(htpasswdParser, input.readHtpasswdLines(), response)
    constructor(htpasswdParser: HtpasswdParser, readable: Readable, response: AuthenticationResponseBuilder = defaultResponse): this(htpasswdParser, readable.asInputStream(), response)

    override fun authenticate(requestContext: T, authRequest: AuthenticationRequest<I, S>): AuthenticationResponse {
        val username = authRequest.identity as String
        val verified = authenticator.authenticate(username, authRequest.secret as String?)

        if (!verified) {
            throw AuthenticationResponse.exception(AuthenticationFailureReason.UNKNOWN)
        }

        return response(username)
    }
}

private typealias AuthenticationResponseBuilder = (username: String) -> AuthenticationResponse
private fun InputStream.readHtpasswdLines() = Scanner(this).asSequence().toList().toTypedArray()
private val defaultResponse: AuthenticationResponseBuilder = { username -> AuthenticationResponse.success(username) }
