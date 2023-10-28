package dev.myshkouski.htpasswd

actual class BcryptHashVerifier actual constructor(
    private val hash: String
) : PasswordVerifier {
    override fun verify(password: String): Boolean {
        TODO("not implemented")
    }
}
