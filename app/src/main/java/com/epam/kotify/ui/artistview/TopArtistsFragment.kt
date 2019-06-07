package com.epam.kotify.ui.artistview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
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
import com.epam.kotify.repository.Status
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

    override fun onAttach(context: Context?) {
        KotifyApp.component.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[TopsViewModel::class.java]
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
        val recycler = view.findViewById<RecyclerView>(R.id.topsRecyclerView)

        recycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = artistsAdapter
        }

        val progress = view.findViewById<ProgressBar>(R.id.progress)
        viewModel.artists.observe(this, Observer<Resource<List<Artist>>> {
            if (it.status == Status.SUCCESS || it.status == Status.ERROR) {
                progress.visibility = ProgressBar.GONE
                artistsAdapter.setArtists(it.data!!)
            } else {
                progress.visibility = ProgressBar.VISIBLE
            }
        })
    }

    companion object {
        private const val TAG = "ARTISTS FRAGMENT"
        fun newInstance() = TopArtistsFragment()
    }
}