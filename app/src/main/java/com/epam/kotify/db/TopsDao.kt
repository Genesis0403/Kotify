package com.epam.kotify.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.epam.kotify.model.artists.Artist
import com.epam.kotify.model.tracks.Track

/**
 * DB requests.
 *
 * @author Vlad Korotkevich
 */

@Dao
interface TopsDao {

    @Query("SELECT * FROM artist")
    fun getTopArtists(): LiveData<List<Artist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArtists(artist: List<Artist>): Array<Long>

    @Query("SELECT * FROM track")
    fun getTopTracks(): LiveData<List<Track>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTracks(tracks: List<Track>): Array<Long>

    @Query("DELETE FROM artist")
    fun clearArtists()

    @Query("DELETE FROM track")
    fun clearTracks()
}