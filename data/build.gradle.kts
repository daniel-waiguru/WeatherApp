@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("weatherapp.android.library")
    alias(libs.plugins.weatherapp.android.library.jacoco)
    id("weatherapp.android.hilt")
    id("weatherapp.android.room")
}

android {
    namespace = "com.danielwaiguru.weatherapp.data"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":domain"))
    // Networking
    implementation(libs.bundles.network)
    implementation(libs.play.services.location)
    // Chucker
    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker)

    testImplementation(project(":testing"))
    testImplementation(libs.robolectric)
}
