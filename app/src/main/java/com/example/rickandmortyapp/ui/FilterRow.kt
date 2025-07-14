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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.rickandmortyapp.R

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
            label = stringResource(R.string.filter_status),
            options = listOf(null,
                stringResource(R.string.alive),
                stringResource(R.string.dead),
                stringResource(R.string.unknown)),
            selectedOption = status,
            onOptionSelected = onStatusSelected
        )
        Spacer(Modifier.width(8.dp))
        DropdownFilter(
            label = stringResource(R.string.filter_gender),
            options = listOf(null,
                stringResource(R.string.female),
                stringResource(R.string.male),
                stringResource(R.string.genderless),
                stringResource(R.string.unknown)),
            selectedOption = gender,
            onOptionSelected = onGenderSelected
        )
        
        Spacer(Modifier.width(8.dp))
        OutlinedTextField(
            value = species ?: "",
            onValueChange = {
                onSpeciesSelected(if (it.isNotEmpty()) it else null)
            },
            label = { Text( stringResource(R.string.filter_species)) },
            modifier = Modifier.weight(1f)
        )
    }
}


@Composable
fun DropdownFilter(
    label: String,
    options: List<String?>,
    selectedOption: String?,
    onOptionSelected: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val displayText = selectedOption?.capitalize() ?: stringResource(R.string.filter_any)
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
                    Text(option?.capitalize() ?: stringResource(R.string.filter_any))
                }
            }
        }
    }
}
