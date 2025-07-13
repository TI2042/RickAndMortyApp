package com.example.rickandmortyapp.ui.customdesigne

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun PrettyFilterRow(
    status: String?,
    onStatusSelected: (String?) -> Unit,
    species: String?,
    onSpeciesSelected: (String?) -> Unit,
    gender: String?,
    onGenderSelected: (String?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PrettyDropdown(
            label = "Статус",
            options = listOf(null, "alive", "dead", "unknown"),
            selectedOption = status,
            onOptionSelected = onStatusSelected
        )
        PrettyDropdown(
            label = "Пол",
            options = listOf(null, "female", "male", "genderless", "unknown"),
            selectedOption = gender,
            onOptionSelected = onGenderSelected
        )
        OutlinedTextField(
            value = species ?: "",
            onValueChange = { onSpeciesSelected(if (it.isNotEmpty()) it else null) },
            placeholder = { Text("Вид") },
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .shadow(4.dp, RoundedCornerShape(14.dp)),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color(0xFFF5F7FB),
                focusedBorderColor = Color(0xFF7B61FF),
                unfocusedBorderColor = Color(0xFFE0E4ED),
                cursorColor = Color(0xFF7B61FF)
            ),
            shape = RoundedCornerShape(14.dp)
        )
    }
}

@Composable
fun PrettyDropdown(
    label: String,
    options: List<String?>,
    selectedOption: String?,
    onOptionSelected: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val displayText = selectedOption?.capitalize() ?: "Любой"
    OutlinedButton(
        onClick = { expanded = true },
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color(0xFFF5F7FB),
            contentColor = Color(0xFF535871)
        ),
        border = BorderStroke(1.dp, Color(0xFFE0E4ED)),
    ) {
        Text("$label: $displayText")
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null,
            modifier = Modifier.size(18.dp).padding(start = 2.dp)
        )
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

