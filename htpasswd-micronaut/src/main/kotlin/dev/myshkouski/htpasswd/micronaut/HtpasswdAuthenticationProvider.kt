package dev.myshkouski.htpasswd.micronaut

import dev.myshkouski.htpasswd.HtpasswdAuthenticator
import dev.myshkouski.htpasswd.HtpasswdParser
import io.micronaut.core.io.Readable
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import java.io.InputStream
import java.util.*

class HtpasswdAuthenticationProvider<T>(
    private val authenticator: HtpasswdAuthenticator,
    private val response: AuthenticationResponseBuilder = defaultResponse,
) : AuthenticationProvider<T> {
    constructor(htpasswdParser: HtpasswdParser, htpasswdLines: Array<String>, response: AuthenticationResponseBuilder = defaultResponse): this(HtpasswdAuthenticator(htpasswdParser, htpasswdLines), response)
    constructor(htpasswdParser: HtpasswdParser, input: InputStream, response: AuthenticationResponseBuilder = defaultResponse): this(htpasswdParser, input.readHtpasswdLines(), response)
    constructor(htpasswdParser: HtpasswdParser, readable: Readable, response: AuthenticationResponseBuilder = defaultResponse): this(htpasswdParser, readable.asInputStream(), response)

    private fun AuthenticationRequest<*, *>.emitter() = { emitter: FluxSink<AuthenticationResponse> ->
        val username = identity as String
        val verified = authenticator.authenticate(username, secret as String?)

        if (verified) {
            emitter.next(response(username))
            emitter.complete()
        } else {
            emitter.error(AuthenticationResponse.exception())
        }
    }

    override fun authenticate(
        httpRequest: T?,
        authenticationRequest: AuthenticationRequest<*, *>?
    ): Publisher<AuthenticationResponse> {
        return Flux.create(authenticationRequest!!.emitter(), FluxSink.OverflowStrategy.ERROR)
    }
}

private typealias AuthenticationResponseBuilder = (username: String) -> AuthenticationResponse
private fun InputStream.readHtpasswdLines() = Scanner(this).asSequence().toList().toTypedArray()
private val defaultResponse: AuthenticationResponseBuilder = { username -> AuthenticationResponse.success(username) }
