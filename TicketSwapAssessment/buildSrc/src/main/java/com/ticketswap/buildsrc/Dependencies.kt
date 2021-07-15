object Version {
    const val kotlinVersion = "1.5.10"
    const val androidGradleVersion = "4.0.1"
    const val constraintLayoutVersion = "2.0.4"
    const val recyclerViewVersion = "1.2.1"

    // Compile dependencies
    const val appCompatVersion = "1.3.0"
    const val hiltVersion = "2.35"

    // Unit tests
    const val mockitoVersion = "2.13.0"

    //Rx Versions
    const val rxJava2 = "2.1.9"
    const val rxAndroid = "2.0.1"

    // Spotify
    const val spotifyVersion = "1.1.0"

    //Square Versions
    const val picassoVersion = "2.5.2"
    const val moshiConverterVersion = "2.3.0"
    const val rxAdapterVersion = "1.0.0"
    const val moshiVersion = "1.5.0"
    const val retrofitVersion = "2.3.0"
    const val okhttpVersion = "3.11.0"
    const val timberVersion = "4.6.0"
}

object Dependencies {

    val androidGradle = "com.android.tools.build:gradle:${Version.androidGradleVersion}"
    val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlinVersion}"
    val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Version.hiltVersion}"

    val minSdkVersion = 23
    val targetSdkVersion = 28
    val compileSdkVersion = 28
    val applicationId = "com.ticketswap.assessment"
    val versionCode = 1
    val versionName = "1.0"
}

object Libs {
    val kotlin_std = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Version.kotlinVersion}"
    val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Version.kotlinVersion}"
    val appcompat = "androidx.appcompat:appcompat:${Version.appCompatVersion}"
    val constraint_layout = "androidx.constraintlayout:constraintlayout:${Version.constraintLayoutVersion}"
    val recyclerView = "androidx.recyclerview:recyclerview:${Version.recyclerViewVersion}"
}

object Spotify {
    val spotify = "com.spotify.android:auth:${Version.spotifyVersion}"
}

object Square {
    val picasso = "com.squareup.picasso:picasso:${Version.picassoVersion}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofitVersion}"
    val moshi_retrofit = "com.squareup.retrofit2:converter-moshi:${Version.moshiConverterVersion}"
    val rxadapter = "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:${Version.rxAdapterVersion}"
    val moshi = "com.squareup.moshi:moshi:${Version.moshiVersion}"
    val moshi_kotlin = "com.squareup.moshi:moshi-kotlin:${Version.moshiVersion}"
    val okhttp = "com.squareup.okhttp3:okhttp:${Version.okhttpVersion}"
    val okhttp_intercepter = "com.squareup.okhttp3:logging-interceptor:${Version.okhttpVersion}"
    val timber = "com.jakewharton.timber:timber:${Version.timberVersion}"
}

object Reactive {
    val rxJava2 = "io.reactivex.rxjava2:rxjava:${Version.rxJava2}"
    val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Version.rxAndroid}"
}

object Di {
    val hiltAndroid = "com.google.dagger:hilt-android:${Version.hiltVersion}"
    val hiltCompiler = "com.google.dagger:hilt-compiler:${Version.hiltVersion}"
}

object TestLibs {
    val junit = "junit:junit:4.12"
    val runner = "com.android.support.test:runner:1.0.1"
    val mockito = "org.mockito:mockito-core:${Version.mockitoVersion}"
    val mockito_kotlin = "com.nhaarman:mockito-kotlin:1.5.0"
    val espresso = "com.android.support.test.espresso:espresso-core:3.0.1"
}