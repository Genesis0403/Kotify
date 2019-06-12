package com.epam.kotify.ui

import android.location.Address
import androidx.lifecycle.Observer
import com.epam.kotify.model.domain.Artist
import com.epam.kotify.model.domain.Track
import com.epam.kotify.repository.Resource
import com.epam.kotify.repository.TopsRepository
import com.epam.kotify.ui.viewmodels.TopsViewModel
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
import java.util.*

@RunWith(JUnitPlatform::class)
class TopsViewModelTest : Spek({

    emulateInstantTaskExecutorRule()

    val repository: TopsRepository = mockk()
    val viewModel = spyk(
        TopsViewModel(
            repository,
            mockk(relaxed = true),
            mockk(relaxed = true)
        )
    )

    val countryObserver: Observer<String> = mockk(relaxed = true)
    val artistsObserver: Observer<Resource<List<Artist>>> = mockk(relaxed = true)
    val tracksObserver: Observer<Resource<List<Track>>> = mockk(relaxed = true)

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
        }

        afterEachTest {
            viewModel.country.removeObserver(countryObserver)
        }

        describe("success observation") {

            beforeEachTest {
                every { viewModel.geocoder.getFromLocation(any(), any(), any()) } returns MutableList(1) {
                    Address(
                        Locale.US
                    ).also { it.countryName = "belarus" }
                }
                viewModel.onMarkerSet(mockk())
            }

            it("should observe") {
                verify { countryObserver.onChanged(anyString()) }
            }
        }
    }
})