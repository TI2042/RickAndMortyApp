package com.example.rickandmortyapp.ui


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.data.local.CharacterEntity
import com.example.rickandmortyapp.repository.CharacterRepository
import kotlinx.coroutines.launch

class CharacterListViewModel(
    private val repo: CharacterRepository
) : ViewModel() {
    var characters by mutableStateOf<List<CharacterEntity>>(emptyList())
        private set
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    fun loadCharacters(
        page: Int = 1,
        name: String? = null,
        species: String? = null,
        status: String? = null,
        gender: String? = null,
        offline: Boolean = false
    ) {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                characters = repo.getCharacters(page, name, species, status, gender, offline)
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
    init {
        loadCharacters()
    }
}
