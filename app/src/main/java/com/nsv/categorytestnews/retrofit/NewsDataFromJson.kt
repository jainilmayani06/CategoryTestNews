package com.nsv.categorytestnews.retrofit

import com.nsv.categorytestnews.retrofit.Article

data class NewsDataFromJson(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)