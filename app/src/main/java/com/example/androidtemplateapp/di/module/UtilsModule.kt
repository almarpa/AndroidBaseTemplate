package com.example.androidtemplateapp.di.module

import android.util.Log
import com.example.androidtemplateapp.data.db.ws.interceptor.UrlInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Singleton
    @Named("UrlInterceptor")
    @Provides
    fun provideBaseUrlInterceptor(): UrlInterceptor {
        return UrlInterceptor()
    }

    @Provides
    @Named("LoggingInterceptor")
    fun provideLoggerInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor {
            Log.v("API-CALL", "message: $it")
        }.setLevel(
            HttpLoggingInterceptor.Level.BODY,
        )
    }
}
