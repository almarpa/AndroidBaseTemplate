package com.example.androidtemplateapp.data.db.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.androidtemplateapp.data.db.database.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT min(createdAt) from pokemon")
    suspend fun getCreationDate(): Long?

    @Query("SELECT * from pokemon")
    fun getAllPaged(): PagingSource<Int, PokemonEntity>

    @Query("SELECT * from pokemon WHERE isTeamMember")
    fun getAllTeamMembers(): Flow<List<PokemonEntity>>

    @Query("SELECT * from pokemon WHERE name LIKE '%' || :name || '%'")
    fun searchPokemonByName(name: String): Flow<List<PokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(pokemonList: List<PokemonEntity>)

    @Update
    suspend fun update(pokemon: PokemonEntity)

    @Delete
    suspend fun delete(pokemon: PokemonEntity)

    @Query("DELETE FROM pokemon")
    suspend fun clearAll()
}
