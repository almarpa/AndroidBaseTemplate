package com.example.androidbasetemplate.data.db.ws.model.response

import com.example.androidbasetemplate.entity.TypeX
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
