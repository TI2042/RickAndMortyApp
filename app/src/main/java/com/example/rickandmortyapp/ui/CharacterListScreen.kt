package com.example.rickandmortyapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.ui.Alignment

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
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize().padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(characters, key = { _, it -> it.id }) { index, character ->
                    CharacterCard(character, onClick = { onCharacterClick(character) })

                    // Если пользователь прокрутил к предпоследнему элементу — грузим следующую страницу
                    if (index >= characters.size - 4 && !viewModel.isLastPage && !viewModel.isLoading) {
                        LaunchedEffect(Unit) {
                            viewModel.loadNextPage()
                        }
                    }
                }
                if (viewModel.isLoadingNextPage) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(Modifier.padding(16.dp))
                        }
                    }
                }

            }
        }
    }
}