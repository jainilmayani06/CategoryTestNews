package com.nsv.categorytestnews.fragmentClasses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.nsv.categorytestnews.R
import com.nsv.categorytestnews.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    lateinit var binding: FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)




    }

}