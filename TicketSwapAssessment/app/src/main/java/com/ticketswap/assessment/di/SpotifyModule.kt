package com.ticketswap.assessment.di

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.squareup.moshi.Moshi
import com.ticketswap.assessment.BuildConfig
import com.ticketswap.assessment.api.SpotifyApi
import com.ticketswap.assessment.utils.interceptors.SpotifyAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SpotifyModule {

    @Provides
    @Singleton
    @Named(NAMED_SPOTIFY_RETROFIT)
    fun provideSpotifyRetrofit(@Named(NAMED_SPOTIFY_OKHTTP_CLIENT) client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(SPOTIFY_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named(NAMED_SPOTIFY_OKHTTP_CLIENT)
    fun provideSpotifyOkhttpClient(interceptor: SpotifyAuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(prepareLogInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideSpotifyApi(@Named(NAMED_SPOTIFY_RETROFIT) retrofit: Retrofit): SpotifyApi {
        return retrofit.create(SpotifyApi::class.java)
    }

    companion object {
        private const val NAMED_SPOTIFY_RETROFIT = "SpotifyRetrofit"
        private const val NAMED_SPOTIFY_OKHTTP_CLIENT = "SpotifyOkhttpClient"
        private const val SPOTIFY_BASE_URL = "https://api.spotify.com/v1/"

        private fun prepareLogInterceptor() = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }
}