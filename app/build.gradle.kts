plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.example.gotoit"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.gotoit"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            buildConfigField("String", "TEST_KEY", "\"test_value\"")
        }
        release {
            buildConfigField("String", "TEST_KEY", "\"test_value\"")
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
    kotlinOptions {
        jvmTarget = "11"
    }


    buildFeatures {
        compose = true
        buildConfig = true
    }

    secrets {
        propertiesFileName = "secrets.properties"
        defaultPropertiesFileName = "local.defaults.properties"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.support.annotations)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Retrofit

    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)

    //Okhttp

    implementation(libs.okhttp)

    //Coil

    implementation(libs.coil.compose)

    //ViewModel

    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Coroutines

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Navigation

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material)

    // Accompanist

    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.accompanist.swiperefresh.v0360)

    // Room

    implementation(libs.androidx.room.runtime)

    // Maps

    implementation(libs.maps.mobile)

    implementation(libs.secrets.gradle.plugin)
}