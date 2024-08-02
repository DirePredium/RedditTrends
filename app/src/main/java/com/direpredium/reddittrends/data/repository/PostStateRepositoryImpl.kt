package com.direpredium.reddittrends.data.repository

import com.direpredium.reddittrends.data.models.storage.PostCashState
import com.direpredium.reddittrends.data.storage.PostStateStorage
import com.direpredium.reddittrends.domain.models.storage.PostState
import com.direpredium.reddittrends.domain.repository.PostStateRepository

class PostStateRepositoryImpl(val postStateStorage: PostStateStorage): PostStateRepository {

    override fun savePostState(postState: PostState?): Boolean {
        return postStateStorage.savePostState(postState?.mapToPostCashState())
    }

    override fun getPostState(): PostState? {
        return postStateStorage.getPostState()?.mapToPostState()
    }

}

fun PostState.mapToPostCashState(): PostCashState {
    return PostCashState(
        this.name,
        this.url
    )
}

fun PostCashState.mapToPostState(): PostState {
       return PostState(
           this.name,
           this.url
       )
}