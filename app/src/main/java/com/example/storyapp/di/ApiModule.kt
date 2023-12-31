package com.example.storyapp.di

import com.example.storyapp.data.remote.ApiConfig
import com.example.storyapp.data.remote.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun provideApiService(): ApiServices = ApiConfig.getApiService()
}