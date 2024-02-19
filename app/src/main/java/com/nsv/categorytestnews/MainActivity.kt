package com.nsv.categorytestnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayout
import com.nsv.categorytestnews.adapters.FragmentAdapter
import com.nsv.categorytestnews.databinding.ActivityMainBinding
import com.nsv.categorytestnews.repository.NewsRepository
import com.nsv.categorytestnews.utils.Constants.BUSINESS
import com.nsv.categorytestnews.utils.Constants.ENTERTAINMENT
import com.nsv.categorytestnews.utils.Constants.HEALTH
import com.nsv.categorytestnews.utils.Constants.HOME
import com.nsv.categorytestnews.utils.Constants.SCIENCE
import com.nsv.categorytestnews.utils.Constants.SPORTS
import com.nsv.categorytestnews.utils.Constants.TECHNOLOGY
import com.nsv.testnews.db.ArticleDatabase

class MainActivity : AppCompatActivity() {

    lateinit var newsViewModel: NewsViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
        newsViewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)


    }
}