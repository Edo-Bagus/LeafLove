import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.leaflove"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.leaflove"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        //load the values from .properties file
        val keystoreFile = project.rootProject.file("apikey.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        //return empty key in case something goes wrong
        val weatherApiKey = properties.getProperty("WEATHER_API_KEY") ?: ""
        buildConfigField(
            type = "String",
            name = "WEATHER_API_KEY",
            value = weatherApiKey
        )

        val plantApiKey = properties.getProperty("PLANT_API_KEY") ?: ""
        buildConfigField(
            type = "String",
            name = "PLANT_API_KEY",
            value = plantApiKey
        )

        val cloudName = properties.getProperty("CLOUD_NAME") ?: ""
        buildConfigField(
            type = "String",
            name = "CLOUD_NAME",
            value = cloudName
        )

        val cloudApiKey = properties.getProperty("CLOUDINARY_API_KEY") ?: ""
        buildConfigField(
            type = "String",
            name = "CLOUDINARY_API_KEY",
            value = cloudApiKey
        )

        val cloudApiSecret = properties.getProperty("CLOUDINARY_API_SECRET") ?: ""
        buildConfigField(
            type = "String",
            name = "CLOUDINARY_API_SECRET",
            value = cloudApiSecret
        )
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.firebase.firestore.ktx)
    ksp(libs.androidx.room.compiler)

// Koin Core
    implementation(libs.koin.core)

    // Koin for Android
    implementation(libs.koin.android)

    // Koin for Jetpack Compose
    implementation(libs.koin.androidx.compose)

    implementation("com.cloudinary:cloudinary-android:3.0.2")


    // Optional: Koin for Navigation (if you're using Jetpack Navigation)
    implementation(libs.koin.androidx.navigation)

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.21")

    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")


    implementation("androidx.navigation:navigation-compose:2.8.2")

    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")

    //Link to Image
    implementation("io.coil-kt:coil-compose:2.4.0")

    //String to json
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("androidx.camera:camera-core:1.1.0")
    implementation("androidx.camera:camera-camera2:1.1.0")
    implementation("androidx.camera:camera-lifecycle:1.1.0")
    implementation("androidx.camera:camera-view:1.1.0")

    implementation("com.google.accompanist:accompanist-permissions:0.36.0")

    implementation("io.github.sceneview:arsceneview:2.2.1")
    implementation(libs.androidx.core.ktx)
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("androidx.compose.foundation:foundation:1.2.0")
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.play.services.location)
    implementation(libs.play.services.maps)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}