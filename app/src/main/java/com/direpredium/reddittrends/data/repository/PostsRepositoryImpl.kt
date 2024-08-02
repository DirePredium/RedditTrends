package com.direpredium.reddittrends.data.repository

import androidx.paging.LivePagedListBuilder
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.direpredium.reddittrends.PostsPageLoader
import com.direpredium.reddittrends.PostsPagingSource
import com.direpredium.reddittrends.data.api.ApiRedditTopPostService
import com.direpredium.reddittrends.data.models.api.PostData
import com.direpredium.reddittrends.data.models.api.RedditData
import com.direpredium.reddittrends.data.models.api.RedditResponse
import com.direpredium.reddittrends.data.util.HtmlUrlStringHandler
import com.direpredium.reddittrends.domain.models.api.AsyncResult
import com.direpredium.reddittrends.domain.models.api.ErrorResult
import com.direpredium.reddittrends.domain.models.api.MediaMetadata
import com.direpredium.reddittrends.domain.models.api.MediaPicture
import com.direpredium.reddittrends.domain.models.api.Page
import com.direpredium.reddittrends.domain.models.api.PagingPosts
import com.direpredium.reddittrends.domain.models.api.Post
import com.direpredium.reddittrends.domain.models.api.SuccessResult
import com.direpredium.reddittrends.domain.repository.PostsApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

class PostsRepositoryImpl(private val apiRedditTopPostService: ApiRedditTopPostService) :
    PostsApiRepository {

    override suspend fun getPostsByPage(page: Page): AsyncResult<PagingPosts> =
        withContext(Dispatchers.IO) {
            try {
                val response: Response<RedditResponse> =
                    apiRedditTopPostService.getPostsByPage(page.limit, page.after)
                if (response.isSuccessful) {
                    val posts =
                        response.body()?.data ?: return@withContext SuccessResult(
                            PagingPosts(
                                0,
                                emptyList()
                            )
                        )
                    val pagingPosts = posts.mapToPagingPosts()
                    return@withContext SuccessResult(pagingPosts)
                } else {
                    println("Error: ${response.errorBody()?.string()}")
                    return@withContext ErrorResult(HttpException(response))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext ErrorResult(e)
            }
        }

    override suspend fun getPostByName(name: String): AsyncResult<Post> {
        TODO()
    }

}

fun RedditData.mapToPagingPosts(): PagingPosts {
    return PagingPosts(
        dist = this.dist,
        posts = this.children.map { it.data.mapToPost() },
        after = this.after
    )
}

fun PostData.mapToPost(): Post {
    val mediaMetadataMapped = this.media_metadata?.mapValues { (_, metadata) ->
        val url = HtmlUrlStringHandler.decodeHtmlEntities(metadata.s.u)
        MediaMetadata(fullMedia = MediaPicture(url = url))
    }
    val thumbnail = HtmlUrlStringHandler.decodeHtmlEntities(this.thumbnail)
    return Post(
        name = this.name,
        author = this.author,
        createUtc = this.created_utc,
        thumbnailUrl = thumbnail,
        title = this.title,
        mediaMetadata = mediaMetadataMapped,
        numComments = this.num_comments
    )
}
