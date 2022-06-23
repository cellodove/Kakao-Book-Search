package com.cellodove.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cellodove.domain.model.response.Documents
import com.cellodove.domain.usecase.SearchBookPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchBookPagingUseCase : SearchBookPagingUseCase
) : ViewModel() {
    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<Documents>>? = null
    private val checkLikeList = ArrayList<String>()

    fun searchBook(queryString: String): Flow<PagingData<Documents>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<Documents>> = searchBookPagingUseCase.getBookPagingData(queryString,viewModelScope)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    fun addCheckLike(title : String){
        checkLikeList.add(title)
    }

    fun deleteCheckLike(title : String){
        checkLikeList.remove(title)
    }

    fun getCheckLikeList():ArrayList<String>{
        return checkLikeList
    }

    fun deleteAllLikeList(){
        checkLikeList.clear()
    }
}