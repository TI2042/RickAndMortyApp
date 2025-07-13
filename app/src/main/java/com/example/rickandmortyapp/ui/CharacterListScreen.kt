package com.example.rickandmortyapp.ui
import CharacterCard
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.rickandmortyapp.data.local.CharacterEntity
import com.example.rickandmortyapp.ui.customdesigne.PrettyFilterRow
import com.example.rickandmortyapp.ui.customdesigne.PrettySearchBar
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel,
    onCharacterClick: (CharacterEntity) -> Unit
) {
    val characters = viewModel.characters
    val isLoading = viewModel.isLoading
    val error = viewModel.error

    Column(modifier = Modifier.fillMaxSize()) {
        // Поисковая строка
        PrettySearchBar(
            value = viewModel.searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) }
        )

        // Красивые фильтры
        PrettyFilterRow(
            status = viewModel.selectedStatus,
            onStatusSelected = { viewModel.onStatusSelected(it) },
            species = viewModel.selectedSpecies,
            onSpeciesSelected = { viewModel.onSpeciesSelected(it) },
            gender = viewModel.selectedGender,
            onGenderSelected = { viewModel.onGenderSelected(it) }
        )

        // Для удобства: выводим, сколько найдено
        Text("Найдено: ${characters.size}", modifier = Modifier.padding(8.dp))

        // Основной контент
        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

        Box(modifier = Modifier.fillMaxSize().weight(1f)) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    // Обновление первой страницы с текущими фильтрами
                    viewModel.loadCharacters(page = 1, offline = false)
                }
            ) {
                when {
                    error != null -> {
                        Text(
                            error ?: "",
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    characters.isEmpty() && !isLoading -> {
                        Text(
                            "Ничего не найдено",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    else -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            itemsIndexed(characters, key = { _, it -> it.id }) { index, character ->
                                CharacterCard(character, onClick = { onCharacterClick(character) })

                                // Подгрузка следующей страницы при прокрутке вниз
                                if (index >= characters.size - 4 &&
                                    !viewModel.isLastPage &&
                                    !viewModel.isLoading &&
                                    !viewModel.isLoadingNextPage
                                ) {
                                    LaunchedEffect(Unit) {
                                        viewModel.loadNextPage()
                                    }
                                }
                            }
                            // Индикатор подгрузки страницы (внизу списка)
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
            // Главный индикатор (на весь экран при первичной загрузке)
            if (isLoading && characters.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
