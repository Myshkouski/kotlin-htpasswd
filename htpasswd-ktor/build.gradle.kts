plugins {
    kotlin("multiplatform")
}

kotlin {
    jvmToolchain(17)
    jvm {
        withSourcesJar()
        withJava()
        testRuns.named("test") {
            executionTask.configure {
                useJUnitPlatform()
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.htpasswd)
            compileOnly(libs.ktor.server.auth)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}
