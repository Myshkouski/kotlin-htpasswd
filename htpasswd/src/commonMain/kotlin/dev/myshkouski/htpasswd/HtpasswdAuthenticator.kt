package dev.myshkouski.htpasswd

class HtpasswdAuthenticator(
    htpasswdParser: HtpasswdParser,
    htpasswdLines: Array<String>,
) {
    private val htpasswd = htpasswdParser.parse(htpasswdLines).associate { it.username to it.verifier }

    fun authenticate(username: String, password: String?): Boolean {
        password ?: return false
        return htpasswd[username]?.verify(password) ?: false
    }
}
