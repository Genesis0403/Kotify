package com.epam.kotify.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import javax.inject.Inject

class TopsViewPagerAdapter @Inject constructor(
    fm: FragmentManager,
    private val fragments: List<Fragment>,
    private val pagesNames: List<String>
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount() = fragments.size

    override fun getPageTitle(position: Int): CharSequence? {
        return pagesNames[position]
    }
}