package com.example.rickandmortyapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int = 1,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("gender") gender: String? = null
    ): CharacterResponse
}

data class CharacterResponse(
    val results: List<CharacterDto>,
    val info: PageInfo
)

data class PageInfo(val pages: Int, val next: String?, val prev: String?)
