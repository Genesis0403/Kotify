package com.epam.kotify.db

import androidx.room.TypeConverter
import com.epam.kotify.model.Image
import com.epam.kotify.model.artists.Artist
import com.google.gson.Gson

class TypesConverter {

    @TypeConverter
    fun fromImages(images: List<Image>?): String {
        return Gson().toJson(images)
    }

    @TypeConverter
    fun toImages(json: String): List<Image>? {
        return (Gson().fromJson(json, Array<Image>::class.java))?.toList()
    }

    @TypeConverter
    fun fromArtist(artist: Artist): String {
        return Gson().toJson(artist)
    }

    @TypeConverter
    fun toArtist(json: String): Artist {
        return Gson().fromJson(json, Artist::class.java)
    }
}