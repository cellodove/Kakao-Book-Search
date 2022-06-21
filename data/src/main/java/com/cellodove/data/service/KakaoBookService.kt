package com.cellodove.data.service

import com.cellodove.data.model.BookResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoBookService {

    @GET("/v3/search/book")
    suspend fun getBooks(
        @Header(value = "Authorization") token : String,
        @Query(value = "query") userQuery: String,
        @Query(value = "page") page: Int,
        @Query(value = "size") size: Int
    ): BookResponse
}