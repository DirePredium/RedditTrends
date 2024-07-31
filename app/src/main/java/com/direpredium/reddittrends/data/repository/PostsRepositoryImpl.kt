package com.direpredium.reddittrends.data.repository

import com.direpredium.reddittrends.data.api.ApiRedditTopPostService
import com.direpredium.reddittrends.data.models.api.PostData
import com.direpredium.reddittrends.data.models.api.RedditData
import com.direpredium.reddittrends.data.models.api.RedditResponse
import com.direpredium.reddittrends.domain.models.api.AsyncResult
import com.direpredium.reddittrends.domain.models.api.ErrorResult
import com.direpredium.reddittrends.domain.models.api.MediaMetadata
import com.direpredium.reddittrends.domain.models.api.MediaPicture
import com.direpredium.reddittrends.domain.models.api.Page
import com.direpredium.reddittrends.domain.models.api.PagingPosts
import com.direpredium.reddittrends.domain.models.api.Post
import com.direpredium.reddittrends.domain.models.api.SuccessResult
import com.direpredium.reddittrends.domain.repository.PostsApiRepository
import retrofit2.HttpException
import retrofit2.Response

class PostsRepositoryImpl(private val apiRedditTopPostService: ApiRedditTopPostService) :
    PostsApiRepository {

    override suspend fun getPostsByPage(page: Page): AsyncResult<PagingPosts> {
        try {
            val response: Response<RedditResponse> =
                apiRedditTopPostService.getPostsByPage(page.limit)
            if (response.isSuccessful) {
                val posts =
                    response.body()?.data ?: return SuccessResult(PagingPosts(0, emptyList()))
                val pagingPosts = posts.mapToPagingPosts()
                return SuccessResult(pagingPosts)
            } else {
                println("Error: ${response.errorBody()?.string()}")
                return ErrorResult(HttpException(response))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return ErrorResult(e)
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
        MediaMetadata(fullMedia = MediaPicture(url = metadata.s.u))
    }
    return Post(
        author = this.author,
        createUtc = this.created_utc,
        thumbnailUrl = this.thumbnail,
        mediaMetadata = mediaMetadataMapped,
        numComments = this.num_comments
    )
}
