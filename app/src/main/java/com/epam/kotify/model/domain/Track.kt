package com.epam.kotify.model.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Model class which is used to represent API responses in [RecyclerView].
 *
 * @author Vlad Korotkevich
 */

@Entity
@Parcelize
data class Track(
    @PrimaryKey
    val title: String,
    val artist: String,
    val image: String,
    val duration: Long,
    val listeners: Int,
    var isLoved: Boolean = false
) : Parcelable