package com.example.rickandmortyapp

import com.example.rickandmortyapp.data.local.CharacterEntity
import com.example.rickandmortyapp.data.local.Gender
import com.example.rickandmortyapp.data.local.Status
import com.example.rickandmortyapp.repository.CharacterRepository
import com.example.rickandmortyapp.ui.CharacterDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import io.mockk.*

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repo: CharacterRepository
    private lateinit var viewModel: CharacterDetailViewModel

    private val fakeEntity = CharacterEntity(2, "Morty", Status.Alive, "Human", Gender.Male, "img")

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repo = mockk()
        viewModel = CharacterDetailViewModel(repo)
    }

    @Test
    fun `loadCharacter sets character state`() = runTest {
        coEvery { repo.getCharacterById(2) } returns fakeEntity

        viewModel.loadCharacter(2)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(fakeEntity, viewModel.character.value)
        assertFalse(viewModel.isLoading.value)
    }
}
