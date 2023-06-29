package com.roshanadke.inspireme.data.dto

import com.google.gson.annotations.SerializedName

data class AuthorInfoListDto(
    val count: Int,
    val lastItemIndex: Any,
    val page: Int,
    @SerializedName("results")
    val authorInfoDtoList: List<AuthorInfoDto>,
    val totalCount: Int,
    val totalPages: Int
)