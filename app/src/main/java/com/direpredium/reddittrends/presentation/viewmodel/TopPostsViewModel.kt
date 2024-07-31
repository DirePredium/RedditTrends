package com.direpredium.reddittrends.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.direpredium.reddittrends.domain.models.storage.PostState
import com.direpredium.reddittrends.domain.usecase.GetPostsByPageUseCase
import com.direpredium.reddittrends.presentation.model.RedditPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.direpredium.reddittrends.domain.models.api.AsyncResult
import com.direpredium.reddittrends.domain.models.api.ErrorResult
import com.direpredium.reddittrends.domain.models.api.Page
import com.direpredium.reddittrends.domain.models.api.PagingPosts
import com.direpredium.reddittrends.domain.models.api.PendingResult
import com.direpredium.reddittrends.domain.models.api.Post
import com.direpredium.reddittrends.domain.models.api.SuccessResult

private const val LOG_TAG = "TopPostsViewModel"

@HiltViewModel
class TopPostsViewModel @Inject constructor(
    private val getPostsByPageUseCase: GetPostsByPageUseCase
): ViewModel() {

    private val _posts = MutableLiveData<AsyncResult<PagingPosts>>()
    val post: LiveData<AsyncResult<PagingPosts>> = _posts

    fun fetchRedditPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            _posts.postValue(PendingResult())
            val posts = getPostsByPageUseCase.execute(Page(2))
            _posts.postValue(posts)
        }
    }

}