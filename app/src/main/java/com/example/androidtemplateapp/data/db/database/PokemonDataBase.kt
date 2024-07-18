package com.example.androidtemplateapp.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidtemplateapp.common.utils.Converters
import com.example.androidtemplateapp.data.db.database.dao.PokemonDao
import com.example.androidtemplateapp.data.db.database.dao.PokemonDetailsDao
import com.example.androidtemplateapp.data.db.database.entity.PokemonDetailsEntity
import com.example.androidtemplateapp.data.db.database.entity.PokemonEntity

@Database(
    entities = [PokemonEntity::class, PokemonDetailsEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PokemonDataBase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun pokemonDetailsDao(): PokemonDetailsDao

    suspend fun getDatabaseCreationDate() = pokemonDao().getCreationDate() ?: 0L
}
