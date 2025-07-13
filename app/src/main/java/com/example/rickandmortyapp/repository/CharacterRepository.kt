package com.example.rickandmortyapp.repository

import android.util.Log
import com.example.rickandmortyapp.data.local.CharacterDao
import com.example.rickandmortyapp.data.local.CharacterEntity
import com.example.rickandmortyapp.data.remote.RickAndMortyApi

class CharacterRepository(
    private val api: RickAndMortyApi,
    private val dao: CharacterDao
) {
//    suspend fun getCharacters( page: Int,
//        name: String?,
//        species: String?,
//       status: String?,
//        gender: String?,
//        offline: Boolean) : List<CharacterEntity> {
//        val response = api.getCharacters()
//        return response.results.map { dto ->
//            CharacterEntity(dto.id, dto.name, dto.status, dto.species, dto.gender, dto.image)
//        }
//    }
   suspend fun getCharacters(
        page: Int,
        name: String?,
        species: String?,
        status: String?,
        gender: String?,
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
                // Кешируем
                val entities = response.results.map { dto ->
                    CharacterEntity(dto.id, dto.name, dto.status, dto.species, dto.gender, dto.image)
                }
                dao.insertAll(entities)
                entities
            } catch (e: Exception) {
                e.printStackTrace()
                // Если ошибка — пробуем из базы
                dao.getCharacters(
                    name = "%${name ?: ""}%",
                    species = "%${species ?: ""}%",
                    status = "%${status ?: ""}%",
                    gender = "%${gender ?: ""}%"
                )
            }
        } else {
            dao.getCharacters(
                name = "%${name ?: ""}%",
                species = "%${species ?: ""}%",
                status = "%${status ?: ""}%",
                gender = "%${gender ?: ""}%"
            )
        }

   }
}
