package com.cellodove.domain.usecase

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cellodove.domain.model.Documents
import com.cellodove.domain.repository.BookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class SearchBookPagingUseCase(private val kakaoSearchBookRepository : BookRepository) {
    fun getBookPagingData(userQuery: String):Flow<PagingData<Documents>>{
        return kakaoSearchBookRepository.searchBookPaging(userQuery)
    }
}