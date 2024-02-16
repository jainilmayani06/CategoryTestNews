package com.nsv.categorytestnews.fragmentClasses

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.nsv.categorytestnews.R
import com.nsv.categorytestnews.databinding.FragmentHeadlinesBinding

class HeadlinesFragment : Fragment(R.layout.fragment_headlines) {

    lateinit var binding: FragmentHeadlinesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHeadlinesBinding.bind(view)

    }

}