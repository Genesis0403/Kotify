package com.epam.kotify.ui

import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.epam.kotify.model.artists.Artist
import com.epam.kotify.model.tracks.Track
import com.epam.kotify.repository.Resource
import com.epam.kotify.repository.TopsRepository
import com.epam.kotify.ui.artistview.TopArtistsFragment
import com.epam.kotify.ui.tracksview.TopTracksFragment
import com.epam.kotify.utils.Constants
import com.google.android.gms.maps.model.LatLng
import java.util.*
import javax.inject.Inject
import com.epam.kotify.model.domain.Artist as DomainArtist
import com.epam.kotify.model.domain.Track as DomainTrack

class TopsViewModel @Inject constructor(
    private val repository: TopsRepository
) : ViewModel() {


    //TODO move fragments and pages to Dagger
    //TODO save marker position
    val fragments = listOf(
        MapFragment.newInstance(),
        TopArtistsFragment.newInstance(),
        TopTracksFragment.newInstance()
    )

    val pagesNames = listOf(
        "Map",
        "Artists",
        "Tracks"
    )

    private val _country = MutableLiveData<String>()
    val country: LiveData<String> get() = _country

    private val topArtistsResponse: LiveData<Resource<List<Artist>>> =
        Transformations.switchMap(_country) { country ->
            Log.d(TAG, "LOADING TOP")
            repository.loadCountryTopArtists(
                country,
                Constants.TEMP_LIMIT,
                Constants.API_KEY
            )
        }

    val artists: LiveData<Resource<List<DomainArtist>>> =
        Transformations.switchMap(topArtistsResponse) { response ->
            with(response) {
                val list = data?.asSequence()?.map {
                    DomainArtist(
                        it.name,
                        it.image?.last()?.toString() ?: "",
                        it.listeners ?: 0
                    )
                }?.toList() ?: emptyList()
                MutableLiveData<Resource<List<DomainArtist>>>().also { it.value = Resource.success(list) }
            }
        }

    private val topTracksResponse: LiveData<Resource<List<Track>>> =
        Transformations.switchMap(country) { country ->
            repository.loadCountryTopTracks(
                country,
                Constants.TEMP_LIMIT,
                Constants.API_KEY
            )
        }

    val tracks: LiveData<Resource<List<DomainTrack>>> =
        Transformations.switchMap(topTracksResponse) { response ->
            with(response) {
                val list = data?.asSequence()?.map {
                    DomainTrack(
                        it.name,
                        it.artist?.name ?: "None",
                        it.image?.last()?.toString() ?: "",
                        it.duration ?: 0,
                        it.listeners ?: 0
                    )
                }?.toList() ?: emptyList()
                MutableLiveData<Resource<List<DomainTrack>>>().also { it.value = Resource.success(list) }
            }
        }

    fun onMarkerSet(point: LatLng, context: Context?) {
        val geocoder = Geocoder(context, Locale.US)
        try {
            val addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1)
            val name = addresses[0].countryName.toLowerCase()
            _country.value = name.toLowerCase()
            Log.d(TAG, "value set to: ${country.value.toString()}")
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

    private companion object {
        private const val TAG = "TOPS VIEWMODEL"
        private const val LIMIT = 50
    }

}