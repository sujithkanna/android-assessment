package com.ticketswap.assessment.di

import com.ticketswap.assessment.api.SpotifyApi
import com.ticketswap.assessment.features.search.SearchRepository
import com.ticketswap.assessment.features.search.SearchRepositoryImpl
import com.ticketswap.assessment.user.UserManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideSpotifyOkhttpClient(
        userManager: UserManager, spotifyApi: SpotifyApi
    ): SearchRepository {
        return SearchRepositoryImpl(userManager, spotifyApi)
    }
}