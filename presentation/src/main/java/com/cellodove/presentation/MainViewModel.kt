package com.cellodove.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cellodove.domain.model.request.SearchBookRequest
import com.cellodove.domain.repository.BookRepository
import com.cellodove.domain.usecase.SearchBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchBookUseCase: SearchBookUseCase
) : ViewModel() {

    fun request(query: SearchBookRequest) = viewModelScope.launch {
        val response = searchBookUseCase(query,viewModelScope)
        Log.i("KakaoPay", response.toString())
    }
}