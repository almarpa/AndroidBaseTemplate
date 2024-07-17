package com.example.androidtemplateapp.di.module

import android.content.Context
import androidx.room.Room
import com.example.androidtemplateapp.data.db.database.PokemonDataBase
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
    fun provideAppDataBase(@ApplicationContext appContext: Context): PokemonDataBase {
        return Room
            .databaseBuilder(appContext, PokemonDataBase::class.java, "base.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    /** DAOs */

    @Singleton
    @Provides
    fun providePokemonDao(pokemonDataBase: PokemonDataBase) = pokemonDataBase.pokemonDao()

    @Singleton
    @Provides
    fun providePokemonDetailsDao(pokemonDataBase: PokemonDataBase) =
        pokemonDataBase.pokemonDetailsDao()
}
