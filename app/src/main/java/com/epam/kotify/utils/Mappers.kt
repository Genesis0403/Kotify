package com.epam.kotify.utils

import com.epam.kotify.model.artists.Artist
import com.epam.kotify.model.tracks.Track
import javax.inject.Inject
import javax.inject.Singleton

import com.epam.kotify.model.domain.Artist as DomainArtist
import com.epam.kotify.model.domain.Track as DomainTrack

@Singleton
class Mappers @Inject constructor(
    private val executors: AppExecutors
) {
    fun mapToArtists(response: List<Artist>): List<DomainArtist> {
        return response.map {
            DomainArtist(
                it.name,
                it.image?.last()?.toString() ?: "",
                it.listeners ?: 0
            )
        }.toList()
    }

    fun mapToTracks(response: List<Track>): List<DomainTrack> {
        return response.map {
            DomainTrack(
                it.name,
                it.artist?.name ?: "None",
                it.image?.last()?.toString() ?: "",
                it.duration ?: 0,
                it.listeners ?: 0
            )
        }.toList()
    }
}