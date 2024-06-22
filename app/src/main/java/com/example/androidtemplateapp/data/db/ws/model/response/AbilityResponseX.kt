package com.example.androidtemplateapp.data.db.ws.model.response

import com.google.gson.annotations.SerializedName

data class AbilityResponseX(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
)
