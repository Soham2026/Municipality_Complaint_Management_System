package com.example.cityzen.retrofit

import com.example.cityzen.data.dto.ComplaintCountResponse
import com.example.cityzen.data.dto.ComplaintFileRequest
import com.example.cityzen.data.dto.UserComplaintsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @GET("user/get_complaints")
    suspend fun getComplaints(
        @Query("userId") userId:Long,
        @Query("pageNumber") pageNumber : Int,
        @Query("pageSize") pageSize : Int
    ): Response<UserComplaintsResponse>

    @GET("user/getcount")
    suspend fun getComplaintsCount(
        @Query("userId") userId:Long
    ): Response<ComplaintCountResponse>

    @POST("user/file_complaints")
    suspend fun fileComplaint(
        @Body complaint: ComplaintFileRequest
    ): Response<Unit>


}