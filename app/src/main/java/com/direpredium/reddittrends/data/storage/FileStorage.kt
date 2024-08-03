package com.direpredium.reddittrends.data.storage

import com.direpredium.reddittrends.data.models.storage.SavePhotoParams
import com.direpredium.reddittrends.domain.models.api.AsyncResult
import kotlinx.coroutines.flow.Flow

interface FileStorage {
    fun savePhoto(photoParams: SavePhotoParams): Flow<AsyncResult<Unit>>
}