package com.example.cityzen.retrofit

interface TokenProvider {
    fun fetchToken(): String?
}