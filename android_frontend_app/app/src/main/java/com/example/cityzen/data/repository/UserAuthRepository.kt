package com.example.cityzen.data.repository

import com.example.cityzen.data.dto.UserAuthResponse
import com.example.cityzen.data.dto.UserLoginRequest
import com.example.cityzen.data.dto.UserSignUpRequest
import com.example.cityzen.retrofit.AuthApi
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserAuthRepository @Inject constructor(
    private val authApi: AuthApi
){
    suspend fun login(request: UserLoginRequest): Response<UserAuthResponse>{
        return authApi.login(request)
    }

    suspend fun signUp(request: UserSignUpRequest): Response<UserAuthResponse>{
        return authApi.signUp(request)
    }

}