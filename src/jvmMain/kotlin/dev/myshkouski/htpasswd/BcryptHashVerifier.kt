package dev.myshkouski.htpasswd

import at.favre.lib.crypto.bcrypt.BCrypt

actual class BcryptHashVerifier actual constructor(
    private val hash: String
) : PasswordVerifier {
    override fun verify(password: String): Boolean {
        return BCrypt.verifyer().verify(password.toCharArray(), hash)?.verified ?: false
    }
}
