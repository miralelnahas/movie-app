package com.trianglz.core.di

import com.trianglz.core.datasources.MoviesDataSourceImpl
import com.trianglz.core.managers.ConnectionManagerImpl
import com.trianglz.data.managers.ConnectionManager
import com.trianglz.data.repositories.MoviesDataSource
import com.trianglz.data.repositories.MoviesRepository
import com.trianglz.data.repositories.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CoreComponents {

    @Binds
    fun moviesRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository

    @Binds
    fun moviesRemoteDs(moviesDataSourceImpl: MoviesDataSourceImpl): MoviesDataSource

    @Binds
    fun connectionManager(connectionManagerImpl: ConnectionManagerImpl): ConnectionManager
}