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
import com.epam.kotify.App
import com.epam.kotify.R
import com.epam.kotify.model.domain.Track
import com.epam.kotify.ui.EmptyRecyclerView
import com.epam.kotify.ui.RemoveFromLovedDialog
import com.epam.kotify.ui.viewmodels.TopsViewModel
import javax.inject.Inject

class LovedTracksFragment : Fragment(), TracksAdapter.OnItemLongClickListener {

    companion object {
        private const val TAG = "LOVED_TRACKS_FRAGMENT"

        fun newInstance() = LovedTracksFragment()
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var topsViewModel: TopsViewModel
    private val tracksAdapter = TracksAdapter(this)

    override fun onAttach(context: Context?) {
        App.component.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topsViewModel = ViewModelProviders.of(requireActivity(), factory)[TopsViewModel::class.java]
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
        topsViewModel.lovedTracks.observe(this, Observer<List<Track>> {
            tracksAdapter.run {
                this.setTracks(it)
                this.notifyDataSetChanged()
            }
        })
    }

    override fun onLongClick(track: Track) {
        RemoveFromLovedDialog.newInstance(RemoveFromLovedDialog.TRACK_ID, track)
            .show(activity?.supportFragmentManager, null)
    }
}