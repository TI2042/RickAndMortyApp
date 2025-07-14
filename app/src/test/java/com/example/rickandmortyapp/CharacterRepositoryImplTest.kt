package com.example.rickandmortyapp

import com.example.rickandmortyapp.data.CharacterRepositoryImpl
import com.example.rickandmortyapp.data.local.CharacterDao
import com.example.rickandmortyapp.data.local.CharacterEntity
import com.example.rickandmortyapp.data.local.Gender
import com.example.rickandmortyapp.data.local.Status
import com.example.rickandmortyapp.data.remote.CharacterDto
import com.example.rickandmortyapp.data.remote.RickAndMortyApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import io.mockk.*

class CharacterRepositoryImplTest {

    private lateinit var api: RickAndMortyApi
    private lateinit var dao: CharacterDao
    private lateinit var repo: CharacterRepositoryImpl

    private val fakeEntity = CharacterEntity(1, "Rick", Status.Alive, "Human", Gender.Male, "img")
    private val fakeDto = CharacterDto(1, "Rick", "Alive", "Human", "Male", "img")

    @Before
    fun setUp() {
        api = mockk()
        dao = mockk()
        repo = CharacterRepositoryImpl(api, dao)
    }

    @Test
    fun `returns character from DB if present`() = runBlocking {
        coEvery { dao.getCharacterById(1) } returns fakeEntity

        val result = repo.getCharacterById(1)

        assertEquals(fakeEntity, result)
        coVerify(exactly = 0) { api.getCharacterById(any()) }
    }

    @Test
    fun `fetches from API and saves if not in DB`() = runBlocking {
        coEvery { dao.getCharacterById(1) } returns null
        coEvery { api.getCharacterById(1) } returns fakeDto
        coEvery { dao.insertAll(any()) } just Runs

        val result = repo.getCharacterById(1)

        assertNotNull(result)
        assertEquals("Rick", result?.name)
        coVerify { api.getCharacterById(1) }
        coVerify { dao.insertAll(match { it[0].name == "Rick" }) }
    }
}
