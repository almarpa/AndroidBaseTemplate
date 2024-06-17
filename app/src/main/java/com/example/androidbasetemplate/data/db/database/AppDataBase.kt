package com.example.androidbasetemplate.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidbasetemplate.common.utils.Converters
import com.example.androidbasetemplate.data.db.database.dao.PokemonDao
import com.example.androidbasetemplate.data.db.database.dao.PokemonDetailsDao
import com.example.androidbasetemplate.entity.Pokemon
import com.example.androidbasetemplate.entity.PokemonDetails

@Database(entities = [Pokemon::class, PokemonDetails::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun pokemonDetailsDao(): PokemonDetailsDao
}
