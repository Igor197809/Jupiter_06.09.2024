plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.jupiter_06092024"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.jupiter_06092024"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,DEPENDENCIES}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")

    // Jetpack Compose UI
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0")
    implementation("androidx.compose.material:material:1.5.0")

    // Navigation в Compose
    implementation("androidx.navigation:navigation-compose:2.7.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    // WorkManager для фоновых задач
    implementation("androidx.work:work-runtime-ktx:2.8.1")

    // Зависимости для работы с Google Sheets API и OAuth2
    implementation("com.google.api-client:google-api-client-android:1.34.1")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.34.1")
    implementation("com.google.apis:google-api-services-sheets:v4-rev640-1.25.0")

    // Google HTTP Client для Android и Jackson
    implementation("com.google.http-client:google-http-client-android:1.42.3")
    implementation("com.google.api-client:google-api-client-jackson2:1.34.1")

    // Для использования LiveData в Jetpack Compose
    implementation("androidx.compose.runtime:runtime-livedata:1.5.0")

    // Google OAuth2 HTTP (HttpCredentialsAdapter)
    implementation("com.google.auth:google-auth-library-oauth2-http:1.11.0")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.0")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.0")
}
