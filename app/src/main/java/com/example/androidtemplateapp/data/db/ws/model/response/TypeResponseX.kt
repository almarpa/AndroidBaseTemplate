package com.example.androidtemplateapp.data.db.ws.model.response

import com.example.androidtemplateapp.entity.TypeX
import com.google.gson.annotations.SerializedName

data class TypeResponseX(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val typeXXResponse: TypeXXResponse,
) {
    fun map() = TypeX(
        slot,
        typeXXResponse.map()
    )
}
