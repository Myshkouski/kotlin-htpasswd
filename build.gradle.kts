plugins {
    val kotlinVersion = libs.versions.kotlin.get()
    kotlin("multiplatform") version kotlinVersion apply false
    kotlin("jvm") version kotlinVersion apply false
}

allprojects {
    repositories {
        mavenCentral()
    }

    group = "dev.myshkouski.${rootProject.name}"
    version = "0.1.2-SNAPSHOT"
}

subprojects {
    apply<MavenPublishPlugin>()
}
