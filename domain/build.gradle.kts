@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("weatherapp.jvm.library")
}

dependencies {
    implementation(libs.coroutines.core)
    implementation(libs.javax)
    implementation(libs.retrofit.moshi.converter)
}
