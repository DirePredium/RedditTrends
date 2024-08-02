package com.direpredium.reddittrends.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.direpredium.reddittrends.domain.models.api.AsyncResult
import com.direpredium.reddittrends.domain.models.api.ErrorResult
import com.direpredium.reddittrends.domain.models.api.PagingPosts
import com.direpredium.reddittrends.domain.models.api.Post
import com.direpredium.reddittrends.domain.models.api.SuccessResult

typealias PostsPageLoader = suspend (limit: Int, after: String?) -> AsyncResult<PagingPosts>

class PostsPagingSource(
    private val loader: PostsPageLoader
) : PagingSource<String, Post>(){

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Post> {
        return try {
            val after = params.key
            val posts = loader.invoke(params.loadSize, after)
            when(posts) {
                is SuccessResult -> {
                    LoadResult.Page(
                        data = posts.data.posts,
                        prevKey = null,
                        nextKey = posts.data.after
                    )
                }
                is ErrorResult -> {
                    return LoadResult.Error(
                        throwable = posts.exception
                    )
                }
                else -> {
                    return LoadResult.Error(
                        throwable = Exception("Unreachable response state")
                    )
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<String, Post>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }

}