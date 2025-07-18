package com.example.cityzen.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cityzen.constants.AuthKeys
import com.example.cityzen.data.dto.Complaint
import com.example.cityzen.data.dto.ComplaintStatusUpdateRequest
import com.example.cityzen.data.dto.Pagination
import com.example.cityzen.data.repository.AdminComplaintManagementRepository
import com.example.cityzen.utils.DataStoreUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AdminComplaintManagementViewModel @Inject constructor(
    private val adminComplaintManagementRepository: AdminComplaintManagementRepository,
    private val dataStore: DataStoreUtils
) : ViewModel() {


    private var _adminComplaints = MutableStateFlow(emptyList<Complaint>())
    var adminComplaints: StateFlow<List<Complaint>> = _adminComplaints
    var assignedWard: Long = 0


    var isComplaintsLoading = false


    var pageNumber = 0
    var pageSize = 10
    var totalPages = 0
    var isLastPage = false


    fun getComplaintsByWard(
        wardId: Long,
        onSuccess: () -> Unit,
        onLastPageReceived: () -> Unit,
        onError: (Int) -> Unit
    ) {
        if (!isLastPage) {
            viewModelScope.launch {
                isComplaintsLoading = true
                Log.d("ADMIN_COMPLAINT_MANAGEMENT", "FETCHING COMPLAINTS FOR WARD : ${wardId}, by ")
                try {
                    val page = Pagination(pageNumber, pageSize)
                    val response =
                        adminComplaintManagementRepository.getComplaintsByWard(wardId, page)

                    if (response.isSuccessful) {
                        response.body()?.let {
                            _adminComplaints.update { oldList ->
                                oldList + it.complaint
                            }
                            pageNumber++
                            isLastPage = it.last
                            onSuccess()
                        }
                    } else {
                        Log.d("ADMIN_COMPLAINT_MANAGEMENT", "GOT AN ERROR : ${response.code()}")
                        onError(response.code())
                    }
                } catch (e: Exception) {
                    Log.d("ADMIN_COMPLAINT_MANAGEMENT", "GOT AN EXCEPTION : ${e.message}")
                    onError(-1)
                }

            }
        } else {
            onLastPageReceived()
        }

    }

    fun getAssignedWard(userId: Long, onSuccess: (Long) -> Unit) {
        viewModelScope.launch {
            try {
                val response = adminComplaintManagementRepository.getAssignedWard(userId)

                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("ADMIN_COMPLAINT_MANAGEMENT", "GOT THE ASSIGNED WARD : $it")
                        assignedWard = it
                        onSuccess(it)
                        dataStore.saveToDataStore(AuthKeys.ASSIGNED_WARD.name, it.toString())
                    }
                } else {
                    Log.d("ADMIN_COMPLAINT_MANAGEMENT", "GOT AN ERROR : ${response.code()}")
                }
            } catch (e: Exception) {
                Log.d("ADMIN_COMPLAINT_MANAGEMENT", "GOT AN EXCEPTION : ${e.message}")

            }
        }
    }

    fun updateComplaintStatus(
        complaintId: Long,
        status: String,
        onSuccess: () -> Unit,
        onError: (Int) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = adminComplaintManagementRepository.updateComplaintStatus(
                    complaintStatusUpdateRequest = ComplaintStatusUpdateRequest(complaintId, status)
                )
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError(response.code())
                }
            }catch (e: Exception){
                onError(-1)
            }
        }
    }

}

