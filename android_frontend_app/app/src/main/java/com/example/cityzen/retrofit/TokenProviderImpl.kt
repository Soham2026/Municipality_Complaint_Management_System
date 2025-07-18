package com.example.cityzen.retrofit

import com.example.cityzen.constants.AuthKeys
import com.example.cityzen.utils.DataStoreUtils
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TokenProviderImpl @Inject constructor(private val dataStore: DataStoreUtils): TokenProvider {

    override fun fetchToken(): String? {
        return runBlocking{ dataStore.readFromDataStore(AuthKeys.TOKEN.name)?.toString() }
    }


}