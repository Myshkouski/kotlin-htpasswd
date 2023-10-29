package dev.myshkouski.htpasswd

interface HtpasswdLineParser {
    fun parseLine(line: String): VerifiableCredentials?
}
