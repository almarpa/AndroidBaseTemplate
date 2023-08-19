package com.example.androidbasetemplate.di.module

import com.example.androidbasetemplate.data.db.ws.api.ProductApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Named("SecureTemplateApi")
    @Singleton
    fun provideSecureTemplateApiClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .readTimeout(5L, TimeUnit.SECONDS)
            .connectTimeout(5L, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Named("SecureTemplateApi")
    @Singleton
    fun provideSecureTemplateRetrofit(
        @Named("SecureTemplateApi") apiClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://template-app")
            .client(apiClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideProductApi(
        @Named("SecureTemplateApi") retrofit: Retrofit,
    ): ProductApi = retrofit.create(ProductApi::class.java)
}
