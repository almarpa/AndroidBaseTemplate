package com.example.androidbasetemplate.domain

import com.example.androidbasetemplate.entity.Product

interface ProductUseCase {

    suspend fun getProducts(): List<Product>
    suspend fun getProduct(productId: Int): Product
}
