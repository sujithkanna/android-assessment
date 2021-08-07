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
        buildConfigString("SPOTIFY_API_KEY", "84ea753e599142b8bace9b63d153227b")
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
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.kotlin_std)
    implementation(Libs.kotlin_reflect)
    implementation(Libs.appcompat)
    implementation(Libs.recyclerView)
    implementation(Libs.constraintLayout)
    implementation(Libs.coreKtx)
    implementation(Libs.material)
    implementation(Libs.uiKtx)
    implementation(Libs.androidKts)
    implementation(Libs.fragmentKtx)
    implementation(Libs.liveDataKtx)

    // Network
    implementation(Square.retrofit)
    implementation(Square.moshi_retrofit)
    implementation(Square.rxadapter)
    implementation(Square.moshi)
    implementation(Square.moshi_kotlin)
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

    //Test
    testImplementation(TestLibs.junit)
    androidTestImplementation(TestLibs.runner)
    testImplementation(TestLibs.mockito)
    testImplementation(TestLibs.mockito_kotlin)
    androidTestImplementation(TestLibs.espresso)
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
