package com.aditprayogo.bfaa_submission2.di

import com.aditprayogo.bfaa_submission2.data.network.UserServices
import com.aditprayogo.bfaa_submission2.network.Network
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideUserServices() : UserServices {
        return Network.retorfitClient().create(UserServices::class.java)
    }
}