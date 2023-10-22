package com.example.androidbasetemplate.di.module

import com.example.androidbasetemplate.BuildConfig
import com.example.androidbasetemplate.data.db.ws.api.PokemonApi
import com.example.androidbasetemplate.data.db.ws.interceptor.UrlInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Named("SecureApi")
    @Singleton
    fun provideSecureApiClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @Named("UrlInterceptor") urlInterceptor: UrlInterceptor,
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .readTimeout(5L, TimeUnit.SECONDS)
            .connectTimeout(5L, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(urlInterceptor)
            .build()
    }

    @Provides
    @Named("SecureApi")
    @Singleton
    fun provideSecureRetrofit(
        @Named("SecureApi") apiClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(apiClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePokemonApi(
        @Named("SecureApi") retrofit: Retrofit,
    ): PokemonApi = retrofit.create(PokemonApi::class.java)
}
