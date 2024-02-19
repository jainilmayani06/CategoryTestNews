package com.nsv.categorytestnews.fragmentClasses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.nsv.categorytestnews.MainActivity
import com.nsv.categorytestnews.NewsViewModel
import com.nsv.categorytestnews.R
import com.nsv.categorytestnews.databinding.FragmentArticleBinding

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var newsViewModel: NewsViewModel

    lateinit var binding: FragmentArticleBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)

        val args: ArticleFragmentArgs by navArgs()

        newsViewModel = (activity as MainActivity).newsViewModel
        val article = args.article

        binding.webView.apply {
           webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            article.url?.let {
                loadUrl(it)
            }
            settings.apply {
                domStorageEnabled = true
                loadsImagesAutomatically = true
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                javaScriptEnabled = true
            }
            /*settings.apply {
                domStorageEnabled = true
                loadsImagesAutomatically = true
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                javaScriptEnabled = true
            }
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()*/
        }


        binding.fab.setOnClickListener {
            newsViewModel.addToFavourites(article)
            Snackbar.make(view, "Added to favourites", Snackbar.LENGTH_SHORT).show()
        }
    }
}