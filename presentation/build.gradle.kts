@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("weatherapp.android.feature")
    alias(libs.plugins.weatherapp.android.library.jacoco)
    id("weatherapp.android.library.compose")
}

android {
    namespace = "com.danielwaiguru.weatherapp.presentation"
}
dependencies {
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.permissions)
    implementation(project(":designsystem"))
    implementation(libs.lottie.compose)
    testImplementation(project(":testing"))
    androidTestImplementation(project(":testing"))
}
