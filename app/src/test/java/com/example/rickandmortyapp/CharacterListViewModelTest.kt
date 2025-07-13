package com.example.rickandmortyapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.rickandmortyapp.data.local.CharacterEntity
import com.example.rickandmortyapp.repository.CharacterRepository
import com.example.rickandmortyapp.ui.CharacterListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterListViewModelTest {

    // Тестовый диспетчер для корутин
    private val testDispatcher = StandardTestDispatcher()

    // Мокаем репозиторий
    private lateinit var repository: CharacterRepository
    private lateinit var viewModel: CharacterListViewModel

    // JUnit Rule для LiveData/Compose
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = CharacterListViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadCharacters loads data correctly`() = runTest {
        val testCharacters = listOf(
            CharacterEntity(1, "Rick", "alive", "Human", "male", "url"),
            CharacterEntity(2, "Morty", "alive", "Human", "male", "url")
        )
        coEvery {
            repository.getCharacters(
                page = 1,
                name = null,
                species = null,
                status = null,
                gender = null,
                offline = false
            )
        } returns testCharacters

        viewModel.loadCharacters()
        advanceUntilIdle()

        assertEquals(2, viewModel.characters.size)
        assertEquals("Rick", viewModel.characters[0].name)
    }

    @Test
    fun `searchQuery triggers filtering`() = runTest {
        val filtered = listOf(CharacterEntity(2, "Morty", "alive", "Human", "male", "url"))
        coEvery { repository.getCharacters(any(), eq("Morty"), any(), any(), any(), any()) } returns filtered

        viewModel.onSearchQueryChange("Morty")
        advanceTimeBy(600) // для debounce
        advanceUntilIdle()

        assertEquals(1, viewModel.characters.size)
        assertEquals("Morty", viewModel.characters[0].name)
    }

    @Test
    fun `error sets error message`() = runTest {
        coEvery { repository.getCharacters(any(), any(), any(), any(), any(), any()) } throws Exception("Network error")

        viewModel.loadCharacters()
        advanceUntilIdle()

        assertTrue(viewModel.error?.contains("Ошибка загрузки") == true)
        assertTrue(viewModel.characters.isEmpty())
    }
}