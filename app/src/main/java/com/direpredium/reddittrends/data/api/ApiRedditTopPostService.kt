package com.direpredium.reddittrends.data.api

import com.direpredium.reddittrends.data.models.api.PageRequest
import com.direpredium.reddittrends.data.models.api.PostResponse

interface ApiRedditTopPostService {
    // "https://www.reddit.com/top.json")
    fun getPostsByPage(pageRequest: PageRequest): PostResponse
}