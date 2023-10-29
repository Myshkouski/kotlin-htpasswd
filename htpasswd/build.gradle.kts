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
                implementation("at.favre.lib:bcrypt:0.10.2")
            }
        }
        val jvmTest by getting
        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin-wrappers:kotlin-js:1.0.0-pre.632")
            }
        }
        val jsTest by getting
//        val nativeMain by getting
//        val nativeTest by getting
    }
}
