package com.direpredium.reddittrends.domain.usecase

import com.direpredium.reddittrends.domain.models.api.AsyncResult
import com.direpredium.reddittrends.domain.models.api.Page
import com.direpredium.reddittrends.domain.models.api.PagingPosts
import com.direpredium.reddittrends.domain.repository.PostsApiRepository
import javax.inject.Inject

class GetPostsByPageUseCase @Inject constructor(private val postsApiRepository: PostsApiRepository) {

    suspend fun execute(page: Page): AsyncResult<PagingPosts> {
        return postsApiRepository.getPostsByPage(page)
    }

}