package com.direpredium.reddittrends.domain.usecase

import com.direpredium.reddittrends.domain.models.api.Page
import com.direpredium.reddittrends.domain.models.api.Post
import com.direpredium.reddittrends.domain.repository.PostsApiRepository
import javax.inject.Inject
import com.direpredium.reddittrends.domain.models.api.AsyncResult
import com.direpredium.reddittrends.domain.models.api.PagingPosts

class GetPostsByPageUseCase @Inject constructor(private val postsApiRepository: PostsApiRepository) {

    suspend fun execute(page: Page): AsyncResult<PagingPosts> {
        return postsApiRepository.getPostsByPage(page)
    }

}