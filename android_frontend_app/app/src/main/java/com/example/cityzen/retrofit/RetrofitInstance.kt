package com.example.cityzen.retrofit

import com.example.cityzen.ui.viewmodels.UserSessionViewModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val baseUrl = "http://192.168.29.180:8080/waste-management-service/"

    public fun getInstance(interceptor: Interceptor): Retrofit{
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}