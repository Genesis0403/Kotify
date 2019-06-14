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
import com.epam.kotify.App
import com.epam.kotify.R
import com.epam.kotify.model.domain.Artist
import com.epam.kotify.ui.EmptyRecyclerView
import com.epam.kotify.ui.RemoveFromLovedDialog
import com.epam.kotify.ui.viewmodels.TopsViewModel
import javax.inject.Inject

class LovedArtistsFragment : Fragment(), ArtistsAdapter.OnItemLongClickListener {

    companion object {
        private const val TAG = "LOVED_ARTISTS_FRAGMENT"

        fun newInstance() = LovedArtistsFragment()
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var topsViewModel: TopsViewModel
    private val artistsAdapter = ArtistsAdapter(this)

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
            adapter = artistsAdapter
            emptyView = view.findViewById<TextView>(R.id.emptyView)
        }

        val progress = view.findViewById<ProgressBar>(R.id.progress)
        topsViewModel.lovedArtists.observe(this, Observer<List<Artist>> {
            artistsAdapter.run {
                this.setArtists(it)
                this.notifyDataSetChanged()
            }
        })
    }

    override fun onLongClick(artist: Artist) {
        RemoveFromLovedDialog.newInstance(RemoveFromLovedDialog.ARTIST_ID, artist)
            .show(activity?.supportFragmentManager, null)
    }
}