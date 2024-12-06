plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.mobdeve.xx22.rivera.josecarlos.animotrack"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mobdeve.xx22.rivera.josecarlos.animotrack"
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

    configurations.all {
        resolutionStrategy {
            force("androidx.core:core:1.13.0")
            force("androidx.appcompat:appcompat:1.6.1")
            force("androidx.constraintlayout:constraintlayout:2.1.4")
        }
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.database)
    implementation(libs.gridlayout)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.inappmessaging.display)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation ("com.google.firebase:firebase-firestore:25.1.1") {
        exclude(group = "com.android.support")
    }
    implementation("com.google.firebase:firebase-auth:23.1.0") {
        exclude(group = "com.android.support")
    }
    implementation ("com.google.firebase:firebase-messaging:24.1.0") {
        exclude(group = "com.android.support")
    }
    implementation ("com.google.firebase:firebase-installations:18.0.0") {
        exclude(group = "com.android.support")
    }

}