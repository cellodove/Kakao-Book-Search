package com.cellodove.presentation.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cellodove.domain.usecase.SearchBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchBookUseCase: SearchBookUseCase
) : ViewModel() {
    private val _useLiveData = MutableLiveData<FragmentStep>()

    val userLiveData: LiveData<FragmentStep>
        get() = _useLiveData

    init {
        _useLiveData.value = FragmentStep.SEARCH_BOOK
    }


    fun request(userQuery: String, page: Int, size: Int) = viewModelScope.launch {
        val response = searchBookUseCase(userQuery,page,size,viewModelScope)
        Log.i("KakaoPay", response.toString())
    }

    fun fragmentChange(fragmentStep:FragmentStep){
        _useLiveData.value = fragmentStep
    }

    enum class FragmentStep{
        SEARCH_BOOK,BOOK_DETAIL
    }
}