package com.example.rickandmortyapp.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.data.local.Gender
import com.example.rickandmortyapp.data.local.Status

@Composable
fun displayStatus(status: Status): String = when (status) {
    Status.Alive -> stringResource(R.string.alive)
    Status.Dead -> stringResource(R.string.dead)
    Status.Unknown -> stringResource(R.string.unknown)
}

@Composable
fun displayGender(gender: Gender): String = when (gender) {
    Gender.Female -> stringResource(R.string.female)
    Gender.Male -> stringResource(R.string.male)
    Gender.Genderless -> stringResource(R.string.genderless)
    Gender.Unknown -> stringResource(R.string.unknown)
}