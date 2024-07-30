package com.direpredium.reddittrends.data.repository

import com.direpredium.reddittrends.data.models.storage.SavePhotoParams
import com.direpredium.reddittrends.data.storage.FileStorage
import com.direpredium.reddittrends.domain.models.storage.ErrorSavePhotoResult
import com.direpredium.reddittrends.domain.models.storage.FailureSavePhotoResult
import com.direpredium.reddittrends.domain.models.storage.PhotoParams
import com.direpredium.reddittrends.domain.models.storage.SavePhotoResult
import com.direpredium.reddittrends.domain.models.storage.SuccessSavePhotoResult
import com.direpredium.reddittrends.domain.repository.FileRepository

class FileRepositoryImpl(private val fileStorage: FileStorage): FileRepository {

    override suspend fun saveWebFile(photoParams: PhotoParams): SavePhotoResult {
        return try {
            val savePhotoParams = SavePhotoParams(photoParams.url, photoParams.fileName, photoParams.format)
            val result = fileStorage.savePhoto(savePhotoParams)
            if (result) {
                SuccessSavePhotoResult
            } else {
                FailureSavePhotoResult
            }
        } catch (e: Exception) {
            ErrorSavePhotoResult(e)
        }
    }

}