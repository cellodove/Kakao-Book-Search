package com.cellodove.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cellodove.data.source.BookRemotePagingDataSource
import com.cellodove.domain.model.Documents
import com.cellodove.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookRemotePagingDataSource : BookRemotePagingDataSource
): BookRepository {
    override fun searchBookPaging(userQuery: String): Flow<PagingData<Documents>> {
        return Pager(config = PagingConfig(pageSize = NETWORK_PAGE_SIZE,enablePlaceholders = false),
            pagingSourceFactory = { bookRemotePagingDataSource.getBook(userQuery) }).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}