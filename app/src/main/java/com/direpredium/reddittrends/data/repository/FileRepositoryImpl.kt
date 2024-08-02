package com.direpredium.reddittrends.data.repository

import com.direpredium.reddittrends.data.models.storage.SavePhotoParams
import com.direpredium.reddittrends.data.models.storage.SavedFileFormat
import com.direpredium.reddittrends.data.storage.FileStorage
import com.direpredium.reddittrends.domain.models.api.AsyncResult
import com.direpredium.reddittrends.domain.models.storage.FileFormat
import com.direpredium.reddittrends.domain.models.storage.PhotoParams
import com.direpredium.reddittrends.domain.repository.FileRepository
import kotlinx.coroutines.flow.Flow

class FileRepositoryImpl(private val fileStorage: FileStorage): FileRepository {

    override suspend fun saveWebFile(photoParams: PhotoParams): Flow<AsyncResult<Unit>> {
        return fileStorage.savePhoto(photoParams.mapToSavePhotoParams())
    }

}

private fun PhotoParams.mapToSavePhotoParams(): SavePhotoParams {
    return SavePhotoParams(
        name = this.fileName,
        url = this.url,
        format = this.format.toSavedFileFormat() ?: throw IllegalArgumentException("Unknown file format")
    )
}

fun FileFormat.toSavedFileFormat(): SavedFileFormat? {
    return when (this) {
        FileFormat.JPEG -> SavedFileFormat.JPEG
        FileFormat.PNG -> SavedFileFormat.PNG
        else -> null
    }
}