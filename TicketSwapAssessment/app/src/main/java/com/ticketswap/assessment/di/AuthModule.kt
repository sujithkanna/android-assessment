package com.ticketswap.assessment.di

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse.Type
import com.ticketswap.assessment.BuildConfig.SPOTIFY_API_KEY
import com.ticketswap.assessment.R
import com.ticketswap.assessment.user.UserManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @Singleton
    fun provideAuthRequest(@ApplicationContext context: Context): AuthenticationRequest {
        val uri = Uri.Builder()
            .scheme(context.getString(R.string.com_spotify_sdk_redirect_scheme))
            .authority(context.getString(R.string.com_spotify_sdk_redirect_host))
            .build()
        return AuthenticationRequest.Builder(SPOTIFY_API_KEY, Type.TOKEN, uri.toString())
            .setShowDialog(true)
            .setScopes(arrayOf("user-read-email"))
            .setCampaign("your-campaign-token")
            .build()
    }

    @Provides
    @Singleton
    fun provideUserManager(prefs: SharedPreferences): UserManager {
        return UserManager(prefs)
    }
}