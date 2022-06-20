package com.kakaopay.book.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakaopay.book.domain.BookRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: BookRepository
) : ViewModel() {

    fun request(query: String) = viewModelScope.launch {
        val response = repository.getBookList(query)
        Log.i("KakaoPay", response.toString())
    }
}