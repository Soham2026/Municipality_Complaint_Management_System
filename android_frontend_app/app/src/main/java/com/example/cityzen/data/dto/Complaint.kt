package com.example.cityzen.data.dto

data class Complaint(
    val address: String,
    val cityId: Long,
    val complaint: String,
    val complaintId: Long,
    val createdAt: String,
    val imageUrl: String,
    val pinCode: String,
    val stateId: Long,
    val status: String,
    val userId: Long,
    val wardId: Long
)