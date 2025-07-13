package com.example.rickandmortyapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rickandmortyapp.data.local.CharacterEntity

@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel,
    onCharacterClick: (CharacterEntity) -> Unit
) {
    val characters = viewModel.characters
    val isLoading = viewModel.isLoading
    val error = viewModel.error
    val searchQuery = viewModel.searchQuery

    Column {
        // Поисковая строка
        OutlinedTextField(
            value = viewModel.searchQuery,
            onValueChange = {
                viewModel.onSearchQueryChange(it)
            },
            label = { Text("Поиск персонажей") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        // Фильтры (статус, вид, пол)
        FilterRow(
            status = viewModel.selectedStatus,
            onStatusSelected = {
                viewModel.onStatusSelected(it)
                viewModel.loadCharacters()
            },
            species = viewModel.selectedSpecies,
            onSpeciesSelected = {
                viewModel.onSpeciesSelected(it)
                viewModel.loadCharacters()
            },
            gender = viewModel.selectedGender,
            onGenderSelected = {
                viewModel.onGenderSelected(it)
                viewModel.loadCharacters()
            }
        )


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