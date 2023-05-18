package com.example.androidbasetemplate.data.db.ws.model.response

import com.google.gson.annotations.SerializedName

class ProductResponse(
    @SerializedName("productId")
    var productId: String?,
    @SerializedName("denomination")
    var denomination: String?
)
