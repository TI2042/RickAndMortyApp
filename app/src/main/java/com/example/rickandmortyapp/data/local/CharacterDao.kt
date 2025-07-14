package com.example.rickandmortyapp.data.local

import androidx.room.*

@Dao
interface CharacterDao {
    @Query("""
        SELECT * FROM characters
        WHERE (:name IS NULL OR name LIKE :name)
          AND (:species IS NULL OR species LIKE :species)
          AND (:status IS NULL OR status = :status)
          AND (:gender IS NULL OR gender = :gender)
        LIMIT :limit OFFSET :offset
    """)
    suspend fun getCharacters(
        name: String?,
        species: String?,
        status: Status?,
        gender: Gender?,
        limit: Int,
        offset: Int
    ): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE id = :id LIMIT 1")
    suspend fun getCharacterById(id: Int): CharacterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)

}

