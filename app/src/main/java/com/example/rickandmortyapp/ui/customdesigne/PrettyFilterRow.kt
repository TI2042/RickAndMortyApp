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
import androidx.compose.ui.res.stringResource
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.data.local.Gender
import com.example.rickandmortyapp.data.local.Status
import com.example.rickandmortyapp.ui.utils.displayGender
import com.example.rickandmortyapp.ui.utils.displayStatus

@Composable
fun PrettyFilterRow(
    status: Status?,
    onStatusSelected: (Status?) -> Unit,
    species: String?,
    onSpeciesSelected: (String?) -> Unit,
    gender: Gender?,
    onGenderSelected: (Gender?) -> Unit,
    statusLabel: String,
    genderLabel: String,
    speciesLabel: String,
    anyLabel: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PrettyDropdown(
            label = statusLabel,
            options = listOf(null) + Status.values().toList(),
            selectedOption = status,
            onOptionSelected = onStatusSelected,
            anyLabel = anyLabel,
            valueToString = { opt -> opt?.let { displayStatus(it) } ?: anyLabel }
        )

        PrettyDropdown(
            label = genderLabel,
            options = listOf(null) + Gender.values().toList(),
            selectedOption = gender,
            onOptionSelected = onGenderSelected,
            anyLabel = anyLabel,
            valueToString = { opt -> opt?.let { displayGender(it) } ?: anyLabel }
        )
        OutlinedTextField(
            value = species ?: "",
            onValueChange = { onSpeciesSelected(it.ifEmpty { null }) },
            placeholder = { Text(speciesLabel) },
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 2.dp)
        )
    }
}

@Composable
fun <T> PrettyDropdown(
    label: String,
    options: List<T?>,
    selectedOption: T?,
    onOptionSelected: (T?) -> Unit,
    anyLabel: String,
    valueToString: @Composable (T?) -> String
) {
    var expanded by remember { mutableStateOf(false) }
    val displayText = valueToString(selectedOption)
    OutlinedButton(
        onClick = { expanded = true },
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.height(56.dp),
        border = ButtonDefaults.outlinedBorder
    ) {
        Text("$label: $displayText")
        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = null,
            modifier = Modifier.padding(start = 2.dp)
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
                    Text(valueToString(option))
                }
            }
        }
    }
}



