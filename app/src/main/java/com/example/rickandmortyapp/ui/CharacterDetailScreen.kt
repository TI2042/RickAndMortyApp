package com.example.rickandmortyapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.data.local.CharacterEntity
import com.example.rickandmortyapp.ui.utils.displayGender
import com.example.rickandmortyapp.ui.utils.displayStatus
import com.example.rickandmortyapp.data.local.Status

@Composable
fun CharacterDetailScreen(
    characterId: Int,
    viewModel: CharacterDetailViewModel,
    onBack: () -> Unit
) {
    val character by viewModel.character.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Загружаем персонажа при открытии экрана
    LaunchedEffect(characterId) {
        viewModel.loadCharacter(characterId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            character == null -> {
                Text(
                    text = stringResource(R.string.nothing_found),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                CharacterDetailContent(character!!, onBack)
            }
        }
    }
}

@Composable
fun CharacterDetailContent(
    character: CharacterEntity,
    onBack: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Text(stringResource(R.string.back))
        }

        Image(
            painter = rememberAsyncImagePainter(character.image),
            contentDescription = character.name,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = character.name,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(16.dp))

        StatusRow(character)

        Spacer(Modifier.height(8.dp))

        InfoRow(label = stringResource(R.string.filter_species), value = character.species)
        InfoRow(label = stringResource(R.string.filter_gender), value = displayGender(character.gender))
    }
}

@Composable
fun StatusRow(character: CharacterEntity) {
    val color = when (character.status) {
        Status.Alive -> MaterialTheme.colors.primary
        Status.Dead -> MaterialTheme.colors.error
        Status.Unknown -> MaterialTheme.colors.onSurface.copy(alpha = 0.4f)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = displayStatus(character.status),
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(Modifier.padding(vertical = 3.dp)) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.body2
        )
    }
}

