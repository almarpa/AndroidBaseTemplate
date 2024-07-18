package com.example.androidtemplateapp.data.db.database.dao

import androidx.room.*
import com.example.androidtemplateapp.data.db.database.entity.PokemonDetailsEntity

@Dao
interface PokemonDetailsDao {

    @Query("SELECT * FROM pokemonDetails WHERE name = :id")
    suspend fun get(id: String): PokemonDetailsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemonDetails: PokemonDetailsEntity)

    @Update
    suspend fun update(pokemonDetails: PokemonDetailsEntity)

    @Delete
    suspend fun delete(pokemonDetails: PokemonDetailsEntity)

    @Query("DELETE FROM pokemonDetails")
    suspend fun clearAll()
}
