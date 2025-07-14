package com.example.rickandmortyapp.repository

import com.example.rickandmortyapp.data.local.CharacterDao
import com.example.rickandmortyapp.data.local.CharacterEntity
import com.example.rickandmortyapp.data.local.Gender
import com.example.rickandmortyapp.data.local.Status
import com.example.rickandmortyapp.data.remote.RickAndMortyApi

class CharacterRepository(
    private val api: RickAndMortyApi,
    private val dao: CharacterDao
) {
   suspend fun getCharacters(
        page: Int,
        name: String?,
        species: String?,
        status: Status?,
        gender: Gender?,
        offline: Boolean
   ): List<CharacterEntity> {
        return if (!offline) {
            try {
                val response = api.getCharacters(
                    page = page,
                    name = name,
                    species = species,
                    status = status,
                    gender = gender
                )
                println("API LOADED: ${response.results.size}")

                val entities = response.results.map { dto ->
                    CharacterEntity(
                        dto.id,
                        dto.name,
                        Status.fromApiName(dto.status),
                        dto.species,
                        Gender.fromApiName(dto.gender),
                        dto.image
                    )
                }
                dao.insertAll(entities)
                entities
            } catch (e: Exception) {
                e.printStackTrace()
                dao.getCharacters(
                    name = "%${name ?: ""}%",
                    species = "%${species ?: ""}%",
                    status = status,
                    gender = gender,
                    limit = 20,
                    offset = (page - 1) * 20
                )
            }
        } else {
            dao.getCharacters(
                name = "%${name ?: ""}%",
                species = "%${species ?: ""}%",
                status = status,
                gender = gender,
                limit = 20,
                offset = (page - 1) * 20
            )
        }

   }
}
