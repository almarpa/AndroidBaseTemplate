package com.example.androidbasetemplate.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.androidbasetemplate.entity.Pokemon

@Dao
interface PokemonDao {

    @Query("SELECT * from pokemon WHERE name = :id")
    fun get(id: String): Pokemon?

    @Query("SELECT * from pokemon")
    fun getAll(): List<Pokemon>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemon: Pokemon)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemonList: List<Pokemon>)

    @Update
    fun update(pokemon: Pokemon)

    @Delete
    fun delete(pokemon: Pokemon)

    @Query("DELETE FROM pokemon")
    fun clearAll()
}
