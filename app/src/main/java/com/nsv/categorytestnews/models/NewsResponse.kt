package com.nsv.categorytestnews.models

import com.nsv.categorytestnews.models.Article

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)