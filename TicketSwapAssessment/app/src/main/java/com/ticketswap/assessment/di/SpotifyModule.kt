package com.ticketswap.assessment.di

import com.ticketswap.assessment.BuildConfig
import com.ticketswap.assessment.utils.interceptors.SpotifyAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SpotifyModule {

    @Provides
    @Singleton
    @Named(NAMED_SPOTIFY_OKHTTP_CLIENT)
    fun provideSpotifyOkhttpClient(interceptor: SpotifyAuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(prepareLogInterceptor())
            .build()
    }

    companion object {
        const val NAMED_SPOTIFY_RETROFIT = "SpotifyRetrofit"
        const val NAMED_SPOTIFY_OKHTTP_CLIENT = "SpotifyOkhttpClient"

        private fun prepareLogInterceptor() = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }
}