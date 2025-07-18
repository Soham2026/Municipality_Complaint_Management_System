package com.example.cityzen.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApi {

    @GET("location/get_states")
    suspend fun getStates(): Response<Map<String, String>>

    @GET("location/get_cities")
    suspend fun getCities(@Query("stateId") stateId: Long): Response<Map<String, String>>

    @GET("location/get_wards")
    suspend fun getWards(@Query("cityId") cityId: Long): Response<Map<String, String>>


}