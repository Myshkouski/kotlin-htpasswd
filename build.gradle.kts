plugins {
    kotlin("multiplatform") version "1.9.10" apply false
    kotlin("jvm") version "1.9.10" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }

    group = "dev.myshkouski.${rootProject.name}"
    version = "0.1.1-SNAPSHOT"
}

subprojects {
    apply<MavenPublishPlugin>()
}
