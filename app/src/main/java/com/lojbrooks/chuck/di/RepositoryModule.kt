package com.lojbrooks.chuck.di

import com.lojbrooks.chuck.data.repository.ApiJokeRepository
import com.lojbrooks.chuck.domain.repository.JokeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideJokeRepository(repo: ApiJokeRepository): JokeRepository
}
