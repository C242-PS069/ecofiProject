plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.dicoding.ecofiproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dicoding.ecofiproject"
        minSdk = 24
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding {
        enable = true
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation (libs.androidx.core.ktx.v1120)
    implementation (libs.androidx.appcompat.v161)
    implementation (libs.material.v190)
    implementation (libs.androidx.activity.ktx)
    implementation (libs.androidx.constraintlayout.v214)
    implementation (libs.androidx.fragment.ktx)
    implementation ("me.relex:circleindicator:2.1.6")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.preference.ktx)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.logging.interceptor)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation(libs.gson)
    implementation (libs.androidx.datastore.preferences)
    implementation (libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.datastore.preferences.core.jvm)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.view)
    implementation (libs.glide)
    annotationProcessor (libs.glide.compiler)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.cardview)
    implementation (libs.material.v180)
    testImplementation (libs.junit)
    androidTestImplementation (libs.androidx.junit.v115)
    androidTestImplementation (libs.androidx.espresso.core.v351)
}
