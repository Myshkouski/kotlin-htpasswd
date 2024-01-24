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
    js {
        nodejs()
        useEsModules()
        generateTypeScriptDefinitions()
        binaries.executable()
//        compilations.all {
//            packageJson {
//                // ./gradlew --no-daemon --console=plain -Pscope=orgName clean jsNpmPackage
//                val scope: String? by project.properties.toMap().withDefault { null }
//                if (!scope.isNullOrEmpty()) {
//                    name = "@$scope/" + npmProject.name
//                }
//            }
//        }
    }
//    val hostOs = System.getProperty("os.name")
//    val isArm64 = System.getProperty("os.arch") == "aarch64"
//    val isMingwX64 = hostOs.startsWith("Windows")
//    val nativeTarget = when {
//        hostOs == "Mac OS X" && isArm64 -> macosArm64("native")
//        hostOs == "Mac OS X" && !isArm64 -> macosX64("native")
//        hostOs == "Linux" && isArm64 -> linuxArm64("native")
//        hostOs == "Linux" && !isArm64 -> linuxX64("native")
//        isMingwX64 -> mingwX64("native")
//        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
//    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.bcrypt)
            }
        }
        val jvmTest by getting
        val jsMain by getting {
            dependencies {
                implementation(libs.kotlin.wrappers)
            }
        }
        val jsTest by getting
//        val nativeMain by getting
//        val nativeTest by getting
    }
}
