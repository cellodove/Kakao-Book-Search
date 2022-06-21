package com.kakaopay.book.di

import com.cellodove.domain.repository.BookRepository
import com.cellodove.domain.usecase.SearchBookPagingUseCase
import com.cellodove.domain.usecase.SearchBookUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun providesSearchBookUseCase(repository: BookRepository) : SearchBookUseCase{
        return SearchBookUseCase(repository)
    }

    @Provides
    fun providesSearchBookPagingUseCase(repository: BookRepository) : SearchBookPagingUseCase {
        return SearchBookPagingUseCase(repository)
    }
}