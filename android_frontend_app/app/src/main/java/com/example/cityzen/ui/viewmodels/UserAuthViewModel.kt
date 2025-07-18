package com.example.cityzen.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityzen.constants.ApiCallState
import com.example.cityzen.data.dto.UserLoginRequest
import com.example.cityzen.data.dto.UserSignUpRequest
import com.example.cityzen.data.repository.UserAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserAuthViewModel @Inject constructor(
    private val userAuthRepository: UserAuthRepository
): ViewModel() {

    var signUpState by mutableStateOf(ApiCallState.IDLE)
    var loginState by mutableStateOf(ApiCallState.IDLE)


    fun signUpUser(request: UserSignUpRequest, onSuccess: (token:String,userId:Long,role:String,userName:String,contactNumber:String,email:String) -> Unit, onError: (code:Int) -> Unit){
        signUpState = ApiCallState.LOADING
        viewModelScope.launch {
            try {
                val response = userAuthRepository.signUp(request)
                if (response.isSuccessful){
                    response.body()?.let {
                        onSuccess(it.token,it.userId,it.role,it.userName,it.contactNumber,it.email)
                    }
                    signUpState = ApiCallState.SUCCESS
                }else{
                    signUpState = ApiCallState.ERROR
                    onError(response.code())
                }
            }catch (e: Exception){
                Log.d("API_CALL", "Got exception: $e")
                signUpState = ApiCallState.ERROR
                onError(-1)
            }

            signUpState = ApiCallState.IDLE
        }
    }

    fun userLogin(request: UserLoginRequest, onSuccess: (token:String,userId:Long,role:String,userName:String,contactNumber:String,email:String) -> Unit, onError: (code:Int) -> Unit){
        loginState = ApiCallState.LOADING
        viewModelScope.launch {
            try {
                val response = userAuthRepository.login(request)
                if(response.isSuccessful){
                    response.body()?.let{
                        onSuccess(it.token,it.userId,it.role,it.userName,it.contactNumber,it.email)
                    }
                    loginState = ApiCallState.SUCCESS
                }else{
                    loginState = ApiCallState.ERROR
                    onError(response.code())
                }
            }catch (e:Exception){
                Log.d("API_CALL", "Got exception: $e")
                loginState = ApiCallState.ERROR
                onError(-1)
            }

            loginState = ApiCallState.IDLE

        }
    }


}