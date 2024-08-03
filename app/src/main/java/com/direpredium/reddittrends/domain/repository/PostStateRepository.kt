package com.direpredium.reddittrends.domain.repository

import com.direpredium.reddittrends.domain.models.storage.PostState

interface PostStateRepository {
    fun savePostState(postState: PostState?): Boolean
    fun getPostState(): PostState?
}