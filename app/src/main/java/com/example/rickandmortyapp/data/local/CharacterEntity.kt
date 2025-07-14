package com.example.rickandmortyapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val gender: Gender,
    val image: String
)
