package com.epam.kotify.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.epam.kotify.App
import com.epam.kotify.R
import com.epam.kotify.model.domain.Artist
import com.epam.kotify.model.domain.Track
import com.epam.kotify.ui.viewmodels.TopsViewModel
import javax.inject.Inject

class AddToLovedDialog : DialogFragment() {

    companion object {
        private const val TAG = "ADD_TO_LOVED_DIALOG"
        private const val ID = "ID"
        private const val ENTITY = "ENTITY"
        const val TRACK_ID = 1
        const val ARTIST_ID = 0

        fun newInstance(id: Int, artist: Artist): AddToLovedDialog {
            return AddToLovedDialog().apply {
                arguments = Bundle().apply {
                    putInt(ID, id)
                    putParcelable(ENTITY, artist)
                }
            }
        }

        fun newInstance(id: Int, track: Track): AddToLovedDialog {
            return AddToLovedDialog().apply {
                arguments = Bundle().apply {
                    putInt(ID, id)
                    putParcelable(ENTITY, track)
                }
            }
        }
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var topsViewModel: TopsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component.inject(this)
        super.onCreate(savedInstanceState)
        topsViewModel = ViewModelProviders.of(requireActivity(), factory)[TopsViewModel::class.java]
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
            .setTitle(getString(R.string.dialog_title))
            .setItems(R.array.dialogAddItems) { _, which ->
                when (which) {
                    0 -> {
                        val id = arguments?.getInt(ID, -1) ?: -1
                        addToLoved(id)
                    }
                }
            }.create()
    }

    private fun addToLoved(id: Int) {
        when (id) {
            ARTIST_ID -> {
                val artist = arguments?.getParcelable<Artist>(ENTITY) ?: return
                topsViewModel.insertLovedArtist(artist)
            }
            TRACK_ID -> {
                val track = arguments?.getParcelable<Track>(ENTITY) ?: return
                topsViewModel.insertLovedTrack(track)
            }
            else -> {
                Toast.makeText(context, getString(R.string.insertion_failde), Toast.LENGTH_SHORT).show()
                return
            }
        }
        Toast.makeText(context, getString(R.string.success_insertion), Toast.LENGTH_SHORT).show()
    }
}