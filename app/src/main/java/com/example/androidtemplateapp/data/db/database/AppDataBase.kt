package com.example.androidtemplateapp.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidtemplateapp.common.utils.Converters
import com.example.androidtemplateapp.data.db.database.dao.PokemonDao
import com.example.androidtemplateapp.data.db.database.dao.PokemonDetailsDao
import com.example.androidtemplateapp.entity.Pokemon
import com.example.androidtemplateapp.entity.PokemonDetails

@Database(entities = [Pokemon::class, PokemonDetails::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun pokemonDetailsDao(): PokemonDetailsDao
}
