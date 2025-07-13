package com.example.rickandmortyapp.ui.customdesigne

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PrettySearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text("Поиск персонажей") },
        leadingIcon = {
            Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color(0xFF7B61FF))
        },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .shadow(6.dp, RoundedCornerShape(18.dp)),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color(0xFFF5F7FB),
            focusedBorderColor = Color(0xFF7B61FF),
            unfocusedBorderColor = Color(0xFFE0E4ED),
            cursorColor = Color(0xFF7B61FF)
        ),
        shape = RoundedCornerShape(18.dp)
    )
}
