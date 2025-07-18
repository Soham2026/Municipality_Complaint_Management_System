package com.example.cityzen.retrofit

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response

class Interceptor( val tokenProvider : TokenProvider) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val token = tokenProvider.fetchToken()
        Log.d("API_CALL", "FROM INTERCEPTOR: $token")

        token?.let {
            requestBuilder.addHeader("Authorization","Bearer $it")
        }

        val request = requestBuilder.build()
        Log.d("API_CALL", "FROM INTERCEPTOR WITH AUTH: ${request.header("Authorization")}")

        return chain.proceed(request)
    }
}