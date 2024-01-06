@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("weatherapp.android.library")
    id("weatherapp.android.hilt")
}

android {
    namespace = "com.danielwaiguru.shoppy.data"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":domain"))
    // Networking
    implementation(libs.bundles.network)
    // Chucker
    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker)

}