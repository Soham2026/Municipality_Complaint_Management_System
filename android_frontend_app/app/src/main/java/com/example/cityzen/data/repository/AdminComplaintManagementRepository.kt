package com.example.cityzen.data.repository

import com.example.cityzen.data.dto.ComplaintStatusUpdateRequest
import com.example.cityzen.data.dto.Pagination
import com.example.cityzen.data.dto.UserComplaintsResponse
import com.example.cityzen.retrofit.AdminApi
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdminComplaintManagementRepository @Inject constructor(
    private val adminApi: AdminApi
) {

    suspend fun getComplaintsByWard(wardId: Long, pagination: Pagination): Response<UserComplaintsResponse> {
        return adminApi.getComplaintsByWard(wardId, pagination.pageNumber, pagination.pageSize)
    }

    suspend fun updateComplaintStatus(complaintStatusUpdateRequest: ComplaintStatusUpdateRequest): Response<Unit> {
        return adminApi.updateComplaintStatus(complaintStatusUpdateRequest)
    }

    suspend fun getAssignedWard(adminId: Long): Response<Long> {
        return adminApi.getAssignedWard(adminId)
    }



}

