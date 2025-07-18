package com.example.cityzen.data.dto

import com.google.gson.annotations.SerializedName

data class UserComplaintsResponse(
    @SerializedName("content")
    val complaint: List<Complaint>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: SortX,
    val totalElements: Int,
    val totalPages: Int
)