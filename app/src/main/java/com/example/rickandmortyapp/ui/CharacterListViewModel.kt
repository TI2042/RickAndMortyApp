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

    var currentPage by mutableStateOf(1)
        private set

    var isLastPage by mutableStateOf(false)
        private set

    private var _isLoadingNextPage by mutableStateOf(false)
    val isLoadingNextPage: Boolean get() = _isLoadingNextPage

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
        offline: Boolean = false,
        append: Boolean = false
    ) {
        viewModelScope.launch {
            if (append) _isLoadingNextPage = true else isLoading = true
            error = null
            try {
                val result = repo.getCharacters(
                    page = page,
                    name = searchQuery.ifBlank { null },
                    species = if (selectedSpecies.isNullOrBlank()) null else selectedSpecies,
                    status = if (selectedStatus.isNullOrBlank()) null else selectedStatus,
                    gender = if (selectedGender.isNullOrBlank()) null else selectedGender,
                    offline = offline
                )
                if (append) {
                    characters = characters + result
                } else {
                    characters = result
                }
                // Проверим: если меньше 20 персонажей — последняя страница
                isLastPage = result.size < 20
                currentPage = page
            } catch (e: Exception) {
                error = "Ошибка загрузки: ${e.localizedMessage}"
            }
            isLoading = false
            _isLoadingNextPage  = false
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
    fun loadNextPage() {
        if (!isLastPage && !isLoadingNextPage) {
            loadCharacters(page = currentPage + 1, append = true)
        }
    }

    init {
        loadCharacters()
    }
}