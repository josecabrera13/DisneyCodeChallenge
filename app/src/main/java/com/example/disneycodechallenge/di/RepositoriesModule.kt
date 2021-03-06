package com.example.disneycodechallenge.di

import com.example.disneycodechallenge.data.ComicRepository
import com.example.disneycodechallenge.data.ComicRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    @Binds
    abstract fun bindComicRepository(
        comicRepositoryImpl: ComicRepositoryImpl
    ): ComicRepository
}