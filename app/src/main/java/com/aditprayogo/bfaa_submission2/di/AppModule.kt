package com.aditprayogo.bfaa_submission2.di

import com.aditprayogo.bfaa_submission2.domain.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserRepository(

    ) : UserRepository {
        //todo
    }

}