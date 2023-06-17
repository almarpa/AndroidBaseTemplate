package com.example.androidbasetemplate.data.repository

import com.example.androidbasetemplate.entity.Product

interface ProductRepository {

    suspend fun getProducts(): List<Product>
    suspend fun getProduct(productId: Int): Product
}
