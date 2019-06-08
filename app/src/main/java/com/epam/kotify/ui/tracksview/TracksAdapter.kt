package com.epam.kotify.ui.tracksview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.epam.kotify.R
import com.epam.kotify.model.domain.Track
import java.util.concurrent.TimeUnit

class TracksAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var tracks: List<Track> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_item, parent, false)
        return TrackViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TrackViewHolder -> {
                val item = tracks[position]
                holder.apply {
                    title.text = item.title
                    artist.text = item.artist
                    loadImage(image, item.image)
                    listeners.text = item.listeners.toString()
                    duration.text = convertIntoTime(item.duration)
                }
            }
        }
    }

    private fun convertIntoTime(duration: Long): String {
        val minutes = TimeUnit.SECONDS.toMinutes(duration)
        val seconds = duration - TimeUnit.MINUTES.toSeconds(minutes)
        return StringBuilder().apply {
            if (minutes < 10) {
                append("0$minutes")
            } else {
                append(minutes)
            }
            append(":")
            if (seconds < 10) {
                append("0$seconds")
            } else {
                append(seconds)
            }
        }.toString()
    }

    private fun loadImage(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .apply(RequestOptions.centerCropTransform().fitCenter())
            .fallback(R.drawable.erro_photo_2_24dp)
            .error(R.drawable.erro_photo_2_24dp)
            .into(imageView)
    }

    fun setTracks(tracks: List<Track>) {
        this.tracks = tracks
        notifyDataSetChanged()
    }

    class TrackViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.trackTitle)
        val artist: TextView = view.findViewById(R.id.artistName)
        val duration: TextView = view.findViewById(R.id.duration)
        val image: ImageView = view.findViewById(R.id.trackImage)
        val listeners: TextView = view.findViewById(R.id.listenersCountNumber)
    }
}