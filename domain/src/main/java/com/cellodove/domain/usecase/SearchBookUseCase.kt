package com.cellodove.domain.usecase

import com.cellodove.domain.model.request.SearchBookRequest
import com.cellodove.domain.model.response.SearchBookResponse
import com.cellodove.domain.repository.BookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchBookUseCase(private val kakaoSearchBookRepository : BookRepository) {
    operator fun invoke(
        searchBookRequest : SearchBookRequest,
        scope : CoroutineScope,
        onResult : (SearchBookResponse) -> Unit = {}
    ){
        scope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO){
                kakaoSearchBookRepository.searchBook(searchBookRequest)
            }
            onResult(deferred.await())
        }
    }
}