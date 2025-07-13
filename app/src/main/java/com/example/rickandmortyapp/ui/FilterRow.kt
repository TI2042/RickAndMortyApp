package com.example.rickandmortyapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp

@Composable
fun FilterRow(
    status: String?,
    onStatusSelected: (String?) -> Unit,
    species: String?,
    onSpeciesSelected: (String?) -> Unit,
    gender: String?,
    onGenderSelected: (String?) -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 4.dp)) {

        DropdownFilter(
            label = "Статус",
            options = listOf(null, "alive", "dead", "unknown"),
            selectedOption = status,
            onOptionSelected = onStatusSelected
        )
        Spacer(Modifier.width(8.dp))
        DropdownFilter(
            label = "Пол",
            options = listOf(null, "female", "male", "genderless", "unknown"),
            selectedOption = gender,
            onOptionSelected = onGenderSelected
        )
        // Для вида можешь сделать как простое поле ввода или список популярных видов:
        Spacer(Modifier.width(8.dp))
        OutlinedTextField(
            value = species ?: "",
            onValueChange = {
                onSpeciesSelected(if (it.isNotEmpty()) it else null)
            },
            label = { Text("Вид") },
            modifier = Modifier.weight(1f)
        )
    }
}

// Универсальный DropDown
@Composable
fun DropdownFilter(
    label: String,
    options: List<String?>,
    selectedOption: String?,
    onOptionSelected: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val displayText = selectedOption?.capitalize() ?: "Любой"
    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text("$label: $displayText")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    onOptionSelected(option)
                    expanded = false
                }) {
                    Text(option?.capitalize() ?: "Любой")
                }
            }
        }
    }
}
