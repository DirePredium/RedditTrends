package com.direpredium.reddittrends.domain.repository

import com.direpredium.reddittrends.domain.models.api.Page
import com.direpredium.reddittrends.domain.models.api.Post

interface PostsApiRepository {
    suspend fun getPostsByPage(page: Page): Result<List<Post>>
    suspend fun getPostByName(name: String): Result<Post>
}