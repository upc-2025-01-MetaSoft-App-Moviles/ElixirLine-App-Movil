package com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response

import com.google.gson.annotations.SerializedName

class ClarifyingAgentEntity(
    @SerializedName("name")
    val name: String,
    @SerializedName("dose")
    val dose: Double,
    @SerializedName("unit")
    val unit: String = "g/L" // Default unit for dose, can be overridden if needed
) {
    override fun toString(): String {
        return "ClarifyingAgentEntity(name='$name', dose=$dose)"
    }
}