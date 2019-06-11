package com.epam.kotify.ui.artistview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.epam.kotify.R
import com.epam.kotify.model.domain.Artist
import com.epam.kotify.ui.ItemAnimator
import com.epam.kotify.ui.artistview.ArtistsAdapter.ArtistViewHolder

/**
 * Adapter of [RecyclerView] which contains list of [Artist].
 * All view holders are animated.
 *
 * @see ArtistViewHolder
 * @see ItemAnimator
 *
 * @author Vlad Korotkevich
 */

class ArtistsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var artists: List<Artist> = emptyList()
    private val animator = ItemAnimator()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.artist_item, parent, false)
        return ArtistViewHolder(view)
    }

    override fun getItemCount(): Int {
        return artists.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ArtistViewHolder -> {
                val item = artists[position]
                holder.apply {
                    artist.text = item.name
                    listeners.text = item.playCount.toString()
                    loadImage(image, item.image)
                }
                animator.fadeInAnimation(holder.itemView)
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation()
    }

    private fun loadImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            .apply(RequestOptions.centerCropTransform().fitCenter())
            .fallback(R.drawable.erro_photo_2_24dp)
            .error(R.drawable.erro_photo_2_24dp)
            .into(imageView)
    }

    fun setArtists(artists: List<Artist>) {
        this.artists = artists
        notifyDataSetChanged()
    }

    class ArtistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.artistImage)
        val artist: TextView = view.findViewById(R.id.artistName)
        val listeners: TextView = view.findViewById(R.id.listenersCountNumber)
    }
}