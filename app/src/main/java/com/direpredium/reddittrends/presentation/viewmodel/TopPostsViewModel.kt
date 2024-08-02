package com.direpredium.reddittrends.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.direpredium.reddittrends.domain.models.api.Page
import com.direpredium.reddittrends.domain.models.api.Post
import com.direpredium.reddittrends.domain.models.storage.PostState
import com.direpredium.reddittrends.domain.usecase.GetPostStateUseCase
import com.direpredium.reddittrends.domain.usecase.GetPostsByPageUseCase
import com.direpredium.reddittrends.domain.usecase.SavePostStateUseCase
import com.direpredium.reddittrends.presentation.paging.PostsPageLoader
import com.direpredium.reddittrends.presentation.paging.PostsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TopPostsViewModel @Inject constructor(
    private val getPostsByPageUseCase: GetPostsByPageUseCase,
    private val savePostStateUseCase: SavePostStateUseCase
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
        val page = Page(15)
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

    fun saveOpenedPostState(postState: PostState?) {
        viewModelScope.launch(Dispatchers.IO) {
            savePostStateUseCase.execute(postState)
        }
    }

}