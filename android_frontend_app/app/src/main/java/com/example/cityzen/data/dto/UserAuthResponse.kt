package com.example.cityzen.data.dto

import com.google.gson.annotations.SerializedName

data class UserAuthResponse(
     val token:String,
     @SerializedName("id")
     val userId: Long,
     val role: String,
     val userName: String,
     val contactNumber: String,
     val email: String
)
