package com.nsv.categorytestnews.models

data class NewsDataFromJson(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)