package com.example.disneycodechallenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {

    //We can add qualifiers to inject more types of CoroutineDispatcher
    @Provides
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO
}