package com.ticketswap.assessment.di

import android.content.Context
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.squareup.moshi.Moshi
import com.ticketswap.assessment.App
import com.ticketswap.assessment.BuildConfig
import com.ticketswap.assessment.PrefStore
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class AppModule {
    @Provides
    fun provideContext(application: App): Context = application.applicationContext

    @Provides
    fun provideRetrofit(context: Context): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        val logs = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        httpLoggingInterceptor.level = logs
        val storeService = PrefStore(context)
        val interceptor = Interceptor { chain ->
            val request = chain.request()
            val builder = request.newBuilder()
            val authToken = storeService.getAuthToken()
            if (authToken.isNotBlank()) {
                builder.addHeader("Authorization", authToken)
            }
            val updatedRequest = builder.build()
            chain.proceed(updatedRequest)
        }
        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()
        return Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.spotify.com/v1/")
                .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}