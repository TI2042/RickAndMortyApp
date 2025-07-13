package com.example.rickandmortyapp.data.local

import androidx.room.*

@Dao
interface CharacterDao {
    @Query("""
    SELECT * FROM characters
    WHERE (:name IS NULL OR name LIKE '%' || :name || '%')
      AND (:species IS NULL OR species = :species)
      AND (:status IS NULL OR status = :status)
      AND (:gender IS NULL OR gender = :gender)
""")
    suspend fun getCharacters(
        name: String?,
        species: String?,
        status: String?,
        gender: String?
    ): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)
}
