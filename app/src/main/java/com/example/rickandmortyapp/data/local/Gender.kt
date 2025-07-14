package com.example.rickandmortyapp.data.local

enum class Gender(val apiName: String) {
    Female("female"),
    Male("male"),
    Genderless("genderless"),
    Unknown("unknown");

    companion object {
        fun fromApiName(name: String?): Gender =
            values().find { it.apiName.equals(name, ignoreCase = true) } ?: Unknown
    }
}
