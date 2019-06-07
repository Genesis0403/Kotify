package com.epam.kotify.ui.tracksview

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
import com.epam.kotify.model.domain.Track
import com.epam.kotify.repository.Resource
import com.epam.kotify.repository.Status
import com.epam.kotify.ui.TopsViewModel
import com.epam.kotify.utils.AppExecutors
import javax.inject.Inject

class TopTracksFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var executors: AppExecutors

    private lateinit var viewModel: TopsViewModel

    private val tracksAdapter = TracksAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        KotifyApp.component.inject(this)
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
            adapter = tracksAdapter
        }

        val progress = view.findViewById<ProgressBar>(R.id.progress)
        viewModel.tracks.observe(this, Observer<Resource<List<Track>>> {
            if (it.status == Status.SUCCESS || it.status == Status.ERROR) {
                tracksAdapter.setTracks(it.data!!)
                progress.visibility = ProgressBar.GONE
            } else {
                progress.visibility = ProgressBar.VISIBLE
            }
        })
    }

    companion object {
        private const val TAG = "TRACKS FRAGMENT"
        fun newInstance() = TopTracksFragment()
    }
}