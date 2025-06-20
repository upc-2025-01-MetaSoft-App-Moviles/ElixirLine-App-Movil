package com.metasoft.elixirline_app_movil.fieldlog.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metasoft.elixirline_app_movil.fieldlog.data.remote.FieldLogService
import com.metasoft.elixirline_app_movil.fieldlog.data.remote.model.FieldLogEntryRequest
import com.metasoft.elixirline_app_movil.fieldlog.util.CloudinaryUploader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FieldLogViewModel(
    private val fieldLogService: FieldLogService,
    private val cloudinaryUploader: CloudinaryUploader
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _selectedImages = MutableStateFlow<List<Uri>>(emptyList())
    val selectedImages: StateFlow<List<Uri>> = _selectedImages

    fun setSelectedImages(images: List<Uri>) {
        _selectedImages.value = images
    }

    fun submitEntryWithImages(
        authorId: String,
        parcelId: String,
        description: String,
        entryType: String,
        relatedTaskId: String,
        images: List<Uri> // ✅ Este es el parámetro que faltaba
    ) {
        _loading.value = true
        _success.value = false
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val photoUrls = mutableListOf<String>()
                for (uri in images) {
                    val url = cloudinaryUploader.uploadImage(uri)
                    if (url != null) {
                        photoUrls.add(url)
                    }
                }

                val request = FieldLogEntryRequest(
                    authorId = authorId,
                    parcelId = parcelId,
                    description = description,
                    entryType = entryType,
                    relatedTaskId = relatedTaskId,
                    photoUrls = photoUrls
                )

                val response = fieldLogService.createFieldLogEntry(request)
                if (response.isSuccessful) {
                    _success.value = true
                } else {
                    _errorMessage.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Excepción: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }


    fun resetState() {
        _success.value = false
        _errorMessage.value = null
        _selectedImages.value = emptyList()
    }
}
