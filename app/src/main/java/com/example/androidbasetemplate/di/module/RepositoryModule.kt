package com.example.androidbasetemplate.di.module

import com.example.androidbasetemplate.data.db.dao.ProductDao
import com.example.androidbasetemplate.data.db.ws.api.ProductApi
import com.example.androidbasetemplate.data.repository.ProductRepository
import com.example.androidbasetemplate.data.repository.impl.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideStoreRepository(
        productApi: ProductApi,
        productDao: ProductDao,
    ): ProductRepository {
        return ProductRepositoryImpl(productApi, productDao)
    }
}
