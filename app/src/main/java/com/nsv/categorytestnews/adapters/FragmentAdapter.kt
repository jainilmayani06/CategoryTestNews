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
            /*0 -> {
                return GeneralFragment()
            }*/
            0 -> {
                return BusinessFragment()
            }
            1 -> {
                return EntertainmentFragment()
            }
            2 -> {
                return ScienceFragment()
            }
            3 -> {
                return SportsFragment()
            }
            4 -> {
                return TechFragment()
            }
            5 -> {
                return HealthFragment()
            }

            else -> {
                return EntertainmentFragment()
            }

        }
    }
}