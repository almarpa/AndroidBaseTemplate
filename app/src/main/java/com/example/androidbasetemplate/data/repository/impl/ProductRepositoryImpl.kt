package com.example.androidbasetemplate.data.repository.impl

import com.example.androidbasetemplate.data.db.ws.api.ProductApi
import com.example.androidbasetemplate.data.repository.ProductRepository
import com.example.androidbasetemplate.entity.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class ProductRepositoryImpl(private val productApi: ProductApi) : ProductRepository {

    override suspend fun getProducts(): List<Product> {
        return withContext(Dispatchers.IO) {
            try {
                with(
                    productApi.getProducts().execute()
                ) {
                    body()?.let { body ->
                        return@let body.map { innerProduct -> innerProduct.map() }
                    } ?: run {
                        throw Exception()
                    }
                }
            } catch (e: Throwable) {
                throw Exception(e)
            }
        }
    }

    override suspend fun getProduct(productId: Int): Product {
        return withContext(Dispatchers.IO) {
            try {
                with(
                    productApi.getProduct(productId).execute()
                ) {
                    body()?.let { body ->
                        return@let body.map()
                    } ?: run {
                        throw Exception()
                    }
                }
            } catch (e: Throwable) {
                throw Exception(e)
            }
        }
    }
}
