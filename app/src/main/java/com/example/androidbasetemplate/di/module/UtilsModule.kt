package com.example.androidbasetemplate.di.module

import android.content.Context
import android.content.SharedPreferences
import com.example.androidbasetemplate.common.Constants.BASE_SHARED_PREFERENCES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            BASE_SHARED_PREFERENCES,
            Context.MODE_PRIVATE,
        )
    }
}
