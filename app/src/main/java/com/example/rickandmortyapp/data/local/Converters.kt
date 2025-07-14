package com.example.rickandmortyapp.data.local

import androidx.room.TypeConverter
import com.example.rickandmortyapp.data.local.Status
import com.example.rickandmortyapp.data.local.Gender

class Converters {
    @TypeConverter
    fun fromStatus(status: Status?): String? = status?.apiName

    @TypeConverter
    fun toStatus(apiName: String?): Status = Status.fromApiName(apiName)

    @TypeConverter
    fun fromGender(gender: Gender?): String? = gender?.apiName

    @TypeConverter
    fun toGender(apiName: String?): Gender = Gender.fromApiName(apiName)
}
