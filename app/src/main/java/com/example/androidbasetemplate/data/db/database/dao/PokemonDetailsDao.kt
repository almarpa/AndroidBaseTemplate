package com.example.androidbasetemplate.data.db.database.dao

import androidx.room.*
import com.example.androidbasetemplate.entity.PokemonDetails

@Dao
interface PokemonDetailsDao {

    @Query("SELECT * FROM pokemonDetails WHERE name = :id")
    suspend fun get(id: String): PokemonDetails?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemonDetails: PokemonDetails)

    @Update
    suspend fun update(pokemon: PokemonDetails)

    @Delete
    suspend fun delete(pokemon: PokemonDetails)

    @Query("DELETE FROM pokemonDetails")
    suspend fun clearAll()
}
