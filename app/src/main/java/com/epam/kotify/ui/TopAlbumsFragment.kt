package com.epam.kotify.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.epam.kotify.R
import com.epam.kotify.model.albums.TopAlbumsResponse
import com.epam.kotify.model.domain.Album
import com.epam.kotify.network.RetrofitBuilder
import com.epam.kotify.network.UserTopAlbumsService
import com.epam.kotify.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopAlbumsFragment : Fragment() {

    private val albums: MutableList<Album> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.top_albums_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progress = view.findViewById<ProgressBar>(R.id.progress)
        val recycler = view.findViewById<RecyclerView>(R.id.albumsContainer)
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = AlbumsAdapter(albums)

            val topAlbumsService =
                RetrofitBuilder.retrofit.create(UserTopAlbumsService::class.java)
                    .getUserTopAlbums(Constants.TEMP_USER, LIMIT, context.getString(R.string.api_key))

            topAlbumsService.enqueue(object : Callback<TopAlbumsResponse> {
                override fun onFailure(call: Call<TopAlbumsResponse>, t: Throwable) {
                    Log.d(TAG, "FAILED")
                    Toast.makeText(context, "Failed during download!", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<TopAlbumsResponse>, response: Response<TopAlbumsResponse>) {
                    Log.d(TAG, "IN onResponse")
                    if (response.isSuccessful) {
                        val serverAlbums = response.body()?.topAlbums?.albums
                        albums.addAll(serverAlbums?.map {
                            Album(
                                it.title,
                                it.artist?.name,
                                it.image.last()?.url,
                                it.playCount
                            )
                        } ?: return)
                        adapter?.notifyDataSetChanged()
                        progress.visibility = ProgressBar.GONE
                    }
                }
            })
        }
    }

    companion object {

        private const val TAG = "ALBUMS FRAGMENT"
        private const val LIMIT = 10

        fun newInstance() = TopAlbumsFragment()

    }
}