package com.epam.kotify.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.epam.kotify.model.artists.Artist
import com.epam.kotify.model.tracks.Track

/**
 * Database with Room which contains [LiveData] of API responses.
 *
 * @author Vlad Korotkevich
 */

@Database(entities = [Artist::class, Track::class], version = 4)
@TypeConverters(TypesConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun topsDao(): TopsDao
}