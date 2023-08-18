package com.example.androidbasetemplate.di.module

import com.example.androidbasetemplate.data.repository.ProductRepository
import com.example.androidbasetemplate.domain.ProductUseCase
import com.example.androidbasetemplate.domain.impl.ProductUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideProductUseCase(
        productRepository: ProductRepository,
    ): ProductUseCase = ProductUseCaseImpl(productRepository)
}
