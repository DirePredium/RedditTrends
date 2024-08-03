package com.direpredium.reddittrends.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.direpredium.reddittrends.domain.models.storage.PostState
import com.direpredium.reddittrends.domain.usecase.GetPostStateUseCase
import com.direpredium.reddittrends.domain.usecase.SavePostStateUseCase
import com.direpredium.reddittrends.domain.usecase.SaveWebPhotoToGalleryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPostStateUseCase: GetPostStateUseCase,
    private val savePostStateUseCase: SavePostStateUseCase
): ViewModel() {

    var isPostStateRestored: Boolean = false

    suspend fun getOpenedPostStateAsync(): PostState? {
        return withContext(Dispatchers.IO) {
            getPostStateUseCase.execute()
        }
    }

    fun setPostStateEmpty() {
        viewModelScope.launch(Dispatchers.IO) {
            savePostStateUseCase.execute(null)
        }
    }

}