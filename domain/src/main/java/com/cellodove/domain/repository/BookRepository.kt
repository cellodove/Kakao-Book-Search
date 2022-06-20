package com.cellodove.domain.repository

import com.cellodove.domain.model.request.SearchBookRequest
import com.cellodove.domain.model.response.SearchBookResponse

interface BookRepository {
    suspend fun searchBook(searchBookRequest : SearchBookRequest) : SearchBookResponse
}