package com.cellodove.data

import com.cellodove.data.model.BookItemResponse
import com.cellodove.data.model.BookMetaResponse
import com.cellodove.data.model.BookResponse
import com.cellodove.domain.model.response.Documents
import com.cellodove.domain.model.response.Meta
import com.cellodove.domain.model.response.SearchBookResponse

fun Meta.map() = BookMetaResponse(
    total_count,
    pageable_count,
    is_end
)

fun SearchBookResponse.map() = BookResponse(
    meta.map(),
    documents.toList().map {
        BookItemResponse(
            it.title,
            it.contents,
            it.url,
            it.isbn,
            it.datetime,
            it.authors,
            it.publisher,
            it.translators,
            it.price,
            it.sale_price,
            it.thumbnail,
            it.status
        )
    }
)

fun Documents.map() = BookItemResponse(
    title,
    contents,
    url,
    isbn,
    datetime,
    authors,
    publisher,
    translators,
    price,
    sale_price,
    thumbnail,
    status
)
fun mapperToBookResponse(searchBookResponse : SearchBookResponse) : BookResponse{
    return searchBookResponse.map()
}







fun BookMetaResponse.map() = Meta(
    totalCount,
    pageableCount,
    isEnd
)

fun BookResponse.map() = SearchBookResponse(
    meta.map(),
    documents.toList().map {
        Documents(
            it.title,
            it.contents,
            it.url,
            it.isbn,
            it.datetime,
            it.authors,
            it.publisher,
            it.translators,
            it.price,
            it.salePrice,
            it.thumbnail,
            it.status
        )
    }
)
fun BookItemResponse.map() = Documents(
    title,
    contents,
    url,
    isbn,
    datetime,
    authors,
    publisher,
    translators,
    price,
    salePrice,
    thumbnail,
    status
)

fun mapperToBookResponse(bookResponse : BookResponse) : SearchBookResponse{
    return bookResponse.map()
}