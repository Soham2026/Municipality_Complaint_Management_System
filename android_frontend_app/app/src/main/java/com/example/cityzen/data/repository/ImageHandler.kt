package com.example.cityzen.data.repository

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageHandler @Inject constructor() {
    val storageRef = FirebaseStorage.getInstance().reference
    val baseRef = storageRef.child("Images/")

    fun uploadImage(
        imageUri: Uri, stateId: Long, cityId: Long,
        onSuccess: (String) -> Unit, onFailure: () -> Unit
    ) {
        val imageRef = baseRef.child("State_Id_$stateId/City_Id_$cityId/${UUID.randomUUID()}.jpeg")

        imageRef.putFile(imageUri).addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { url ->
                onSuccess(url.toString())
            }
        }.addOnFailureListener {
            onFailure()
        }
    }

}