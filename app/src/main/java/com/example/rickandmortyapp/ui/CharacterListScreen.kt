package com.example.rickandmortyapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.material.CircularProgressIndicator
import com.example.rickandmortyapp.data.local.CharacterEntity

@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel,
    onCharacterClick: (CharacterEntity) -> Unit
) {
    val characters = viewModel.characters
    val isLoading = viewModel.isLoading
    val error = viewModel.error

    Column {
        // ... тут может быть поиск, фильтр и т.п. ...

        if (isLoading) {
            CircularProgressIndicator()
        } else if (error != null) {
            Text(error)
        } else if (characters.isEmpty()) {
            Text("Ничего не найдено")
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(characters) { character ->
                    CharacterCard(character, onClick = { onCharacterClick(character) })
                }
            }
        }
    }
}
