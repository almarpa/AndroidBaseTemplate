package com.example.androidbasetemplate.data.db.ws.api

import com.example.androidbasetemplate.data.db.ws.model.response.ProductResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {

    @GET("products/api/v2/products}")
    fun getProducts(): Call<List<ProductResponse>>

    @GET("products/api/v2/product/{productId}")
    fun getProduct(@Path("productId") warehouseId: String): Call<ProductResponse>
}
