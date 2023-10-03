plugins {
    id("com.android.application")
}

android {
    namespace = "vn.edu.usth.stockdashboard"
    compileSdk = 33

    defaultConfig {
        applicationId = "vn.edu.usth.stockdashboard"
        minSdk = 24
        targetSdk = 33
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
    // https://mvnrepository.com/artifact/com.github.PhilJay/MPAndroidChart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    // https://mvnrepository.com/artifact/org.java-websocket/Java-WebSocket
    implementation("org.java-websocket:Java-WebSocket:1.5.4")
    // https://mvnrepository.com/artifact/org.json/json
    implementation("org.json:json:20230227")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}