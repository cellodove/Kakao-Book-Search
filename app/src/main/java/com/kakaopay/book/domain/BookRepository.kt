package com.kakaopay.book.domain

import com.kakaopay.book.data.BookResponse

interface BookRepository {

    suspend fun getBookList(
        query: String,
        page: Int = 1,
        size: Int = 50
    ): BookResponse

}