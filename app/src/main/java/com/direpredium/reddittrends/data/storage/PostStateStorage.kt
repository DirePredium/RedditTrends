package com.direpredium.reddittrends.data.storage

import com.direpredium.reddittrends.data.models.storage.PostCashState


interface PostStateStorage {
    fun savePostState(postCashState: PostCashState): Boolean
    fun getPostState(): PostCashState
}