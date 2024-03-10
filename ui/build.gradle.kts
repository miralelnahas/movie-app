plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
}

apply(from = "../common-configs.gradle")
android {
    namespace = "com.trianglz.ui"
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.fragment.ktx)

    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(libs.paging.common.ktx)
    implementation(libs.paging.runtime.ktx)

    implementation(libs.picasso)

    implementation(libs.legacy.support.v4)
    implementation(libs.lifecycle.extensions)
    implementation(libs.lifecycle.viewmodel.ktx)

    implementation(libs.core.splashscreen)

    implementation(libs.shimmer)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.test)
    androidTestImplementation(libs.espresso.core)
}

kapt {
    correctErrorTypes = true
}