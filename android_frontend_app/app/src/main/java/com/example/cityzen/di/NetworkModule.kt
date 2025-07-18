package com.example.cityzen.di

import android.content.Context
import android.util.Log
import com.example.cityzen.retrofit.AdminApi
import com.example.cityzen.retrofit.AuthApi
import com.example.cityzen.retrofit.Interceptor
import com.example.cityzen.retrofit.LocationApi
import com.example.cityzen.retrofit.RetrofitInstance
import com.example.cityzen.retrofit.TokenProvider
import com.example.cityzen.retrofit.TokenProviderImpl
import com.example.cityzen.retrofit.UserApi
import com.example.cityzen.utils.DataStoreUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule{

    @Provides
    fun getTokenProvider(dataStoreUtils: DataStoreUtils): TokenProvider{
        return TokenProviderImpl(dataStoreUtils)
    }

    @Provides
    fun getInterceptor(tokenProvider: TokenProvider): Interceptor{
        return Interceptor(tokenProvider)
    }

    @Provides
    fun provideAuthApi(interceptor: Interceptor):AuthApi{
        return RetrofitInstance.getInstance(interceptor).create(AuthApi::class.java)
    }

    @Provides
    fun provideUserApi(interceptor: Interceptor): UserApi{
        return RetrofitInstance.getInstance(interceptor).create(UserApi::class.java)
    }

    @Provides
    fun provideLocationApi(interceptor: Interceptor): LocationApi{
        return RetrofitInstance.getInstance(interceptor).create(LocationApi::class.java)
    }

    @Provides
    fun provideAdminApi(interceptor: Interceptor): AdminApi{
        return RetrofitInstance.getInstance(interceptor).create(AdminApi::class.java)
    }


}