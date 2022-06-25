package com.cellodove.domain.repository

import androidx.paging.PagingData
import com.cellodove.domain.model.Documents
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun searchBookPaging(userQuery: String) : Flow<PagingData<Documents>>
}