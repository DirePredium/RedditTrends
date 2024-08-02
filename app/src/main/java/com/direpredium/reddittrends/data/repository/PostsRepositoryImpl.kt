package com.direpredium.reddittrends.data.repository

import com.direpredium.reddittrends.data.api.ApiRedditTopPostService
import com.direpredium.reddittrends.data.models.api.PostData
import com.direpredium.reddittrends.data.models.api.RedditData
import com.direpredium.reddittrends.data.models.api.RedditResponse
import com.direpredium.reddittrends.data.util.HtmlUrlStringHandler
import com.direpredium.reddittrends.domain.models.api.AsyncResult
import com.direpredium.reddittrends.domain.models.api.ErrorResult
import com.direpredium.reddittrends.domain.models.api.GalleryData
import com.direpredium.reddittrends.domain.models.api.GalleryItem
import com.direpredium.reddittrends.domain.models.api.MediaMetadata
import com.direpredium.reddittrends.domain.models.api.MediaPicture
import com.direpredium.reddittrends.domain.models.api.Page
import com.direpredium.reddittrends.domain.models.api.PagingPosts
import com.direpredium.reddittrends.domain.models.api.Post
import com.direpredium.reddittrends.domain.models.api.SuccessResult
import com.direpredium.reddittrends.domain.repository.PostsApiRepository
import kotlinx.coroutines.Dispatchers
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
        val url = metadata.s?.u?.let {
            HtmlUrlStringHandler.decodeHtmlEntities(it)
        }
        MediaMetadata(fullMedia = MediaPicture(url = url))
    }
    val galleryData = this.gallery_data?.let { galleryData ->
        GalleryData(items = galleryData.items?.map { item ->
            GalleryItem(media_id = item.media_id, id = item.id)
        })
    }
    val url = this.preview?.images?.getOrNull(0)?.source?.let { imageSource ->
        HtmlUrlStringHandler.decodeHtmlEntities(imageSource.url)
    }
    val thumbnail = HtmlUrlStringHandler.decodeHtmlEntities(this.thumbnail)
    return Post(
        name = this.name,
        author = this.author,
        createUtc = this.created_utc,
        thumbnailUrl = thumbnail,
        title = this.title,
        mediaMetadata = mediaMetadataMapped,
        galleryData = galleryData,
        imageSource = url,
        numComments = this.num_comments
    )
}
