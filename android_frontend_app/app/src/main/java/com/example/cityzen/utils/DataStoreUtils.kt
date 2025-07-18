package com.example.cityzen.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreUtils @Inject constructor( @ApplicationContext val context : Context) {

    private val Context.dataStore by preferencesDataStore("user_details")

    suspend fun saveToDataStore(key: String, value : String){
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit {
            it[dataStoreKey] = value;
        }
    }

    suspend fun readFromDataStore(key: String): Any? {
        return context.dataStore.data.map {
            it[stringPreferencesKey(key)]
        }.first()
    }

    suspend fun clearData(){
        context.dataStore.edit {
            it.clear()
        }
    }

}