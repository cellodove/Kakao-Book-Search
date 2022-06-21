package com.kakaopay.book.di

import com.cellodove.data.BookRepositoryImpl
import com.cellodove.data.source.BookRemoteDataSource
import com.cellodove.data.source.BookRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun providesGithubRemoteSource(source: BookRemoteDataSourceImpl): BookRemoteDataSource {
        return source
    }
}