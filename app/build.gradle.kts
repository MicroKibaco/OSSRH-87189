plugins {
    alias(libs.plugins.android.application)
}


android {
    namespace = "com.github.microkibaco.ultimate.unidramer"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.github.microkibaco.ultimate.unidramer"
        minSdk = 28
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
}

dependencies {
    implementation (libs.flycotablayout)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation (libs.philjay.mpandroidchart)
    implementation (libs.flycotablayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}