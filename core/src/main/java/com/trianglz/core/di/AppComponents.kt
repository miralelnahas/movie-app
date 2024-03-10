package com.trianglz.core.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.trianglz.core.BuildConfig
import com.trianglz.core.database.AppDatabase
import com.trianglz.core.database.movies.MoviesDao
import com.trianglz.core.database.remoteKeys.RemoteKeysDao
import com.trianglz.core.network.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppComponents {
    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideRetrofitClient(
        authorizationInterceptor: AuthorizationInterceptor
    ): Retrofit {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(authorizationInterceptor).build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideMoviesDao(appDatabase: AppDatabase): MoviesDao =
        appDatabase.movieEntityDao()

    @Provides
    @Singleton
    fun provideRemoteKeyDao(appDatabase: AppDatabase): RemoteKeysDao =
        appDatabase.remoteKeyDao()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, MOVIES_DATA_BASE_NAME)
            .build()

    companion object {
        private const val MOVIES_DATA_BASE_NAME = "movies_database.db"
    }

}