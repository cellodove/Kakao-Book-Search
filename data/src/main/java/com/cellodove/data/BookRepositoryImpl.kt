package com.cellodove.data

import com.cellodove.domain.model.request.SearchBookRequest
import com.cellodove.domain.model.response.SearchBookResponse
import com.cellodove.domain.repository.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(

): BookRepository {
    override suspend fun searchBook(searchBookRequest: SearchBookRequest): SearchBookResponse {
        TODO("Not yet implemented")
    }
}