package dev.myshkouski.htpasswd

internal class PlainPasswordVerifier(
    private val plainPassword: String
) : PasswordVerifier {
    override fun verify(password: String): Boolean {
        return password == plainPassword
    }
}
