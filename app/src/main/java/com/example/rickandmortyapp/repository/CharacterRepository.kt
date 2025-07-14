package com.example.rickandmortyapp.repository

import com.example.rickandmortyapp.data.local.CharacterDao
import com.example.rickandmortyapp.data.local.CharacterEntity
import com.example.rickandmortyapp.data.local.Gender
import com.example.rickandmortyapp.data.local.Status
import com.example.rickandmortyapp.data.remote.RickAndMortyApi

interface CharacterRepository {
    suspend fun getCharacters(
        page: Int = 1,
        name: String? = null,
        species: String? = null,
        status: Status? = null,
        gender: Gender? = null,
        offline: Boolean = false
    ): List<CharacterEntity>

    suspend fun getCharacterById(id: Int): CharacterEntity?
}
