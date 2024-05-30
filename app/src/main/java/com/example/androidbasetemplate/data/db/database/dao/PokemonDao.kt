package com.example.androidbasetemplate.data.db.database.dao

import androidx.room.*
import com.example.androidbasetemplate.entity.Pokemon

@Dao
interface PokemonDao {

    @Query("SELECT * from pokemon WHERE name = :id")
    suspend fun get(id: String): Pokemon?

    @Query("SELECT * from pokemon")
    suspend fun getAll(): List<Pokemon>

    @Query("SELECT * from pokemon WHERE isFavourite")
    suspend fun getAllFavourites(): List<Pokemon>

    @Query("SELECT * from pokemon WHERE name LIKE '%' || :name || '%'")
    suspend fun searchPokemonByName(name: String): List<Pokemon>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: Pokemon)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemonList: List<Pokemon>)

    @Update
    suspend fun update(pokemon: Pokemon)

    @Delete
    suspend fun delete(pokemon: Pokemon)

    @Query("DELETE FROM pokemon")
    suspend fun clearAll()
}
