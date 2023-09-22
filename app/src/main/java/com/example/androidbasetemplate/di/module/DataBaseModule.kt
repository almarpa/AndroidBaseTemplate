package com.example.androidbasetemplate.di.module

import android.content.Context
import androidx.room.Room
import com.example.androidbasetemplate.data.db.database.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(appContext, AppDataBase::class.java, "base.db").build()
    }

    /** DAOs */

    @Singleton
    @Provides
    fun providePokemonDao(appDataBase: AppDataBase) = appDataBase.pokemonDao()
}
