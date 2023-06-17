package com.example.androidbasetemplate.domain.impl

import com.example.androidbasetemplate.data.repository.ProductRepository
import com.example.androidbasetemplate.domain.ProductUseCase
import com.example.androidbasetemplate.entity.Product

class ProductUseCaseImpl(private val productRepository: ProductRepository) : ProductUseCase {

    override suspend fun getProducts(): List<Product> {
        return productRepository.getProducts()
    }

    override suspend fun getProduct(productId: Int): Product {
        return productRepository.getProduct(productId)
    }
}
