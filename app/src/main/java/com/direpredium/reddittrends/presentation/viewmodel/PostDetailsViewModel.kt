package com.direpredium.reddittrends.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.direpredium.reddittrends.domain.models.api.AsyncResult
import com.direpredium.reddittrends.domain.models.api.SuccessResult
import com.direpredium.reddittrends.domain.models.storage.PhotoParams
import com.direpredium.reddittrends.domain.usecase.SavePostStateUseCase
import com.direpredium.reddittrends.domain.usecase.SaveWebPhotoToGalleryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val saveWebPhotoToGalleryUseCase: SaveWebPhotoToGalleryUseCase
): ViewModel() {

    private val _savePhotoState = MutableStateFlow<AsyncResult<Unit>>(SuccessResult(Unit))
    val savePhotoState: StateFlow<AsyncResult<Unit>> = _savePhotoState

    fun savePhoto(photoParams: PhotoParams) {
        viewModelScope.launch {
            saveWebPhotoToGalleryUseCase.execute(photoParams)
                .collect { result ->
                    _savePhotoState.value = result
                }
        }
    }

}