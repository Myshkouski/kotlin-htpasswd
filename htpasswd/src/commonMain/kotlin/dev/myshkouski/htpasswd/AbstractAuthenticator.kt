package dev.myshkouski.htpasswd

abstract class AbstractAuthenticator(
    htpasswdParser: HtpasswdParser,
    htpasswdLines: Array<String>,
) {
    private val htpasswd = htpasswdParser.parse(htpasswdLines).associate { it.username to it.verifier }

    protected fun authenticate(username: String, password: String?): Boolean {
        password ?: return false
        return htpasswd[username]?.verify(password) ?: false
    }
}
