package com.example.cityzen.data.repository

import com.example.cityzen.data.dto.ComplaintCountResponse
import com.example.cityzen.data.dto.ComplaintFileRequest
import com.example.cityzen.data.dto.UserComplaintsResponse
import com.example.cityzen.retrofit.UserApi
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserComplaintsRepository @Inject constructor(
    private val userApi: UserApi
) {
    suspend fun getComplaints(userId:Long, pageNumber:Int, pageSize:Int): Response<UserComplaintsResponse>{
        return userApi.getComplaints(userId, pageNumber, pageSize)
    }

    suspend fun getComplaintsCount(userId: Long): Response<ComplaintCountResponse>{
        return userApi.getComplaintsCount(userId)
    }

    suspend fun fileComplaint(complaint: ComplaintFileRequest): Response<Unit>{
       return userApi.fileComplaint(complaint)
    }

}