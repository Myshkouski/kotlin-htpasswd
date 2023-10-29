plugins {
    kotlin("jvm")
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    api(project(":htpasswd"))
    compileOnly("io.micronaut.security:micronaut-security:4.+")
    compileOnly("io.projectreactor:reactor-core:3.+")
}

publishing {
    publications {
        create<MavenPublication>("Jvm") {
            from(components["java"])
        }
    }
}
