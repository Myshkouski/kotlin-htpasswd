pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "htpasswd"
include("htpasswd")
include("htpasswd-micronaut")
include("htpasswd-ktor")
