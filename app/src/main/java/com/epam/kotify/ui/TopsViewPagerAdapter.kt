package com.epam.kotify.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.epam.kotify.ui.artistview.LovedArtistsFragment
import com.epam.kotify.ui.artistview.TopArtistsFragment
import com.epam.kotify.ui.tracksview.LovedTracksFragment
import com.epam.kotify.ui.tracksview.TopTracksFragment

/**
 * ViewPager adapter which contains fragments.
 *
 * @see MapFragment
 * @see TopArtistsFragment
 * @see TopTracksFragment
 *
 * @author Vlad Korotkevich
 */
class TopsViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private companion object {
        private const val COUNT = 5
        private const val FIRST_PAGE = 0
        private const val SECOND_PAGE = 1
        private const val THIRD_PAGE = 2
        private const val FOURTH_PAGE = 3
        private const val FIFTH_PAGE = 4
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            FIRST_PAGE -> MapFragment.newInstance()
            SECOND_PAGE -> TopArtistsFragment.newInstance()
            THIRD_PAGE -> TopTracksFragment.newInstance()
            FOURTH_PAGE -> LovedArtistsFragment.newInstance()
            FIFTH_PAGE -> LovedTracksFragment.newInstance()
            else -> throw IllegalArgumentException("Fragment's manager position out of range.")
        }
    }

    override fun getCount() = COUNT

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            FIRST_PAGE -> "MAP"
            SECOND_PAGE -> "ARTISTS"
            THIRD_PAGE -> "TRACKS"
            FOURTH_PAGE -> "LOVED ARTISTS"
            FIFTH_PAGE -> "LOVED TRACKS"
            else -> throw IllegalArgumentException("Fragment's manager position out of range.")
        }
    }
}