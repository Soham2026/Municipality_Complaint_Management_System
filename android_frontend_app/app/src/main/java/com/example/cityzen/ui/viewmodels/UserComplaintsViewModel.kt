package com.example.cityzen.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityzen.constants.ApiCallState
import com.example.cityzen.data.dto.Complaint
import com.example.cityzen.data.dto.ComplaintFileRequest
import com.example.cityzen.data.repository.UserComplaintsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserComplaintsViewModel @Inject constructor(
    val userComplaintsRepository: UserComplaintsRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var responseState by mutableStateOf(ApiCallState.IDLE)
    var uploadStatus by mutableStateOf(ApiCallState.IDLE)
    var complaintCountStatus by mutableStateOf(ApiCallState.IDLE)
    var _isTokenExpired = MutableStateFlow(false)
    var isTokenExpired: StateFlow<Boolean> = _isTokenExpired
    var somethingWentWrong by mutableStateOf(false)

    private var _userComplaints = MutableStateFlow(emptyList<Complaint>())
    var userComplaints: StateFlow<List<Complaint>> = _userComplaints

    private var _currentPage = MutableStateFlow(0)
    var currentPage: StateFlow<Int> = _currentPage

    private var _totalPages = MutableStateFlow(0)
    var totalPages: StateFlow<Int> = _totalPages

    private var _totalComplaints = MutableStateFlow(0)
    var totalComplaints: StateFlow<Int> = _totalComplaints

    private var _solvedComplaints = MutableStateFlow(0)
    var solvedComplaints: StateFlow<Int> = _solvedComplaints

    private var _activeComplaints = MutableStateFlow(0)
    var activeComplaints: StateFlow<Int> = _activeComplaints

    val userId = savedStateHandle.get<Long>("userId") ?: 0

    var pageNumber = 0
    var pageSize = 10
    var isLastPage = false

    init {
        getComplaintsCount(
            userId = userId,
            onSuccess = {solved , active , total ->
                _solvedComplaints.value = solved
                _activeComplaints.value = active
                _totalComplaints.value = total
                complaintCountStatus = ApiCallState.SUCCESS
            },
            onError = {code ->

                if(code == 401){
                    _isTokenExpired.value = true
                }else{
                    somethingWentWrong = true
                }

            }
        )

        getUserComplaints(
            userId = userId,
            onError = { code ->

                if(code == 401){
                    _isTokenExpired.value = true
                }else{
                    somethingWentWrong = true
                }
            }
        )


    }


    fun resetTokenExpired() {
        _isTokenExpired.value = false
    }
    fun resetSomethingWentWrong() {
        somethingWentWrong = false
    }
    fun setTokenExpired() {
        _isTokenExpired.value = true
    }
    fun setSomethingWentWrong() {
        somethingWentWrong = true
    }

    fun setComplaintCountValues(solved: Int, active: Int, total: Int){
        _solvedComplaints.value = solved
        _activeComplaints.value = active
        _totalComplaints.value = total
    }

    fun getUserComplaints(
        userId: Long,
        onError: (Int) -> Unit,
        onLastPage:()->Unit={}
    ) {

        if(!isLastPage){
            responseState = ApiCallState.LOADING
            viewModelScope.launch {
                try {
                    val response = userComplaintsRepository.getComplaints(userId, pageNumber, pageSize)
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _userComplaints.update { oldList ->
                                oldList + it.complaint
                            }
                            _currentPage.value = it.pageable.pageNumber
                            _totalPages.value = it.totalPages
                            _totalComplaints.value = it.totalElements

                            pageNumber++
                            isLastPage = it.last

                        }
                        responseState = ApiCallState.SUCCESS
                    } else {
                        responseState = ApiCallState.ERROR
                        onError(response.code())
                    }
                } catch (e: Exception) {
                    Log.d("API_CALL", "GOT AN EXCEPTION : ${e.message}")
                    responseState = ApiCallState.ERROR
                    onError(-1)
                }

                responseState = ApiCallState.IDLE

            }
        }else{
            onLastPage()
        }

    }

    fun getComplaintsCount(
        userId: Long,
        onSuccess: (solved: Int, active: Int, total: Int) -> Unit,
        onError: (Int) -> Unit
    ) {
        complaintCountStatus = ApiCallState.LOADING
        viewModelScope.launch {
            try {
                val response = userComplaintsRepository.getComplaintsCount(userId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSuccess(it.solvedComplaints, it.activeComplaints, it.totalComplaints)
                    }

                    complaintCountStatus = ApiCallState.SUCCESS
                } else {
                    onError(response.code())
                    complaintCountStatus = ApiCallState.ERROR
                }
            } catch (e: Exception) {
                complaintCountStatus = ApiCallState.ERROR
                onError(-1)
            }
        }
    }

    fun fileComplaint(
        complaint: ComplaintFileRequest,
        onSuccess: () -> Unit,
        onError: (Int) -> Unit
    ) {
        uploadStatus = ApiCallState.LOADING
        viewModelScope.launch {
            try {
                val response = userComplaintsRepository.fileComplaint(complaint)
                if (response.isSuccessful) {
                    uploadStatus = ApiCallState.SUCCESS
                    onSuccess()
                } else {
                    onError(response.code())
                    uploadStatus = ApiCallState.ERROR
                }
            } catch (e: Exception) {
                onError(-1)
                 uploadStatus = ApiCallState.ERROR
            }
            uploadStatus = ApiCallState.IDLE

            _userComplaints.value=emptyList()
            pageNumber = 0
            isLastPage = false

            getUserComplaints(
                userId = userId,
                onError = { code ->

                    if(code == 401){
                        _isTokenExpired.value = true
                    }else{
                        somethingWentWrong = true
                    }
                }
            )

            getComplaintsCount(
                userId = userId,
                onSuccess = {solved , active , total ->
                    _solvedComplaints.value = solved
                    _activeComplaints.value = active
                    _totalComplaints.value = total
                },
                onError = {code ->

                    if(code == 401){
                        _isTokenExpired.value = true
                    }else{
                        somethingWentWrong = true
                    }

                }
            )

        }

    }


}