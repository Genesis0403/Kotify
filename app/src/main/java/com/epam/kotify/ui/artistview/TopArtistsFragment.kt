package com.epam.kotify.ui.artistview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.epam.kotify.KotifyApp
import com.epam.kotify.R
import com.epam.kotify.model.domain.Artist
import com.epam.kotify.repository.Resource
import com.epam.kotify.ui.EmptyRecyclerView
import com.epam.kotify.ui.TopsViewModel
import com.epam.kotify.utils.AppExecutors
import javax.inject.Inject

class TopArtistsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private lateinit var viewModel: TopsViewModel

    private val artistsAdapter = ArtistsAdapter()

    override fun onAttach(context: Context) {
        KotifyApp.component.inject(this)
        super.onAttach(context)
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
        return inflater.inflate(R.layout.recycler_view_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycler = view.findViewById<EmptyRecyclerView>(R.id.topsRecyclerView)

        recycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = artistsAdapter
            emptyView = view.findViewById<TextView>(R.id.emptyView)
        }

        val progress = view.findViewById<ProgressBar>(R.id.progress)
        viewModel.artists.observe(this, Observer<Resource<List<Artist>>> {
            if (it.status.isLoading()) {
                progress.visibility = ProgressBar.VISIBLE
            } else {
                progress.visibility = ProgressBar.GONE
                artistsAdapter.setArtists(it.data!!)
            }
        })
    }

    companion object {
        private const val TAG = "ARTISTS FRAGMENT"
        fun newInstance() = TopArtistsFragment()
    }
}