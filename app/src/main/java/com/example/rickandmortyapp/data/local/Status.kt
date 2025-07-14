package com.example.rickandmortyapp.data.local

enum class Status(val apiName: String) {
    Alive("alive"),
    Dead("dead"),
    Unknown("unknown");

    companion object {
        fun fromApiName(name: String?): Status =
            values().find { it.apiName.equals(name, ignoreCase = true) } ?: Unknown
    }
}
