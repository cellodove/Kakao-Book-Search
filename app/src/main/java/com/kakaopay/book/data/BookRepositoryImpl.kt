package com.kakaopay.book.data

import com.kakaopay.book.domain.BookRepository

class BookRepositoryImpl(
    private val remoteDataSource: BookRemoteDataSource
) : BookRepository {

    override suspend fun getBookList(
        query: String,
        page: Int,
        size: Int
    ) = remoteDataSource.getBooks(query, page, size)

}