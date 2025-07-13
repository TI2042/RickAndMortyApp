package com.example.rickandmortyapp.data.local

import androidx.room.*

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters WHERE name LIKE :name AND species LIKE :species AND status LIKE :status AND gender LIKE :gender")
    suspend fun getCharacters(
        name: String,
        species: String,
        status: String,
        gender: String
    ): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)
}
