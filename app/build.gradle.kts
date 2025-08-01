import org.jetbrains.kotlin.config.JvmTarget

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.plugin.compose") version ("2.0.0")
}

android {
    namespace = "com.example.androidtemplateapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.androidtemplateapp"
        minSdk = 24
        targetSdk = 36
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

    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.material3.navigation3)

    // di
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

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
    implementation(libs.androidx.activity.compose)
    implementation(libs.accompanist)
    implementation(libs.converter.gson)
    implementation(libs.retrofit)
    implementation(libs.okhttp.interceptor)
    implementation(libs.arrow.core)
}
