package com.kakaopay.book.data

import retrofit2.http.GET
import retrofit2.http.Query

interface BookRemoteDataSource {

    @GET("/v3/search/book")
    suspend fun getBooks(
        @Query(value = "query") userQuery: String,
        @Query(value = "page") page: Int,
        @Query(value = "size") size: Int
    ): BookResponse

}