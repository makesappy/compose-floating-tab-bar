import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.vanniktech.maven.publish)
    alias(libs.plugins.gradleup.nmcp)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
        publishLibraryVariants("release")
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "FloatingTabBar"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Use api for iOS framework export
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material3)
            api(compose.ui)
            api(compose.animation)
            api(libs.constraintlayout.compose.multiplatform)
        }

        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
        }
    }
}

android {
    namespace = "io.github.elyesmansour.floatingTabBar"
    compileSdk = 36

    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}

mavenPublishing {
    publishToMavenCentral()
    
    // Only sign publications when publishing to Maven Central
    // Skip signing for local development (publishToMavenLocal)
    if (project.hasProperty("mavenCentralUsername")) {
        signAllPublications()
    }

    coordinates("io.github.elyesmansour", "floatingTabBar", "2.0.0")

    pom {
        name = "FloatingTabBar"
        description = "A Compose Multiplatform floating tab bar that mimics the iOS 26 Liquid Glass tab bar behavior"
        url = "https://github.com/elyesmansour/compose-floating-tab-bar"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "http://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                name = "Elyes Mansour"
                email = "27696255+elyesmansour@users.noreply.github.com"
                url = "https://github.com/elyesmansour"
            }
        }
        scm {
            connection = "scm:git:git://github.com/elyesmansour/compose-floating-tab-bar.git"
            developerConnection = "scm:git:ssh://github.com/elyesmansour/compose-floating-tab-bar.git"
            url = "https://github.com/elyesmansour/compose-floating-tab-bar"
        }
    }
}
