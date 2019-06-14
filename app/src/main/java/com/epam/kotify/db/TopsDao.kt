package com.epam.kotify.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.epam.kotify.model.domain.Artist
import com.epam.kotify.model.domain.Track

/**
 * DB requests.
 *
 * @author Vlad Korotkevich
 */

@Dao
interface TopsDao {

    @Query("SELECT * FROM artist")
    fun getLovedArtists(): LiveData<List<Artist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLovedArtist(artist: Artist)

    @Query("DELETE FROM artist WHERE name = :name")
    fun deleteLovedArtist(name: String)

    @Query("SELECT * FROM track")
    fun getLovedTracks(): LiveData<List<Track>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLovedTrack(track: Track)

    @Query("DELETE FROM track WHERE title = :title")
    fun deleteLovedTrack(title: String)
}