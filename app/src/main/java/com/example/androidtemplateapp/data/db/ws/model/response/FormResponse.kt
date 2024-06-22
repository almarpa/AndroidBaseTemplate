package com.example.androidtemplateapp.data.db.ws.model.response

import com.example.androidtemplateapp.entity.Form
import com.google.gson.annotations.SerializedName

data class FormResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
) {
    fun map() = Form(
        name = name,
        url = url,
    )
}
