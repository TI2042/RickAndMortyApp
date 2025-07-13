package com.example.rickandmortyapp.ui


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.data.local.CharacterEntity
import com.example.rickandmortyapp.repository.CharacterRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CharacterListViewModel(
    private val repo: CharacterRepository
) : ViewModel() {
    var characters by mutableStateOf<List<CharacterEntity>>(emptyList())
        private set
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    var searchQuery by mutableStateOf("")
        private set

    private var searchJob: Job? = null
    private var filterJob: Job? = null

    fun onSearchQueryChange(query: String) {
        searchQuery = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            loadCharacters()
        }
    }

    fun onStatusSelected(status: String?) {
        selectedStatus = status
        debounceFilters()
    }

    fun onSpeciesSelected(species: String?) {
        selectedSpecies = species
        debounceFilters()
    }

    fun onGenderSelected(gender: String?) {
        selectedGender = gender
        debounceFilters()
    }

    private fun debounceFilters() {
        filterJob?.cancel()
        filterJob = viewModelScope.launch {
            delay(500)
            loadCharacters()
        }
    }
    fun loadCharacters(
        page: Int = 1,
        offline: Boolean = false
    ) {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                characters = repo.getCharacters(
                    page = page,
                    name = if (searchQuery.isNotEmpty()) searchQuery else null,
                    species = selectedSpecies,
                    status = selectedStatus,
                    gender = selectedGender,
                    offline = offline
                )
            } catch (e: Exception) {
                error = "Ошибка загрузки"
            }
            isLoading = false
        }
    }
    class Factory(private val repo: CharacterRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CharacterListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CharacterListViewModel(repo) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    var selectedStatus by mutableStateOf<String?>(null)
        private set

    var selectedSpecies by mutableStateOf<String?>(null)
        private set

    var selectedGender by mutableStateOf<String?>(null)
        private set

    init {
        loadCharacters()
    }
}