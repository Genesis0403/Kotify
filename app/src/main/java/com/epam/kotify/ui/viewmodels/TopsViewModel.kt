package com.epam.kotify.ui.viewmodels

import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.epam.kotify.R
import com.epam.kotify.model.artists.Artist
import com.epam.kotify.model.tracks.Track
import com.epam.kotify.repository.Resource
import com.epam.kotify.repository.TopsRepository
import com.epam.kotify.utils.AppContextProvider
import com.epam.kotify.utils.AppExecutors
import com.epam.kotify.utils.Mappers
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject
import com.epam.kotify.model.domain.Artist as DomainArtist
import com.epam.kotify.model.domain.Track as DomainTrack

/**
 * ViewModel which is used as connection between View and Model.
 * Makes request to API via repository and gets [LiveData] from it.
 *
 * @see TopsRepository
 *
 * @author Vlad Korotkevich
 */

class TopsViewModel @Inject constructor(
    private val repository: TopsRepository,
    private val contextProvider: AppContextProvider,
    private val executors: AppExecutors,
    private val mappers: Mappers,
    val geocoder: Geocoder
) : ViewModel() {

    private companion object {
        private const val TAG = "TOPS VIEWMODEL"
        private const val LIMIT = 50
        private const val NO_STRING_ERROR = "String not found in string resources."
    }

    private var _position: LatLng? = null
    val position: LatLng? get() = _position

    private val _country = MutableLiveData<String>()
    val country: LiveData<String> get() = _country

    private val topArtistsResponse: LiveData<Resource<List<Artist>>> =
        Transformations.switchMap(_country) { country ->
            repository.loadCountryTopArtists(
                country,
                LIMIT,
                contextProvider.getString(R.string.api_key) ?: throw IllegalStateException(NO_STRING_ERROR)
            )
        }

    val artists: LiveData<Resource<List<DomainArtist>>> =
        Transformations.switchMap(topArtistsResponse) { response ->
            val mapped = mappers.mapToArtists(response.data ?: emptyList())
            MutableLiveData<Resource<List<DomainArtist>>>().also {
                it.value = Resource(response.status, mapped, response.message)
            }
        }

    private val topTracksResponse: LiveData<Resource<List<Track>>> =
        Transformations.switchMap(country) { country ->
            repository.loadCountryTopTracks(
                country,
                LIMIT,
                contextProvider.getString(R.string.api_key) ?: throw IllegalStateException(NO_STRING_ERROR)
            )
        }

    val tracks: LiveData<Resource<List<DomainTrack>>> =
        Transformations.switchMap(topTracksResponse) { response ->
            with(response) {
                val list = mappers.mapToTracks(data ?: emptyList())
                MutableLiveData<Resource<List<DomainTrack>>>().also {
                    it.value = Resource(response.status, list, response.message)
                }
            }
        }

    val lovedArtists: LiveData<List<DomainArtist>> = repository.getLovedArtists()

    val lovedTracks: LiveData<List<DomainTrack>> = repository.getLovedTracks()

    fun onMarkerSet(point: LatLng) {
        println("$point")
        executors.diskIO().execute {
            _position = point
            try {
                val addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1)
                val name = addresses[0].countryName.toLowerCase()
                _country.postValue(name)
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
    }

    fun insertLovedArtist(artist: DomainArtist) {
        if (!artist.isLoved) {
            artist.isLoved = true
            repository.insertLovedArtist(artist)
        }
    }

    fun insertLovedTrack(track: DomainTrack) {
        if (!track.isLoved) {
            track.isLoved = true
            repository.insertLovedTrack(track)
        }
    }

    fun removeLovedArtist(artist: DomainArtist) {
        repository.deleteLovedArtist(artist.name)
        artist.isLoved = false
    }

    fun removeLovedTrack(track: DomainTrack) {
        repository.deleteLovedTrack(track.title)
        track.isLoved = false
    }
}