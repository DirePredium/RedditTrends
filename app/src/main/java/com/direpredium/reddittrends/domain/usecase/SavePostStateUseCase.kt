package com.direpredium.reddittrends.domain.usecase

import com.direpredium.reddittrends.domain.models.storage.PostState
import com.direpredium.reddittrends.domain.repository.PostStateRepository
import javax.inject.Inject

class SavePostStateUseCase @Inject constructor(private val postStateRepository: PostStateRepository) {

    fun execute(postState: PostState?): Boolean {
        return postStateRepository.savePostState(postState)
    }

}