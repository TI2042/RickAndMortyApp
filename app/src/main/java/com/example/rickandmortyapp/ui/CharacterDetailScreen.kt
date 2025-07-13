package com.example.rickandmortyapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.rickandmortyapp.data.local.CharacterEntity

@Composable
fun CharacterDetailScreen(
    character: CharacterEntity,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFE0EAFC), Color(0xFFCFDEF3))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(42.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = Color(0xFF535871)
                )
            }

            Spacer(Modifier.height(8.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(160.dp)
                    .shadow(12.dp, CircleShape)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.96f))
            ) {
                Image(
                    painter = rememberAsyncImagePainter(character.image),
                    contentDescription = character.name,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                )
            }
            Spacer(Modifier.height(14.dp))
            Text(
                character.name,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color(0xFF232949),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(8.dp))

            StatusBadgeLarge(character.status)

            Spacer(Modifier.height(18.dp))

            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.98f))
                        .padding(18.dp)
                ) {
                    DetailRow(icon = "\uD83D\uDC68", label = "Вид", value = character.species)
                    DetailRow(icon = if (character.gender.lowercase() == "male") "\u2642" else "\u2640",
                        label = "Пол",
                        value = character.gender.capitalize())
                    DetailRow(icon = "\uD83D\uDCCD", label = "Статус", value = character.status.capitalize())

                }
            }
            Spacer(Modifier.weight(1f))
        }
    }
}

@Composable
fun StatusBadgeLarge(status: String) {
    val color = when (status.lowercase()) {
        "alive" -> Color(0xFF43A047)
        "dead" -> Color(0xFFE53935)
        else -> Color(0xFFBDBDBD)
    }
    val emoji = when (status.lowercase()) {
        "alive" -> "🟢"
        "dead" -> "🔴"
        else -> "⚪"
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .clip(RoundedCornerShape(50))
            .background(color.copy(alpha = 0.16f))
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(emoji, fontSize = 18.sp)
            Spacer(Modifier.width(8.dp))
            Text(
                text = status.capitalize(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = color
            )
        }
    }
}

@Composable
fun DetailRow(icon: String, label: String, value: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 7.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = icon,
            fontSize = 18.sp,
            modifier = Modifier.width(28.dp)
        )
        Text(
            text = "$label:",
            color = Color(0xFF9099B7),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.width(75.dp)
        )
        Text(
            text = value,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF232949),
            fontSize = 16.sp
        )
    }
}

