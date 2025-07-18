package com.example.cityzen.ui.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.cityzen.data.repository.ImageHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ImageHandlerViewModel @Inject constructor(
    private val imageHandler: ImageHandler
): ViewModel(){

    fun uploadImage(
        imageUri: Uri, stateId: Long, cityId: Long,
        onSuccess: (String) -> Unit, onFailure: () -> Unit
    ){
        imageHandler.uploadImage(imageUri, stateId, cityId, onSuccess, onFailure)
    }

}