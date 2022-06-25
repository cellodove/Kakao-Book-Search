package com.cellodove.data

import com.cellodove.data.model.BookItemResponse
import com.cellodove.domain.model.Documents

fun mapperToDocumentsList(bookItemResponse:List<BookItemResponse>) : List<Documents>{
    return bookItemResponse.toList().map {
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
}