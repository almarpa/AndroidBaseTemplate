package com.example.androidtemplateapp.data.db.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.androidtemplateapp.data.db.database.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Query("SELECT * from pokemon WHERE name = :id")
    suspend fun get(id: String): PokemonEntity?

    @Query("SELECT * from pokemon")
    fun getAllPaged(): PagingSource<Int, PokemonEntity>
    
    @Query("SELECT * from pokemon WHERE isTeamMember")
    suspend fun getAllTeamMembers(): List<PokemonEntity>

    @Query("SELECT * from pokemon WHERE name LIKE '%' || :name || '%'")
    suspend fun searchPokemonByName(name: String): List<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemonList: List<PokemonEntity>)

    @Update
    suspend fun update(pokemon: PokemonEntity)

    @Delete
    suspend fun delete(pokemon: PokemonEntity)

    @Query("DELETE FROM pokemon")
    suspend fun clearAll()
}
