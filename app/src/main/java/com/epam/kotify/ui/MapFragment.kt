package com.epam.kotify.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.epam.kotify.KotifyApp
import com.epam.kotify.R
import com.epam.kotify.repository.Status
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject

class MapFragment : Fragment() {

    companion object {
        private const val TAG = "MAP FRAGMENT"

        fun newInstance() = MapFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: TopsViewModel

    private lateinit var googleMap: GoogleMap
    private lateinit var mapView: MapView

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        KotifyApp.component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)[TopsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.map_fragment, container, false)

        mapView = view.findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)

        try {
            MapsInitializer.initialize(context?.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mapView.getMapAsync { map ->
            googleMap = map

            googleMap.setOnMapClickListener { point ->
                googleMap.clear()
                googleMap.addMarker(MarkerOptions().position(point))
                viewModel.onMarkerSet(point, context)
            }

            loadPrevMarker()
        }
        return view
    }

    private fun loadPrevMarker() {
        viewModel.position?.let {
            if (viewModel.artists.value?.status != Status.LOADING) {
                googleMap.addMarker(MarkerOptions().position(it))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}