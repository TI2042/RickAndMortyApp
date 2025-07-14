package com.example.rickandmortyapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.repository.CharacterRepository
import com.example.rickandmortyapp.data.local.CharacterEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    private val repo: CharacterRepository
) : ViewModel() {

    private val _character = MutableStateFlow<CharacterEntity?>(null)
    val character: StateFlow<CharacterEntity?> = _character

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadCharacter(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repo.getCharacterById(id)
            _character.value = result
            _isLoading.value = false
        }
    }

}
