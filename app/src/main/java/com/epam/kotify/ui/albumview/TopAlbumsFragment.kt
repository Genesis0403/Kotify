package com.epam.kotify.ui.albumview

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
import com.epam.kotify.api.CountryTopService
import com.epam.kotify.model.albums.TopAlbumsResponse
import com.epam.kotify.model.domain.Album
import com.epam.kotify.api.RetrofitBuilder
import com.epam.kotify.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopAlbumsFragment : Fragment() {

    private val retrofit = RetrofitBuilder().retrofit
    private val albums: MutableList<Album> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recycler_view_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progress = view.findViewById<ProgressBar>(R.id.progress)
        val recycler = view.findViewById<RecyclerView>(R.id.topsRecyclerView)
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = AlbumsAdapter(albums)

            val topAlbumsService =
                retrofit.create(CountryTopService::class.java)
                    .getUserTopAlbums(
                        Constants.TEMP_USER,
                        LIMIT.toString(), context.getString(R.string.api_key)
                    )

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
                                it.title ?: "None",
                                it.artist?.name ?: "None",
                                it.image.last()?.url ?: "",
                                it.playCount ?: 0
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