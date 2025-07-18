package com.example.cityzen.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityzen.data.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _stateMap = MutableStateFlow(emptyMap<String, String>())
    val stateMap: StateFlow<Map<String, String>> = _stateMap

    private val _cityMap = MutableStateFlow(emptyMap<String, String>())
    val cityMap: StateFlow<Map<String, String>> = _cityMap

    private val _wardMap = MutableStateFlow(emptyMap<String, String>())
    val wardMap: StateFlow<Map<String, String>> = _wardMap

    private val _cityMapList = MutableStateFlow(emptyList<String>())
    val cityMapList: StateFlow<List<String>> = _cityMapList

    private val _wardMapList = MutableStateFlow(emptyList<String>())
    val wardMapList: StateFlow<List<String>> = _wardMapList

    var stateLoadError by mutableStateOf(false)
    var cityLoadError by mutableStateOf(false)
    var wardLoadError by mutableStateOf(false)
    var isTokenExpired by mutableStateOf(false)


    init {
        loadStates()
    }

    fun resetIsTokenExpired() {
        isTokenExpired = false
    }

    fun resetStateLoadError() {
        stateLoadError = false
    }

    fun resetCityLoadError() {
        cityLoadError = false
    }

    fun resetWardLoadError() {
        wardLoadError = false
    }

    fun loadStates(onError: (Int) -> Unit = {}) {
        viewModelScope.launch {
            try {
                val response = locationRepository.getStates()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _stateMap.value = it
                    }
                } else {
                    if (response.code() == 401) {
                        isTokenExpired = true
                    } else {
                        stateLoadError = true
                    }
                }
            } catch (e: Exception) {
                Log.d("API_CALL", "From loadState() : got an exception")
                stateLoadError = true
            }
        }
    }

    fun loadCitiesFromState(stateId: Long) {
        viewModelScope.launch {
            try {
                val response = locationRepository.getCities(stateId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _cityMap.value = it
                        getKeyListFromMap(it, 2)
                    }
                } else {
                    if (response.code() == 401) {
                        isTokenExpired = true
                    } else {
                        cityLoadError = true
                    }
                }
            } catch (e: Exception) {
                Log.d("API_CALL", "From loadCity() : got an exception")
                cityLoadError = true
            }
        }
    }

    fun loadWardsFromCities(cityId: Long) {
        viewModelScope.launch {
            try {
                val response = locationRepository.getWards(cityId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _wardMap.value = it
                        getKeyListFromMap(it, 3)
                    }
                } else {

                    if (response.code() == 401) {
                        isTokenExpired = true
                    } else {
                        wardLoadError = true
                    }
                }
            } catch (e: Exception) {
                Log.d("API_CALL", "From loadWard() : got an exception")
                wardLoadError = true
            }
        }
    }


    fun getKeyListFromMap(map: Map<String, String>, id: Int): List<String> {
        if (id == 2) {
            _cityMapList.value = map.keys.toList()
        } else if (id == 3) {
            _wardMapList.value = map.keys.toList()
        }

        return map.keys.toList()
    }

    fun getIdFromMap(key: String, map: Map<String, String>): String {
        return map[key] ?: ""
    }


}