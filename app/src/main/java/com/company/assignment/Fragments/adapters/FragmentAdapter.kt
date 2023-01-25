package com.company.assignment.Fragments.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.company.assignment.Fragments.AlbumFragment
import com.company.assignment.Fragments.ArtistFragment
import com.company.assignment.Fragments.Trackfragment

class FragmentAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle, context: Context, totalTabs: Int
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    val NUM_PAGES = 3
    
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AlbumFragment()
            1 -> ArtistFragment()
            2 -> Trackfragment()
            else -> AlbumFragment()
        }
    }

    override fun getItemCount(): Int {
        return NUM_PAGES
    }

}