package com.direpredium.reddittrends.domain.repository

import com.direpredium.reddittrends.domain.models.storage.PhotoParams
import com.direpredium.reddittrends.domain.models.storage.SavePhotoResult

interface FileRepository {
    suspend fun saveWebFile(photoParams: PhotoParams): SavePhotoResult
}