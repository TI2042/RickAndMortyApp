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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.rickandmortyapp.R

@Composable
fun PrettySearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(stringResource(R.string.search_placeholder),) },
        leadingIcon = {
            Icon(Icons.Filled.Search, contentDescription = "Search", tint = com.example.rickandmortyapp.ui.theme.CustomFocusedBorderColor)
        },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .shadow(6.dp, RoundedCornerShape(18.dp)),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = com.example.rickandmortyapp.ui.theme.CustomBackgroundColor,
            focusedBorderColor = com.example.rickandmortyapp.ui.theme.CustomFocusedBorderColor,
            unfocusedBorderColor = com.example.rickandmortyapp.ui.theme.CustomUnfocusedBorderColor,
            cursorColor = com.example.rickandmortyapp.ui.theme.CustomFocusedBorderColor
        ),
        shape = RoundedCornerShape(18.dp)
    )
}
