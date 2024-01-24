plugins {
    kotlin("jvm")
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    api(projects.htpasswd)
    compileOnly(libs.micronaut.security)
}

publishing {
    publications {
        create<MavenPublication>("Jvm") {
            from(components["java"])
        }
    }
}
