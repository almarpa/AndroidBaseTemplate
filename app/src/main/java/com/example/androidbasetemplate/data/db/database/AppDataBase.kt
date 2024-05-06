package com.example.androidbasetemplate.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidbasetemplate.data.db.database.dao.PokemonDao
import com.example.androidbasetemplate.entity.Pokemon

@Database(entities = [Pokemon::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
