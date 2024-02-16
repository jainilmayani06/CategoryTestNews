package com.nsv.categorytestnews.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nsv.categorytestnews.fragmentClasses.BusinessFragment
import com.nsv.categorytestnews.fragmentClasses.EntertainmentFragment
import com.nsv.categorytestnews.fragmentClasses.GeneralFragment
import com.nsv.categorytestnews.fragmentClasses.HealthFragment
import com.nsv.categorytestnews.fragmentClasses.ScienceFragment
import com.nsv.categorytestnews.fragmentClasses.SportsFragment
import com.nsv.categorytestnews.fragmentClasses.TechFragment
import com.nsv.categorytestnews.utils.Constants.TOTAL_NEWS_TAB


class FragmentAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle){

    override fun getItemCount(): Int = TOTAL_NEWS_TAB

    override fun createFragment(position: Int): Fragment {

        when (position) {
            0 -> {
                return GeneralFragment()
            }
            1 -> {
                return BusinessFragment()
            }
            2 -> {
                return EntertainmentFragment()
            }
            3 -> {
                return ScienceFragment()
            }
            4 -> {
                return SportsFragment()
            }
            5 -> {
                return TechFragment()
            }
            6 -> {
                return HealthFragment()
            }

            else -> return BusinessFragment()

        }
    }
}