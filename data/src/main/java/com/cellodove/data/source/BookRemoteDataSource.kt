package com.cellodove.data.source

import com.cellodove.data.model.BookResponse
import com.cellodove.data.service.KakaoBookService
import javax.inject.Inject

interface BookRemoteDataSource {
    suspend fun getBooks(userQuery: String, page: Int, size: Int): BookResponse
}

class BookRemoteDataSourceImpl @Inject constructor(
    private val kakaoBookService: KakaoBookService
) : BookRemoteDataSource{
    override suspend fun getBooks(userQuery: String, page: Int, size: Int): BookResponse {
        return kakaoBookService.getBooks(userQuery,page,size)
    }
}