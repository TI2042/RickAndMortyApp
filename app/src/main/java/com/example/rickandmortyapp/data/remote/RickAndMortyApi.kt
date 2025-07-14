package com.example.rickandmortyapp.data.remote

import com.example.rickandmortyapp.data.local.Gender
import com.example.rickandmortyapp.data.local.Status
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int = 1,
        @Query("name") name: String? = null,
        @Query("status") status: Status? = null,
        @Query("species") species: String? = null,
        @Query("gender") gender: Gender? = null
    ): CharacterResponse
}

data class CharacterResponse(
    val results: List<CharacterDto>,
    val info: PageInfo
)

data class PageInfo(val pages: Int, val next: String?, val prev: String?)
