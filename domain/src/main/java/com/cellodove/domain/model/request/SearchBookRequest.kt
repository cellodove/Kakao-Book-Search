package com.cellodove.domain.model.request

data class SearchBookRequest (
    /** 검색을 원하는 질의어 */
    val query : String,

    /** 결과 문서 정렬 방식, accuracy(정확도순) 또는 latest(발간일순), 기본값 accuracy */
    val sort : String?,

    /** 결과 페이지 번호, 1~50 사이의 값, 기본 값 1 */
    val page : Int?,

    /** 한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10 */
    val size : Int?,

    /** 검색 필드 제한 / 사용 가능한 값: title(제목), isbn (ISBN), publisher(출판사), person(인명) */
    val target : String?
)