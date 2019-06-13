package com.epam.kotify.ui

import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.epam.kotify.model.artists.Artist
import com.epam.kotify.model.tracks.Track
import com.epam.kotify.repository.Resource
import com.epam.kotify.repository.TopsRepository
import com.epam.kotify.ui.viewmodels.TopsViewModel
import com.epam.kotify.utils.AppContextProvider
import com.epam.kotify.utils.AppExecutors
import com.epam.kotify.utils.Mappers
import com.google.android.gms.maps.model.LatLng
import io.mockk.*
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import java.lang.NullPointerException
import com.epam.kotify.model.domain.Artist as DomainArtist
import com.epam.kotify.model.domain.Track as DomainTrack

@RunWith(JUnitPlatform::class)
class TopsViewModelTest : Spek({

    emulateInstantTaskExecutorRule()

    val repository: TopsRepository = mockk(relaxed = true)
    val geocoder: Geocoder = mockk(relaxed = true)
    val executors = AppExecutors()
    val contextProvider: AppContextProvider = mockk(relaxed = true)
    val mappers: Mappers = mockk(relaxed = true)
    val address: Address = mockk(relaxed = true)
    val latLng: LatLng = mockk(relaxed = true)

    val viewModel =
        TopsViewModel(
            repository,
            contextProvider,
            executors,
            mappers,
            geocoder
        )

    val countryObserver: Observer<String> = mockk(relaxed = true)
    val artistsObserver: Observer<Resource<List<DomainArtist>>> = mockk(relaxed = true)
    val tracksObserver: Observer<Resource<List<DomainTrack>>> = mockk(relaxed = true)

    given("data init: success") {

        describe("LiveData's not null") {

            it("should not be null") {
                assertThat(viewModel.artists, notNullValue())
                assertThat(viewModel.tracks, notNullValue())
                assertThat(viewModel.country, notNullValue())
            }
        }
    }

    given("data loading: success") {

        beforeEachTest {
            clearMocks(repository)
            viewModel.country.observeForever(countryObserver)
            viewModel.artists.observeForever(artistsObserver)
            viewModel.tracks.observeForever(tracksObserver)
        }

        afterEachTest {
            viewModel.country.removeObserver(countryObserver)
            viewModel.artists.removeObserver(artistsObserver)
            viewModel.tracks.removeObserver(tracksObserver)
        }

        describe("success observation") {

            beforeEachTest {
                every { geocoder.getFromLocation(any(), any(), any()) } returns listOf(address)

                every {
                    repository.loadCountryTopArtists(anyString(), any(), anyString())
                } returns MutableLiveData<Resource<List<Artist>>>().also {
                    it.value = Resource(mockk(), mockk(), null)
                }

                every { mappers.mapToArtists(any()) } returns mockk()

                every {
                    repository.loadCountryTopTracks(anyString(), any(), anyString())
                } returns MutableLiveData<Resource<List<Track>>>().also {
                    it.value = Resource(mockk(), mockk(), null)
                }

                every { mappers.mapToTracks(any()) } returns mockk()

                viewModel.onMarkerSet(latLng)
            }

            it("should observe") {
                verify { countryObserver.onChanged(any()) }
                verify { artistsObserver.onChanged(any()) }
                verify { countryObserver.onChanged(any()) }
            }
        }

        describe("failed observation") {

            beforeEachTest {
                clearAllMocks()
                every { geocoder.getFromLocation(any(), any(), any()) } throws NullPointerException()
                viewModel.onMarkerSet(latLng)
            }

            it("should not observe") {
                verify(inverse = true) { countryObserver.onChanged(any()) }
                verify(inverse = true) { artistsObserver.onChanged(any()) }
                verify(inverse = true) { countryObserver.onChanged(any()) }
            }

        }

        describe("ordered observation") {

            beforeEachTest {
                clearAllMocks()
                every { geocoder.getFromLocation(any(), any(), any()) } returns listOf(address)
                viewModel.onMarkerSet(latLng)
                viewModel.onMarkerSet(latLng)
            }

            it("should be ordered") {
                verifyAll {
                    repository.loadCountryTopArtists(any(), any(), anyString())
                    repository.loadCountryTopArtists(any(), any(), anyString())
                }
            }
        }
    }
})