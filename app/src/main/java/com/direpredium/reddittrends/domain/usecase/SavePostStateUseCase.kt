package com.direpredium.reddittrends.domain.usecase

import com.direpredium.reddittrends.domain.models.storage.PostState
import com.direpredium.reddittrends.domain.repository.PostStateRepository

class SavePostStateUseCase(private val postStateRepository: PostStateRepository) {

    fun execute(postState: PostState): Boolean {
        return postStateRepository.savePostState(postState)
    }

}