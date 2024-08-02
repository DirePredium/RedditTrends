package com.direpredium.reddittrends.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.direpredium.reddittrends.PostsPageLoader
import com.direpredium.reddittrends.PostsPagingSource
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

private const val LOG_TAG = "TopPostsViewModel"

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TopPostsViewModel @Inject constructor(
    private val getPostsByPageUseCase: GetPostsByPageUseCase
): ViewModel() {

    private val refreshTrigger = MutableLiveData(0)
    val postsFlow: Flow<PagingData<Post>>

    init {
        postsFlow = refreshTrigger.asFlow()
            .flatMapLatest {
                fetchPagedPosts()
            }
            .cachedIn(viewModelScope)
    }

    private fun fetchPagedPosts(): Flow<PagingData<Post>> {
        val page = Page(3)
        val loader: PostsPageLoader = { limit, after  ->
            getPostsByPageUseCase.execute(Page(limit, after))
        }
        return Pager(
            config = PagingConfig(
                pageSize = page.limit,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PostsPagingSource(loader) }
        ).flow
    }

    fun refresh() {
        this.refreshTrigger.postValue(this.refreshTrigger.value)
    }

}