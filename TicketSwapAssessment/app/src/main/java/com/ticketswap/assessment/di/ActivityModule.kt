package com.ticketswap.assessment.di

import com.ticketswap.assessment.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    @ActivityScope
    internal abstract fun bindActivity(): MainActivity
}