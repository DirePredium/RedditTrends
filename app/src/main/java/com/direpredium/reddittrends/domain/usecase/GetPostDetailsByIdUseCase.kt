package com.direpredium.reddittrends.domain.usecase

import com.direpredium.reddittrends.domain.models.api.AsyncResult
import com.direpredium.reddittrends.domain.models.api.Post
import com.direpredium.reddittrends.domain.repository.PostsApiRepository
import javax.inject.Inject

class GetPostDetailsByIdUseCase @Inject constructor(private val postsApiRepository: PostsApiRepository) {

    suspend fun execute(name: String): AsyncResult<Post> {
        return postsApiRepository.getPostByName(name)
    }

}