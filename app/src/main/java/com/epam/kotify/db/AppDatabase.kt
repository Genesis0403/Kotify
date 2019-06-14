package com.epam.kotify.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.epam.kotify.model.domain.Artist
import com.epam.kotify.model.domain.Track

/**
 * Database with Room which contains [LiveData] of API responses.
 *
 * @author Vlad Korotkevich
 */

@Database(entities = [Artist::class, Track::class], version = 6)
@TypeConverters(TypesConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun topsDao(): TopsDao
}