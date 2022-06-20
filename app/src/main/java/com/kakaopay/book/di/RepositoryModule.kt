package com.kakaopay.book.di

import com.cellodove.data.BookRepositoryImpl
import com.cellodove.domain.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesBookRepository(repository: BookRepositoryImpl): BookRepository {
        return repository
    }
}