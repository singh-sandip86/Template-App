buildscript {
    repositories {
        mavenCentral()
    }
    extra.apply {
        set("core_ktx_version", "1.12.0")
        set("appcompat_version", "1.6.1")
        set("constraintlayout_version", "2.1.4")
        set("material_version", "1.11.0")

        set("dagger_hilt", "2.51")
        set("hilt_compiler_version", "1.0.0")

        set("lifecycle_version", "2.8.0-alpha02")
        set("coroutines_version", "1.7.0")
        set("nav_version", "2.5.3")
        set("navigation_safe_args_gradle_plugin_version", "2.5.0")

        set("splash_screen", "1.0.1")
        set("paging_version", "3.1.1")
        set("gson_version", "2.10.1")
        set("retrofit_version", "2.9.0")
        set("okhttp_version", "5.0.0-alpha.9")

        set("glide_version", "4.15.1")
        set("viewbindingpropertydelegate_version", "1.5.9")
        set("eventbus_version", "3.3.1")

        set("core_testing_version", "2.2.0")
        set("android_test_core", "1.5.0")
        set("android_test_ext", "1.1.5")
        set("espressoVersion", "3.5.1")
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${rootProject.extra["navigation_safe_args_gradle_plugin_version"]}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${rootProject.extra["dagger_hilt"]}")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("org.jetbrains.kotlin.kapt") version "1.6.10" apply false
    id("com.google.dagger.hilt.android") version "2.51" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}