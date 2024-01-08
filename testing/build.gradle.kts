@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("weatherapp.android.library")
    id("weatherapp.android.hilt")
    id("weatherapp.android.library.compose")
}

android {
    namespace = "com.danielwaiguru.weatherapp.testing"
}

dependencies {
    api(libs.junit4)
    api(libs.junit4.ext)
    api(libs.turbine)
    api(libs.mock.android)
    api(libs.mock.agent)
    api(libs.google.truth)
    api(libs.coroutines.test)
    api(libs.espresso.core)
    api(libs.androidx.arch.core.testing)
    implementation(project(":domain"))
    implementation(project(":data"))
}