package com.metasoft.elixirline_app_movil.fieldlog.data.remote

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.File

class CloudinaryUploader {

    private val cloudName = "dcb5swgui"
    private val uploadPreset = "elixir"
    private val uploadUrl = "https://api.cloudinary.com/v1_1/$cloudName/image/upload"

    fun uploadImage(file: File): String? {
        val client = OkHttpClient()

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", file.name,
                RequestBody.create("image/*".toMediaTypeOrNull(), file))
            .addFormDataPart("upload_preset", uploadPreset)
            .build()

        val request = Request.Builder()
            .url(uploadUrl)
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return null

            val json = response.body?.string()
            val regex = Regex("\"secure_url\":\"(.*?)\"")
            val match = regex.find(json ?: "") ?: return null

            return match.groupValues[1].replace("\\/", "/")
        }
    }
}
