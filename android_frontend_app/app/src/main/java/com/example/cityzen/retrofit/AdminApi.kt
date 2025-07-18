package com.example.cityzen.retrofit

import com.example.cityzen.data.dto.ComplaintStatusUpdateRequest
import com.example.cityzen.data.dto.Pagination
import com.example.cityzen.data.dto.UserComplaintsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AdminApi {

    @GET("admin/complaints/{wardId}")
    suspend fun getComplaintsByWard(
        @Path("wardId") wardId:Long,
        @Query("pageNumber") pageNumber:Int,
        @Query("pageSize") pageSize:Int
    ): Response<UserComplaintsResponse>

    @PUT("admin/complaint-status-update")
    suspend fun updateComplaintStatus(@Body complaintStatusUpdateRequest: ComplaintStatusUpdateRequest): Response<Unit>

    @GET("admin/get_ward/{adminId}")
    suspend fun getAssignedWard(@Path("adminId") adminId:Long): Response<Long>

}
