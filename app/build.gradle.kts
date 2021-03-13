import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.protobuf")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "br.com.lucascordeiro.klever"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled(false)
            proguardFiles = mutableListOf(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.ext["compose_version"].toString()
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.3.0-beta01")
    implementation("com.google.android.material:material:1.3.0")


    //Compose
    implementation("androidx.compose.ui:ui:${rootProject.ext["compose_version"]}")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:${rootProject.ext["compose_version"]}")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation("androidx.compose.foundation:foundation:${rootProject.ext["compose_version"]}")
    // ConstraintLayout
    implementation("androidx.constraintlayout:constraintlayout-compose:${rootProject.ext["constraintlayout_compose_version"]}")
    // Material Design
    implementation("androidx.compose.material:material:${rootProject.ext["compose_version"]}")
    // Material design icons
    implementation("androidx.compose.material:material-icons-core:${rootProject.ext["compose_version"]}")
    implementation("androidx.compose.material:material-icons-extended:${rootProject.ext["compose_version"]}")
    // Integration with activities
    implementation("androidx.activity:activity-compose:1.3.0-alpha04")
    // Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha02")

    //ComposeUtils
    implementation("dev.chrisbanes.accompanist:accompanist-coil:${rootProject.ext["accompanist_coil_version"]}")
    implementation("dev.chrisbanes.accompanist:accompanist-insets:${rootProject.ext["accompanist_coil_version"]}")

    //Navigation
    implementation("androidx.navigation:navigation-compose:${rootProject.ext["navigation_version"]}")

    //Injection
    implementation("org.koin:koin-core:${rootProject.ext["koin_version"]}")
    implementation("org.koin:koin-android-scope:${rootProject.ext["koin_version"]}")
    implementation("org.koin:koin-android-viewmodel:${rootProject.ext["koin_version"]}")
    implementation("org.koin:koin-androidx-compose:${rootProject.ext["koin_version"]}")

    //Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${rootProject.ext["kotlin_coroutine_version"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${rootProject.ext["kotlin_coroutine_version"]}")

    //Startup
    implementation("androidx.startup:startup-runtime:${rootProject.ext["startup_version"]}")

    //Timber
    implementation("com.jakewharton.timber:timber:${rootProject.ext["timber_version"]}")

    //GRPC
    api("com.google.protobuf:protobuf-javalite:${rootProject.ext["protobuf_version"]}")
    api("io.grpc:grpc-kotlin-stub-lite:${rootProject.ext["grpc_kotlin_version"]}")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    runtimeOnly("io.grpc:grpc-okhttp:${rootProject.ext["grpc_version"]}")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.0")

    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}


java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}


protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${rootProject.ext["protobuf_version"]}"
    }
    plugins {
        id("java") {
            artifact = "io.grpc:protoc-gen-grpc-java:${rootProject.ext["grpc_version"]}"
        }
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${rootProject.ext["grpc_version"]}"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:${rootProject.ext["grpc_kotlin_version"]}:jdk7@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("java") {
                    option("lite")
                }
                id("grpc") {
                    option("lite")
                }
                id("grpckt") {
                    option("lite")
                }
            }
        }
    }
}