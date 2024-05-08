import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin.Companion.isIncrementalKapt

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    kotlin("kapt")
    //Dagger-Hilt
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.templateapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.templateapp"
        minSdk = 26
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

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
        dataBinding = true
    }

    // Specifies one flavor dimension.
    flavorDimensions += "version"
    productFlavors {
        create("dev") {
//            dimension = "version"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
        }
        create("qa") {
            applicationIdSuffix = ".qa"
            versionNameSuffix = "-qa"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:${rootProject.extra["core_ktx_version"]}")
    implementation("androidx.appcompat:appcompat:${rootProject.extra["appcompat_version"]}")
    implementation("com.google.android.material:material:${rootProject.extra["material_version"]}")
    implementation("androidx.constraintlayout:constraintlayout:${rootProject.extra["constraintlayout_version"]}")

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:${rootProject.extra["dagger_hilt"]}")
    kapt("com.google.dagger:hilt-compiler:${rootProject.extra["dagger_hilt"]}")
//    kapt("androidx.hilt:hilt-compiler:${rootProject.extra["hilt_compiler_version"]}")

    //Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${rootProject.extra["lifecycle_version"]}")
//    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${rootProject.extra["lifecycle_version"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.extra["lifecycle_version"]}")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${rootProject.extra["coroutines_version"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${rootProject.extra["coroutines_version"]}")

    //Splash screen
    implementation("androidx.core:core-splashscreen:${rootProject.extra["splash_screen"]}")

    //Paging 3
    implementation("androidx.paging:paging-runtime:${rootProject.extra["paging_version"]}")

    //GSON
    implementation("com.google.code.gson:gson:${rootProject.extra["gson_version"]}")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:${rootProject.extra["retrofit_version"]}")
//    implementation("com.squareup.retrofit2:adapter-rxjava2:${rootProject.extra["retrofit_version"]}")
    implementation("com.squareup.retrofit2:converter-gson:${rootProject.extra["retrofit_version"]}")
    implementation("com.squareup.okhttp3:okhttp:${rootProject.extra["okhttp_version"]}")
    implementation("com.squareup.okhttp3:mockwebserver:${rootProject.extra["okhttp_version"]}")
    implementation("com.squareup.okhttp3:logging-interceptor:${rootProject.extra["okhttp_version"]}")

    //Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:${rootProject.extra["nav_version"]}")
    implementation("androidx.navigation:navigation-ui-ktx:${rootProject.extra["nav_version"]}")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:${rootProject.extra["nav_version"]}")

    //Glide
    implementation("com.github.bumptech.glide:glide:${rootProject.extra["glide_version"]}")

    //For view binding(it will nullify binding object when not required...we dont need to use onDestroyView and nullify it)
    //Android guideline: https://developer.android.com/topic/libraries/view-binding
    //Solution blog: https://proandroiddev.com/make-android-view-binding-great-with-kotlin-b71dd9c87719
    implementation("com.github.kirich1409:viewbindingpropertydelegate:${rootProject.extra["viewbindingpropertydelegate_version"]}")

    //EventBus(Currently using it for showing/hiding ProgressBar)
    implementation("org.greenrobot:eventbus:${rootProject.extra["eventbus_version"]}")

    // Encrypted Shared Preferences
    implementation("androidx.security:security-crypto:1.1.0-alpha06")


    // Unit Test
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test:core-ktx:1.5.0")

    // Core Testing
    testImplementation("androidx.arch.core:core-testing:${rootProject.extra["core_testing_version"]}")

    // Coroutine
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${rootProject.extra["coroutines_version"]}")

    // Mockito
    testImplementation("org.mockito:mockito-core:5.7.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")

    // Turbine
    testImplementation("app.cash.turbine:turbine:0.13.0")

    // Instrumental Test
    // To use the androidx.test.core APIs
    androidTestImplementation("androidx.test:core:${rootProject.extra["android_test_core"]}")
    // Kotlin extensions for androidx.test.core
    androidTestImplementation("androidx.test:core-ktx:${rootProject.extra["android_test_core"]}")

    // To use the androidx.test.espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:" + rootProject.extra["espressoVersion"])
    androidTestImplementation("androidx.test.espresso:espresso-intents:" + rootProject.extra["espressoVersion"])
    // For using RecyclerViewActions
    androidTestImplementation("androidx.test.espresso:espresso-contrib:" + rootProject.extra["espressoVersion"])

    // To use the JUnit Extension APIs
    androidTestImplementation("androidx.test.ext:junit:${rootProject.extra["android_test_ext"]}")
    // Kotlin extensions for androidx.test.ext.junit
    androidTestImplementation("androidx.test.ext:junit-ktx:${rootProject.extra["android_test_ext"]}")

    // For testing fragment
    debugImplementation("androidx.fragment:fragment-testing:1.6.2")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}