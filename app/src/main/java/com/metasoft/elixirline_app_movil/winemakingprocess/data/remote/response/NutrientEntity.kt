package com.metasoft.elixirline_app_movil.winemakingprocess.data.remote.response

import com.google.gson.annotations.SerializedName

data class NutrientEntity(
    @SerializedName("name")
    val name: String?,

    @SerializedName("quantityMgL")
    val quantityMgL: Double?,

    @SerializedName("unit")
    val unit: String?
) {
    override fun toString(): String {
        return "NutrientEntity(name=${name ?: "N/A"}, quantityMgL=${quantityMgL ?: 0.0}, unit=${unit ?: "N/A"})"
    }
}
