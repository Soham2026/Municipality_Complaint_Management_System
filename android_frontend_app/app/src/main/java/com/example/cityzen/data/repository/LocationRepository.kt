package com.example.cityzen.data.repository

import com.example.cityzen.retrofit.LocationApi
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
    private val locationApi: LocationApi
) {

    suspend fun getStates(): Response<Map<String, String>>{
        return locationApi.getStates()
    }


    suspend fun getCities(stateId:Long): Response<Map<String, String>>{
        return locationApi.getCities(stateId)
    }

    suspend fun getWards(cityId:Long): Response<Map<String, String>>{
        return locationApi.getWards(cityId)
    }

}