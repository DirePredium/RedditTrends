package com.direpredium.reddittrends.domain.usecase

import com.direpredium.reddittrends.domain.models.api.Page
import com.direpredium.reddittrends.domain.models.api.Post
import com.direpredium.reddittrends.domain.repository.PostsApiRepository

class GetPostsByPageUseCase(private val postsApiRepository: PostsApiRepository) {

    suspend fun execute(page: Page): Result<List<Post>> {
        return postsApiRepository.getPostsByPage(page)
    }

}