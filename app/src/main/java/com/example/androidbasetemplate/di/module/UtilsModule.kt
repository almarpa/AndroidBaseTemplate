package com.example.androidbasetemplate.di.module

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.androidbasetemplate.common.Constants.BASE_SHARED_PREFERENCES
import com.example.androidbasetemplate.data.db.ws.interceptor.UrlInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            BASE_SHARED_PREFERENCES,
            Context.MODE_PRIVATE,
        )
    }
}
