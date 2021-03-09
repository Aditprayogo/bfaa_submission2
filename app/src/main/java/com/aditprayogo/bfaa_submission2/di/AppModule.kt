package com.aditprayogo.bfaa_submission2.di

import com.aditprayogo.bfaa_submission2.data.network.UserServices
import com.aditprayogo.bfaa_submission2.data.repository.UserRepositoryImpl
import com.aditprayogo.bfaa_submission2.domain.UserRepository
import com.aditprayogo.bfaa_submission2.domain.UserUseCase
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
        userServices: UserServices
    ) : UserRepository {
        return UserRepositoryImpl(userServices)
    }

    @Provides
    @Singleton
    fun provideUserUseCase(
        userRepository: UserRepository
    ) : UserUseCase {
        return UserUseCase(userRepository)
    }

}