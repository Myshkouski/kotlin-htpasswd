plugins {
    kotlin("multiplatform") version "1.9.10" apply false
    kotlin("jvm") version "1.9.10" apply false
    `maven-publish`
}

allprojects {
    repositories {
        mavenCentral()
    }

    group = "dev.myshkouski"
    version = "0.1.1-SNAPSHOT"
}
