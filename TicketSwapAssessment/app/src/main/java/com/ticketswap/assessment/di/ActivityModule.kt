package com.ticketswap.assessment.di

import com.ticketswap.assessment.LoginActivity
import com.ticketswap.assessment.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    @ActivityScope
    internal abstract fun loginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [SearchActivityModule::class])
    @ActivityScope
    internal abstract fun searchActivity(): SearchActivity
}