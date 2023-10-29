plugins {
    kotlin("jvm")
}

kotlin {
    jvmToolchain(8)
}

dependencies {
    implementation(project(":htpasswd"))
    compileOnly("io.micronaut.security:micronaut-security:3.+")
    compileOnly("io.projectreactor:reactor-core:3.+")
}
