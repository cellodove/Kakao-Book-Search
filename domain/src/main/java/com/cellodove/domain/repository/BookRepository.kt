package com.cellodove.domain.repository

import androidx.paging.PagingData
import com.cellodove.domain.model.Documents
import com.cellodove.domain.model.SearchBookResponse
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBook(userQuery: String, page: Int, size: Int) : SearchBookResponse

    fun searchBookPaging(userQuery: String) : Flow<PagingData<Documents>>
}