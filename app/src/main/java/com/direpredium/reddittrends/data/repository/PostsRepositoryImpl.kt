package com.direpredium.reddittrends.data.repository

import com.direpredium.reddittrends.data.api.ApiRedditTopPostService
import com.direpredium.reddittrends.data.models.api.PageRequest
import com.direpredium.reddittrends.domain.models.api.Page
import com.direpredium.reddittrends.domain.models.api.Post
import com.direpredium.reddittrends.domain.repository.PostsApiRepository

class PostsRepositoryImpl(private val apiRedditTopPostService: ApiRedditTopPostService): PostsApiRepository {

    override suspend fun getPostsByPage(page: Page): Result<List<Post>> {
        TODO()
    }

    override suspend fun getPostByName(name: String): Result<Post> {
        TODO()
    }

}