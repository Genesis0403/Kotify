package com.epam.kotify.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.epam.kotify.ui.artistview.TopArtistsFragment
import com.epam.kotify.ui.tracksview.TopTracksFragment

class TopsViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private companion object {
        private const val COUNT = 3
        private const val FIRST_PAGE = 0
        private const val SECOND_PAGE = 1
        private const val THIRD_PAGE = 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            FIRST_PAGE -> MapFragment.newInstance()
            SECOND_PAGE -> TopArtistsFragment.newInstance()
            THIRD_PAGE -> TopTracksFragment.newInstance()
            else -> throw IllegalArgumentException("Fragment's manager position out of range.")
        }
    }

    override fun getCount() = COUNT

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            FIRST_PAGE -> "MAP"
            SECOND_PAGE -> "ARTISTS"
            THIRD_PAGE -> "TRACKS"
            else -> throw IllegalArgumentException("Fragment's manager position out of range.")
        }
    }
}