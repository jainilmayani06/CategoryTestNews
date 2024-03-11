package com.nsv.categorytestnews.fragmentClasses

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.nsv.categorytestnews.MainActivity
import com.nsv.categorytestnews.NewsViewModel
import com.nsv.categorytestnews.R
import com.nsv.categorytestnews.databinding.FragmentArticleBinding

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var newsViewModel: NewsViewModel

    lateinit var binding: FragmentArticleBinding


    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)

        val args: ArticleFragmentArgs by navArgs()

        newsViewModel = (activity as MainActivity).newsViewModel
        val article = args.article

        if (article.url.isEmpty()) {
            Toast.makeText(requireContext(), "URL is null or empty", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
            return
        }

       binding.paginationProgressBar.visibility = View.VISIBLE




        binding.webView.apply {
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    // Hide progress bar once the page is loaded
                    binding.paginationProgressBar.visibility = View.GONE
                }
            }
            webChromeClient = WebChromeClient()
            loadUrl(article.url)
            settings.apply {
                domStorageEnabled = true
                loadsImagesAutomatically = true
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                javaScriptEnabled = true
            }
        }

        binding.fab.setOnClickListener {
            newsViewModel.addToFavourites(article)
            Snackbar.make(view, "Added to favourites", Snackbar.LENGTH_SHORT).show()
        }
    }
}
