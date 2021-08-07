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

    const val androidGradle = "com.android.tools.build:gradle:${Version.androidGradleVersion}"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Version.hiltVersion}"

    const val minSdkVersion = 23
    const val targetSdkVersion = 28
    const val compileSdkVersion = 28
    const val applicationId = "com.ticketswap.assessment"
    const val versionCode = 1
    const val versionName = "1.0"
}

object Libs {
    const val kotlin_std = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Version.kotlinVersion}"
    const val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Version.kotlinVersion}"
    const val appcompat = "androidx.appcompat:appcompat:${Version.appCompatVersion}"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Version.constraintLayoutVersion}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Version.recyclerViewVersion}"
}

object Spotify {
    const val spotify = "com.spotify.android:auth:${Version.spotifyVersion}"
}

object Square {
    const val picasso = "com.squareup.picasso:picasso:${Version.picassoVersion}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofitVersion}"
    const val moshi_retrofit = "com.squareup.retrofit2:converter-moshi:${Version.moshiConverterVersion}"
    const val rxadapter = "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:${Version.rxAdapterVersion}"
    const val moshi = "com.squareup.moshi:moshi:${Version.moshiVersion}"
    const val moshi_kotlin = "com.squareup.moshi:moshi-kotlin:${Version.moshiVersion}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Version.okhttpVersion}"
    const val okhttp_intercepter = "com.squareup.okhttp3:logging-interceptor:${Version.okhttpVersion}"
    const val timber = "com.jakewharton.timber:timber:${Version.timberVersion}"
}

object Reactive {
    const val rxJava2 = "io.reactivex.rxjava2:rxjava:${Version.rxJava2}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Version.rxAndroid}"
}

object Di {
    const val hiltAndroid = "com.google.dagger:hilt-android:${Version.hiltVersion}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Version.hiltVersion}"
}

object TestLibs {
    const val junit = "junit:junit:4.12"
    const val runner = "com.android.support.test:runner:1.0.1"
    const val mockito = "org.mockito:mockito-core:${Version.mockitoVersion}"
    const val mockito_kotlin = "com.nhaarman:mockito-kotlin:1.5.0"
    const val espresso = "com.android.support.test.espresso:espresso-core:3.0.1"
}