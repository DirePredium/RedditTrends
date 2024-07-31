package com.direpredium.reddittrends.domain.repository

import com.direpredium.reddittrends.domain.models.api.Page
import com.direpredium.reddittrends.domain.models.api.Post
import com.direpredium.reddittrends.domain.models.api.AsyncResult
import com.direpredium.reddittrends.domain.models.api.PagingPosts

interface PostsApiRepository {
    suspend fun getPostsByPage(page: Page): AsyncResult<PagingPosts>
    suspend fun getPostByName(name: String): AsyncResult<Post>
}