package com.example.disneycodechallenge.di

import com.example.disneycodechallenge.repositories.ComicRepository
import com.example.disneycodechallenge.repositories.ComicRepositoryImpl
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