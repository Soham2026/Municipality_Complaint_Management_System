package com.example.cityzen.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityzen.constants.AuthKeys
import com.example.cityzen.constants.UserRoles
import com.example.cityzen.retrofit.TokenProvider
import com.example.cityzen.retrofit.TokenProviderImpl
import com.example.cityzen.utils.DataStoreUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSessionViewModel @Inject constructor(
    private val tokenProvider: TokenProviderImpl,
    private val dataStore: DataStoreUtils
):ViewModel() {
    var token by mutableStateOf<String?>(null)
    var userId by mutableStateOf<Long?>(null)
    var role by mutableStateOf<String?>(null)
    var userName by mutableStateOf<String?>(null)
    var contactNumber by mutableStateOf<String?>(null)
    var email by mutableStateOf<String?>(null)
    var assignedWardId by mutableStateOf<Long?>(null)

    init {
        loadUserAuthDetails()
    }

    fun fetchToken(): String {
        return token.toString()
    }

    fun loadUserAuthDetails(){
        viewModelScope.launch {
            token = dataStore.readFromDataStore(AuthKeys.TOKEN.name)?.toString()
            userId = dataStore.readFromDataStore(AuthKeys.USER_ID.name)?.toString()?.toLongOrNull()
            role = dataStore.readFromDataStore(AuthKeys.ROLE.name)?.toString()
            userName = dataStore.readFromDataStore(AuthKeys.USERNAME.name)?.toString()
            contactNumber = dataStore.readFromDataStore(AuthKeys.CONTACT_NUMBER.name)?.toString()
            email = dataStore.readFromDataStore(AuthKeys.EMAIL.name)?.toString()

            if(userId != null && role == UserRoles.ADMIN.name){
                assignedWardId = dataStore.readFromDataStore(AuthKeys.ASSIGNED_WARD.name)?.toString()?.toLongOrNull()
            }
        }
    }

    fun saveUserAuthDetails(token:String,userId:Long,role:String,userName:String?,contactNumber:String,email:String){
        viewModelScope.launch {
            dataStore.saveToDataStore(AuthKeys.TOKEN.name,token)
            dataStore.saveToDataStore(AuthKeys.USER_ID.name,userId.toString())
            dataStore.saveToDataStore(AuthKeys.ROLE.name,role)
            dataStore.saveToDataStore(AuthKeys.USERNAME.name,userName?:"")
            dataStore.saveToDataStore(AuthKeys.CONTACT_NUMBER.name,contactNumber)
            dataStore.saveToDataStore(AuthKeys.EMAIL.name,email)
        }
        this.token = token
        this.userId = userId
        this.role = role
        this.userName = userName
        this.contactNumber = contactNumber
        this.email = email
    }

    fun userLogOut(){
        viewModelScope.launch {
            dataStore.clearData()
        }
        this.token=null
        this.userId=null
        this.role=null
        this.userName=null
        this.contactNumber=null
        this.email=null
    }

}


