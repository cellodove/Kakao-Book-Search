package com.kakaopay.book.di

import com.cellodove.data.source.BookRemotePagingDataSource
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
    fun providesBookRemotePagingDataSource(source: BookRemotePagingDataSource): BookRemotePagingDataSource {
        return source
    }
}