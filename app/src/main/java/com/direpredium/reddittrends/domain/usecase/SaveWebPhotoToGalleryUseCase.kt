package com.direpredium.reddittrends.domain.usecase

import com.direpredium.reddittrends.domain.models.api.AsyncResult
import com.direpredium.reddittrends.domain.models.storage.PhotoParams
import com.direpredium.reddittrends.domain.repository.FileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveWebPhotoToGalleryUseCase @Inject constructor(private val fileRepository: FileRepository) {

    suspend fun execute(photoParams: PhotoParams): Flow<AsyncResult<Unit>> {
        return fileRepository.saveWebFile(photoParams)
    }

}