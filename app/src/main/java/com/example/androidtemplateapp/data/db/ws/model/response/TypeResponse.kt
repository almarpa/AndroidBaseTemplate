package com.example.androidtemplateapp.data.db.ws.model.response

import com.example.androidtemplateapp.entity.Type
import com.google.gson.annotations.SerializedName

class TypeResponse(
    @SerializedName("name")
    var name: String,
    @SerializedName("url")
    var url: String,
) {
    fun map(): Type {
        return Type(
            name = name,
            url = url,
        )
    }
}
