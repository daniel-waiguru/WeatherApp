@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("weatherapp.android.feature")
    id("weatherapp.android.library.compose")
}

android {
    namespace = "com.danielwaiguru.weatherapp.presentation"
}
dependencies {
    implementation(libs.accompanist.systemuicontroller)
}