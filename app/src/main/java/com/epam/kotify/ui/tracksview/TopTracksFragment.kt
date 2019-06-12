package com.epam.kotify.ui.tracksview

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
import com.epam.kotify.App
import com.epam.kotify.R
import com.epam.kotify.model.domain.Track
import com.epam.kotify.repository.Resource
import com.epam.kotify.ui.EmptyRecyclerView
import com.epam.kotify.ui.viewmodels.TopsViewModel
import javax.inject.Inject

/**
 * Fragment which contains [EmptyRecyclerView] which [Track] and
 * makes request via [TopsViewModel] and observes [LiveData] of [Track].
 *
 * @see TopsViewModel
 *
 * @author Vlad Korotkevich
 */

class TopTracksFragment : Fragment() {

    companion object {
        private const val TAG = "TRACKS FRAGMENT"
        fun newInstance() = TopTracksFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: TopsViewModel

    private val tracksAdapter = TracksAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        App.component.inject(this)
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
            adapter = tracksAdapter
            emptyView = view.findViewById<TextView>(R.id.emptyView)
        }

        val progress = view.findViewById<ProgressBar>(R.id.progress)
        viewModel.tracks.observe(this, Observer<Resource<List<Track>>> {
            if (it.status.isLoading()) {
                recycler.emptyView?.visibility = View.GONE
                recycler.visibility = RecyclerView.GONE
                progress.visibility = ProgressBar.VISIBLE
            } else {
                tracksAdapter.setTracks(it.data!!)
                progress.visibility = ProgressBar.GONE
            }
        })
    }
}