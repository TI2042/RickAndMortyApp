package com.example.rickandmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.rickandmortyapp.data.CharacterRepositoryImpl
import com.example.rickandmortyapp.data.local.AppDatabase
import com.example.rickandmortyapp.data.remote.RetrofitInstance
import com.example.rickandmortyapp.repository.CharacterRepository
import com.example.rickandmortyapp.ui.CharacterListScreen
import com.example.rickandmortyapp.ui.CharacterDetailScreen
import com.example.rickandmortyapp.ui.CharacterDetailViewModel
import com.example.rickandmortyapp.ui.CharacterListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "rickandmorty-db"
        ).build()

        val api = RetrofitInstance.api
        val repository: CharacterRepository = CharacterRepositoryImpl(api, db.characterDao())
        val viewModel = CharacterListViewModel(repository)

        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                NavHost(navController, startDestination = "list") {
                    composable("list") {
                        
                        val viewModel: CharacterListViewModel = viewModel(
                            factory = CharacterListViewModel.Factory(repository)
                        )
                        CharacterListScreen(
                            viewModel = viewModel,
                            onCharacterClick = { character ->
                                navController.navigate("detail/${character.id}")
                            }
                        )
                    }

                    composable(
                        "detail/{characterId}",
                        arguments = listOf(navArgument("characterId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0
                        
                        val viewModel: CharacterListViewModel = viewModel(
                            factory = CharacterListViewModel.Factory(repository)
                        )
                        
                        val character = viewModel.characters.firstOrNull { it.id == characterId }
                        val detailViewModel = remember { CharacterDetailViewModel(repository) }
                        if (character != null) {
                            CharacterDetailScreen(
                                characterId = characterId,
                                viewModel = detailViewModel,
                                onBack = { navController.popBackStack() }
                            )
                        } else {
                            Text(stringResource(R.string.nothing_found), modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            }
        }
    }
}
