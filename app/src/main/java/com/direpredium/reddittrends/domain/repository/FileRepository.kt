package com.direpredium.reddittrends.domain.repository

import com.direpredium.reddittrends.domain.models.api.AsyncResult
import com.direpredium.reddittrends.domain.models.storage.PhotoParams
import com.direpredium.reddittrends.domain.models.storage.SavePhotoResult
import kotlinx.coroutines.flow.Flow

interface FileRepository {
    suspend fun saveWebFile(photoParams: PhotoParams): Flow<AsyncResult<Unit>>
}