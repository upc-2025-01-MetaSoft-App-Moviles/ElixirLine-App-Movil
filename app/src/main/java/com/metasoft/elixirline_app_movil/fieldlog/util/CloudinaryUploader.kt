package com.metasoft.elixirline_app_movil.fieldlog.util

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.InputStream

class CloudinaryUploader(private val context: Context) {

    private val cloudName = "dum0eajyn" // ✅ Tu nuevo cloud_name
    private val uploadPreset = "elixir" // ✅ Nuevo preset UNSIGNED
    private val uploadUrl = "https://api.cloudinary.com/v1_1/$cloudName/image/upload"
    private val client = OkHttpClient()

    suspend fun uploadImage(imageUri: Uri): String? = withContext(Dispatchers.IO) {
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
            val fileName = getFileName(imageUri) ?: "image.jpg"

            val requestBody = inputStream?.readBytes()?.let {
                RequestBody.create("image/*".toMediaTypeOrNull(), it)
            } ?: return@withContext null

            val multipartBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName, requestBody)
                .addFormDataPart("upload_preset", uploadPreset) // 🔑 Necesario para unsigned
                .addFormDataPart("folder", "bitacoras") // ✅ Tu carpeta definida
                .build()

            val request = Request.Builder()
                .url(uploadUrl)
                .post(multipartBody)
                .build()

            client.newCall(request).execute().use { response ->
                val json = response.body?.string()
                if (response.isSuccessful && json != null) {
                    val regex = """"secure_url":"(.*?)"""".toRegex()
                    return@withContext regex.find(json)?.groups?.get(1)?.value
                }
                Log.e("CloudinaryUploader", "Upload failed: $json")
                null
            }
        } catch (e: Exception) {
            Log.e("CloudinaryUploader", "Exception uploading image", e)
            null
        }
    }

    private fun getFileName(uri: Uri): String? {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        return cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (it.moveToFirst()) it.getString(nameIndex) else null
        }
    }
}
