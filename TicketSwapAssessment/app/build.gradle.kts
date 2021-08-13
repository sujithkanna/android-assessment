plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(Dependencies.compileSdkVersion)
    defaultConfig {
        applicationId = Dependencies.applicationId
        minSdkVersion(Dependencies.minSdkVersion)
        targetSdkVersion(Dependencies.targetSdkVersion)
        versionCode = Dependencies.versionCode
        versionName = Dependencies.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        javaCompileOptions {
            annotationProcessorOptions {
                includeCompileClasspath = false
            }
        }
        buildConfigString("SPOTIFY_API_KEY", "ea6c1ec1a223466c92c2d2a51d09353f")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
        }
    }

    signingConfigs {
        getByName("debug") {
            keyPassword = "android"
            storePassword = "android"
            keyAlias = "androiddebugkey"
            storeFile = file("../debug.keystore")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.kotlin_std)
    implementation(Libs.kotlin_reflect)
    implementation(Libs.appcompat)
    implementation(Libs.recyclerView)
    implementation(Libs.constraintLayout)
    kapt(Libs.coreKtx)
    implementation(Libs.material)
    implementation(Libs.uiKtx)
    kapt(Libs.androidKtx)
    implementation(Libs.navigationKtx)
    implementation(Libs.fragmentKts)
    implementation(Libs.liveDataKtx)

    // Network
    implementation(Square.rxadapter)
    implementation(Square.moshi)
    kapt(Square.moshi_code_gen)
    implementation(Square.okhttp)
    implementation(Square.okhttp_intercepter)
    implementation(Square.picasso)

    // RxJava
    implementation(Reactive.rxJava2)
    implementation(Reactive.rxAndroid)

    // Log
    implementation(Square.timber)

    // Hilt
    implementation(Di.hiltAndroid)
    kapt(Di.hiltCompiler)

    // Spotify
    implementation(Spotify.spotify)

    // Facebook
    implementation(Facebook.shimmer)

    //Test
    testImplementation(TestLibs.junit)
    testImplementation(TestLibs.truth)
    testImplementation(TestLibs.mockito)
    androidTestImplementation(TestLibs.runner)
    androidTestImplementation(TestLibs.espresso)
    testImplementation(TestLibs.coroutinesTest)
    testImplementation(TestLibs.coroutinesCoreTest)
}

fun com.android.build.gradle.internal.dsl.BaseFlavor.buildConfigBoolean(
    name: String,
    value: Boolean
) =
    buildConfigField("Boolean", name, value.toString())

fun com.android.build.gradle.internal.dsl.BaseFlavor.buildConfigString(
    name: String,
    value: String
) =
    buildConfigField("String", name, "\"$value\"")