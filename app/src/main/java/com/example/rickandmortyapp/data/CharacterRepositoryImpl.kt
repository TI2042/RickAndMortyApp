package com.example.rickandmortyapp.data

import com.example.rickandmortyapp.data.local.CharacterDao
import com.example.rickandmortyapp.data.local.CharacterEntity
import com.example.rickandmortyapp.data.local.Status
import com.example.rickandmortyapp.data.local.Gender
import com.example.rickandmortyapp.data.remote.RickAndMortyApi
import com.example.rickandmortyapp.repository.CharacterRepository



class CharacterRepositoryImpl(
    private val api: RickAndMortyApi,
    private val dao: CharacterDao
) : CharacterRepository {
    override suspend fun getCharacters(
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
                    name = name?.let { "%$it%" },
                    species = species?.let { "%$it%" },
                    status = status,
                    gender = gender,
                    limit = 20,
                    offset = (page - 1) * 20
                )
            }
        } else {
            dao.getCharacters(
                name = name?.let { "%$it%" },
                species = species?.let { "%$it%" },
                status = status,
                gender = gender,
                limit = 20,
                offset = (page - 1) * 20
            )
        }
    }

    override suspend fun getCharacterById(id: Int): CharacterEntity? {

        val cached = dao.getCharacterById(id)
        if (cached != null) return cached

        return try {
            val dto = api.getCharacterById(id)
            val entity = CharacterEntity(
                dto.id,
                dto.name,
                Status.fromApiName(dto.status),
                dto.species,
                Gender.fromApiName(dto.gender),
                dto.image
            )
            dao.insertAll(listOf(entity))
            entity
        } catch (e: Exception) {
            null
        }
    }
}
