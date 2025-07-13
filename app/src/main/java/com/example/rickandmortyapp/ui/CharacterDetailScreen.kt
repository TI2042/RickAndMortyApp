package com.example.rickandmortyapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.rickandmortyapp.data.local.CharacterEntity

@Composable
fun CharacterDetailScreen(character: CharacterEntity, onBack: () -> Unit) {
    Column {
        IconButton(onClick = { onBack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Image(
            painter = rememberAsyncImagePainter(character.image),
            contentDescription = character.name,
            modifier = Modifier.size(220.dp)
        )
        Text(character.name, style = MaterialTheme.typography.h5)
        Text("Status: ${character.status}")
        Text("Species: ${character.species}")
        Text("Gender: ${character.gender}")
        // …другие поля по желанию
    }
}
