package com.cellodove.domain.repository

import com.cellodove.domain.model.response.SearchBookResponse

interface BookRepository {
    suspend fun searchBook(userQuery: String, page: Int, size: Int) : SearchBookResponse
}