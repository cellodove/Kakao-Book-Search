package com.cellodove.domain.usecase

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cellodove.domain.model.response.Documents
import com.cellodove.domain.model.response.SearchBookResponse
import com.cellodove.domain.repository.BookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SearchBookPagingUseCase(private val kakaoSearchBookRepository : BookRepository) {
    fun getBookPagingData(userQuery: String, scope : CoroutineScope):Flow<PagingData<Documents>>{
        return kakaoSearchBookRepository.searchBookPaging(userQuery).cachedIn(scope)
    }
}