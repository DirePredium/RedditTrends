package com.direpredium.reddittrends.domain.usecase

import com.direpredium.reddittrends.domain.models.storage.PostState
import com.direpredium.reddittrends.domain.repository.PostStateRepository
import javax.inject.Inject

class GetPostStateUseCase @Inject constructor(private val postStateRepository: PostStateRepository) {

    fun execute(): PostState? {
        return postStateRepository.getPostState()
    }

}