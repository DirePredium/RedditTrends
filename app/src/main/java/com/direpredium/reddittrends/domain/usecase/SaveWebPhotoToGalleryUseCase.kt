package com.direpredium.reddittrends.domain.usecase

import com.direpredium.reddittrends.domain.models.storage.PhotoParams
import com.direpredium.reddittrends.domain.models.storage.SavePhotoResult
import com.direpredium.reddittrends.domain.repository.FileRepository
import javax.inject.Inject

class SaveWebPhotoToGalleryUseCase @Inject constructor(private val fileRepository: FileRepository) {

    suspend fun execute(photoParams: PhotoParams): SavePhotoResult {
        return fileRepository.saveWebFile(photoParams)
    }

}