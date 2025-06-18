package com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response

import com.google.gson.annotations.SerializedName

class NutrientEntity (
    @SerializedName("name")
    val name: String,
    @SerializedName("dose")
    val dose: Double,
    @SerializedName("unit")
    val unit: String = "g/L"
) {
    // Override toString for better logging
    override fun toString(): String {
        return "NutrientEntity(name='$name', dose=$dose)"
    }
}