package com.nsv.categorytestnews.fragmentClasses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nsv.categorytestnews.MainActivity
import com.nsv.categorytestnews.models.NewsModel
import com.nsv.categorytestnews.R
import com.nsv.categorytestnews.adapters.FragmentAdapter
import com.nsv.categorytestnews.architecture.NewsViewModel
import com.nsv.categorytestnews.databinding.FragmentCategoryBinding
import com.nsv.categorytestnews.utils.Constants


class CategoryFragment : Fragment(R.layout.fragment_category) {

    private lateinit var mainActivity: MainActivity
    private lateinit var binding: FragmentCategoryBinding

    private  val newsCategories = arrayOf(
        Constants.HOME, Constants.BUSINESS,
        Constants.ENTERTAINMENT, Constants.SCIENCE,
        Constants.SPORTS, Constants.TECHNOLOGY, Constants.HEALTH
    )

    private lateinit var viewModel: NewsViewModel
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var fragmentAdapter: FragmentAdapter
    private lateinit var shimmerLayout: ShimmerFrameLayout
    private lateinit var newsViewModel: NewsViewModel
    private var totalRequestCount = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoryBinding.bind(view)


        newsViewModel = NewsViewModel()

        mainActivity = MainActivity()
        tabLayout = binding.tabLayout
        viewPager = binding.viewPager
        shimmerLayout = binding.shimmerLayout
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        requestNews(Constants.GENERAL, generalNews)
        requestNews(Constants.BUSINESS, businessNews)
        requestNews(Constants.ENTERTAINMENT, entertainmentNews)
        requestNews(Constants.HEALTH, healthNews)
        requestNews(Constants.SCIENCE, scienceNews)
        requestNews(Constants.SPORTS, sportsNews)
        requestNews(Constants.TECHNOLOGY, techNews)

        newsViewModel = NewsViewModel()
        mainActivity = requireActivity() as MainActivity


        fragmentAdapter = FragmentAdapter(mainActivity.supportFragmentManager, lifecycle)
        viewPager.adapter = fragmentAdapter
        viewPager.visibility = View.GONE
    }

    private fun requestNews(newsCategory: String, newsData: MutableList<NewsModel>) {
        viewModel.getNews(category = newsCategory)?.observe(viewLifecycleOwner) {
            newsData.addAll(it)
            totalRequestCount += 1

            // If main fragment loaded then attach the fragment to viewPager
            if (newsCategory == Constants.GENERAL) {
                shimmerLayout.stopShimmer()
                shimmerLayout.hideShimmer()
                shimmerLayout.visibility = View.GONE
                setViewPager()
            }

            if (totalRequestCount == Constants.TOTAL_NEWS_TAB) {
                viewPager.offscreenPageLimit = 7
            }
        }
    }

    private fun setViewPager() {
        if (!apiRequestError) {
            viewPager.visibility = View.VISIBLE
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = newsCategories[position]
            }.attach()
        } else {
            val showError: TextView = binding.displayError
            showError.text = errorMessage
            showError.visibility = View.VISIBLE
        }
    }

    companion object {
        var generalNews: ArrayList<NewsModel> = ArrayList()
        var entertainmentNews: MutableList<NewsModel> = mutableListOf()
        var businessNews: MutableList<NewsModel> = mutableListOf()
        var healthNews: MutableList<NewsModel> = mutableListOf()
        var scienceNews: MutableList<NewsModel> = mutableListOf()
        var sportsNews: MutableList<NewsModel> = mutableListOf()
        var techNews: MutableList<NewsModel> = mutableListOf()
        var apiRequestError = false
        var errorMessage = "error"
    }


}