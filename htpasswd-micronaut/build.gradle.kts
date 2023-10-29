plugins {
    kotlin("jvm")
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":htpasswd"))
    compileOnly("io.micronaut.security:micronaut-security:4.+")
    compileOnly("io.projectreactor:reactor-core:3.+")
}
