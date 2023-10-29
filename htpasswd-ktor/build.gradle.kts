plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {
        withSourcesJar()
        jvmToolchain(8)
        withJava()
        testRuns.named("test") {
            executionTask.configure {
                useJUnitPlatform()
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":htpasswd"))
                compileOnly("io.ktor:ktor-server-auth:2.+")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
