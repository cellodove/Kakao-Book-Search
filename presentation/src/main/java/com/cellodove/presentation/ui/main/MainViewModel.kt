package com.cellodove.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cellodove.domain.model.Documents
import com.cellodove.domain.usecase.SearchBookPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchBookPagingUseCase : SearchBookPagingUseCase
) : ViewModel() {
    private val checkLikeList = ArrayList<String>()

    fun searchBook(queryString: String): Flow<PagingData<Documents>> {
        return searchBookPagingUseCase.getBookPagingData(queryString).cachedIn(viewModelScope)
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