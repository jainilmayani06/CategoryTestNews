package com.nsv.categorytestnews.retrofit


import com.nsv.categorytestnews.BuildConfig.API_KEY
import com.nsv.categorytestnews.models.NewsDataFromJson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIHeadline {

    @GET("v2/top-headlines")
    suspend fun getHeadlines(
        @Query("country")
        countryCode: String = "in",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsDataFromJson>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsDataFromJson>
}