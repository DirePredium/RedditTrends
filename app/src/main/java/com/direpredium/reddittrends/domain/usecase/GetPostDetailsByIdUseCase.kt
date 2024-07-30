package com.direpredium.reddittrends.domain.usecase

import com.direpredium.reddittrends.domain.models.api.Post
import com.direpredium.reddittrends.domain.repository.PostsApiRepository

class GetPostDetailsByIdUseCase(private val postsApiRepository: PostsApiRepository) {

    suspend fun execute(name: String): Result<Post> {
        return postsApiRepository.getPostByName(name)
    }

}