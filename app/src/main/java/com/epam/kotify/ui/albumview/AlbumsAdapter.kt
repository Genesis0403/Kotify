package com.epam.kotify.ui.albumview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.epam.kotify.R
import com.epam.kotify.model.domain.Album

class AlbumsAdapter(private val albums: List<Album>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.album_item, parent, false)
        return AlbumViewHolder(view)
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AlbumViewHolder -> {
                holder.apply {
                    val item = albums[position]
                    title.text = item.title
                    artist.text = item.artist
                    playcount.text = item.playcount.toString()
                    loadImage(image, item.image)
                }
            }
        }
    }

    private fun loadImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            .apply(RequestOptions.centerCropTransform().fitCenter())
            .error(R.drawable.error_picture_24dp)
            .into(imageView)
    }

    class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.trackImage)
        val title: TextView = view.findViewById(R.id.trackTitle)
        val artist: TextView = view.findViewById(R.id.artistName)
        val playcount: TextView = view.findViewById(R.id.listenersCountNumber)
    }
}