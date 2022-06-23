package com.cellodove.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cellodove.data.mapperToBookItemResponseList
import com.cellodove.data.service.KakaoBookService
import com.cellodove.domain.model.Documents
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val SEARCH_STARTING_PAGE_INDEX = 1

class BookRemotePagingDataSource @Inject constructor(
    private val kakaoBookService: KakaoBookService,
    private val query : String
) : PagingSource<Int, Documents>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Documents> {
        val position = params.key ?: SEARCH_STARTING_PAGE_INDEX
        val apiQuery = query
        return try {
            val response =  kakaoBookService.getBooks("KakaoAK 909689c173c91d9b3ea428891711edd1", apiQuery, position, 50)
            val bookResponse = response.documents
            val nextKey = if (response.documents.isEmpty()){
                null
            }else{
                position + 1
            }
            LoadResult.Page(data = mapperToBookItemResponseList(bookResponse), prevKey = if (position == SEARCH_STARTING_PAGE_INDEX) null else position - 1, nextKey = nextKey)
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Documents>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}