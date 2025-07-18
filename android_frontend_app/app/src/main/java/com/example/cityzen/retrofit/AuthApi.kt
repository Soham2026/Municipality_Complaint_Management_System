package com.example.cityzen.retrofit

import com.example.cityzen.data.dto.UserLoginRequest
import com.example.cityzen.data.dto.UserAuthResponse
import com.example.cityzen.data.dto.UserSignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("login")
    suspend fun login(@Body request: UserLoginRequest): Response<UserAuthResponse>

    @POST("user_signup")
    suspend fun signUp(@Body request: UserSignUpRequest): Response<UserAuthResponse>

}