package dev.myshkouski.htpasswd.micronaut

import dev.myshkouski.htpasswd.HtpasswdAuthenticator
import dev.myshkouski.htpasswd.HtpasswdParser
import io.micronaut.core.io.Readable
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import java.io.InputStream
import java.util.*

class HtpasswdAuthenticationProvider(
    htpasswdParser: HtpasswdParser,
    htpasswdLines: Array<String>,
) : AuthenticationProvider {
    constructor(htpasswdParser: HtpasswdParser, input: InputStream): this(htpasswdParser, input.readHtpasswdLines())
    constructor(htpasswdParser: HtpasswdParser, readable: Readable): this(htpasswdParser, readable.asInputStream())

    private val authenticator = HtpasswdAuthenticator(htpasswdParser, htpasswdLines)

    private fun AuthenticationRequest<*, *>.emitter() = { emitter: FluxSink<AuthenticationResponse> ->
        val verified = authenticator.authenticate(identity as String, secret as String?)

        if (verified) {
            emitter.next(AuthenticationResponse.success(identity as String))
            emitter.complete()
        } else {
            emitter.error(AuthenticationResponse.exception())
        }
    }

    override fun authenticate(
        httpRequest: HttpRequest<*>?,
        authenticationRequest: AuthenticationRequest<*, *>?
    ): Publisher<AuthenticationResponse> {
        return Flux.create(authenticationRequest!!.emitter(), FluxSink.OverflowStrategy.ERROR)
    }
}

private fun InputStream.readHtpasswdLines() = Scanner(this).asSequence().toList().toTypedArray()
