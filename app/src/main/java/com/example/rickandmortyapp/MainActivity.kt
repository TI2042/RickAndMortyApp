package com.example.rickandmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
        setContent {
            val context = LocalContext.current
            val db = remember {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "characters-db"
                ).build()
            }
            val api = remember { RetrofitInstance.api }
            val repository: CharacterRepository = remember { CharacterRepositoryImpl(api, db.characterDao()) }

            val listViewModel = remember { CharacterListViewModel(repository) }
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "list") {
                composable("list") {
                    CharacterListScreen(
                        viewModel = listViewModel,
                        onCharacterClick = { character ->
                            navController.navigate("details/${character.id}")
                        }
                    )
                }
                composable(
                    route = "details/{characterId}",
                    arguments = listOf(navArgument("characterId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0
                    val detailViewModel = remember { CharacterDetailViewModel(repository) }
                    CharacterDetailScreen(
                        characterId = characterId,
                        viewModel = detailViewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
