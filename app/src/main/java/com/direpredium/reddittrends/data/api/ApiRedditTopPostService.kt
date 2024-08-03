package com.direpredium.reddittrends.data.api

import com.direpredium.reddittrends.data.models.api.RedditResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiRedditTopPostService {
    @GET("top.json")
    suspend fun getPostsByPage(
        @Query("limit") limit: Int,
        @Query("after") after: String? = null
    ): Response<RedditResponse>
}