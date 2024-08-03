package com.direpredium.reddittrends.domain.models.storage

sealed class SavePhotoResult
object SuccessSavePhotoResult: SavePhotoResult()
object FailureSavePhotoResult: SavePhotoResult()
class ErrorSavePhotoResult(val error: Throwable): SavePhotoResult()