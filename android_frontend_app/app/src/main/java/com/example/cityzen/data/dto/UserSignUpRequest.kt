package com.example.cityzen.data.dto

data class UserSignUpRequest (
    private val userName: String,
    private val contactNumber: String,
    private val email: String,
    private val password: String,
    private val role: String

)