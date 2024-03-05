package com.trianglz.core.di

import com.trianglz.core.datasources.MoviesDataSourceImpl
import com.trianglz.data.repositories.MoviesDataSource
import com.trianglz.data.repositories.MoviesRepository
import com.trianglz.data.repositories.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreComponents {

    @Binds
    abstract fun moviesRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository

    @Binds
    abstract fun moviesRemoteDs(moviesDataSourceImpl: MoviesDataSourceImpl): MoviesDataSource
}