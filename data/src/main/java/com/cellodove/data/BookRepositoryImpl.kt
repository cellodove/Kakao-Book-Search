package com.cellodove.data

import com.cellodove.data.source.BookRemoteDataSource
import com.cellodove.domain.model.response.SearchBookResponse
import com.cellodove.domain.repository.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookRemoteDataSource : BookRemoteDataSource
): BookRepository {
    override suspend fun searchBook(userQuery: String, page: Int, size: Int,): SearchBookResponse {
        return mapperToBookResponse(bookRemoteDataSource.getBooks(userQuery,page,size))
    }
}