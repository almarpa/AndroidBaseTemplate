import org.jetbrains.kotlin.config.JvmTarget

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.androidtemplateapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.androidtemplateapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        javaCompileOptions {
            annotationProcessorOptions {
                argument("room.schemaLocation", "${projectDir}/schemas")
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_17)
        targetCompatibility(JavaVersion.VERSION_17)
    }

    kotlinOptions {
        jvmTarget = JvmTarget.JVM_17.description
        freeCompilerArgs = freeCompilerArgs + listOf("-Xcontext-receivers")
    }

    flavorDimensions.add("environment")
    productFlavors {
        create("itg") {
            dimension = "environment"
            applicationIdSuffix = ".itg"
            buildConfigField("String", "BASE_URL", "\"https://pokeapi.co/\"")
        }
        create("local") {
            dimension = "environment"
            applicationIdSuffix = ".local"
            buildConfigField("String", "BASE_URL", "\"http://10.0.2.2/\"")
        }
        create("pre") {
            dimension = "environment"
            applicationIdSuffix = ".pre"
            buildConfigField("String", "BASE_URL", "\"https://pokeapi.co/\"")
        }
        create("pro") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://pokeapi.co/\"")
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }

    packagingOptions.resources.excludes.add("META-INF/{AL2.0,LGPL2.1}")
}

dependencies {

    // compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.compose.constraintlayout)

    // material
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.material3)

    api(libs.androidx.lifecycle)

    // navigation
    api(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation)
    implementation(libs.androidx.hilt.navigation.compose)

    // di
    implementation(libs.hilt.android)
    ksp(libs.dagger.compiler)
    ksp(libs.hilt.android.compiler)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.testing)

    // database
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)
    implementation(libs.datastore)

    // images
    implementation(libs.palette)
    implementation(libs.coil.kt)

    // pagination
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    // unit test
    testImplementation(libs.junit)
    testImplementation(libs.androidx.junit)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)

    // others
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.accompanist)
    implementation(libs.converter.gson)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit)
    implementation(libs.okhttp.interceptor)
}
