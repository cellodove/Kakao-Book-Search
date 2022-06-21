package com.cellodove.domain.usecase

import com.cellodove.domain.model.response.SearchBookResponse
import com.cellodove.domain.repository.BookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchBookUseCase(private val kakaoSearchBookRepository : BookRepository) {
    operator fun invoke(
        userQuery: String,
        page: Int,
        size: Int,
        scope : CoroutineScope,
        onResult : (SearchBookResponse) -> Unit = {}
    ){
        scope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO){
                kakaoSearchBookRepository.searchBook(userQuery, page, size)
            }
            onResult(deferred.await())
        }
    }
}