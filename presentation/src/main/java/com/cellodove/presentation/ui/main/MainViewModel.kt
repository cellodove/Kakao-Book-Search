package com.cellodove.presentation.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cellodove.domain.model.response.Documents
import com.cellodove.domain.usecase.SearchBookPagingUseCase
import com.cellodove.domain.usecase.SearchBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchBookUseCase: SearchBookUseCase,
    private val searchBookPagingUseCase : SearchBookPagingUseCase
) : ViewModel() {
    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<Documents>>? = null

    private val _useLiveData = MutableLiveData<FragmentStep>()
    val userLiveData: LiveData<FragmentStep>
        get() = _useLiveData
    init {
        _useLiveData.value = FragmentStep.SEARCH_BOOK
    }

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




    fun request(userQuery: String, page: Int, size: Int) = viewModelScope.launch {
        searchBookUseCase(userQuery,page,size,viewModelScope){
        }
    }

    fun fragmentChange(fragmentStep:FragmentStep){
        _useLiveData.value = fragmentStep
    }

    enum class FragmentStep{
        SEARCH_BOOK,BOOK_DETAIL
    }
}