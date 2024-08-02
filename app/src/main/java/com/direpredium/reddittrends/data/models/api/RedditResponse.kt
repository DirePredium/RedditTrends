package com.direpredium.reddittrends.data.models.api

data class RedditResponse(
    val kind: String,
    val data: RedditData
)

data class RedditData(
    val after: String?,
    val dist: Int,
    val children: List<Child>
)

data class Child(
    val kind: String,
    val data: PostData
)

data class PostData(
    val author: String,
    val created_utc: Long,
    val thumbnail: String,
    val title: String,
    val name: String,
    val media_metadata: Map<String, MediaMetadata>?,
    val gallery_data: GalleryData?,
    val num_comments: Int,
    val preview: Preview?
)

data class MediaMetadata(
    val s: MediaPicture?
)

data class MediaPicture(
    val u: String?
)

data class GalleryData(
    val items: List<GalleryItem>?
)

data class GalleryItem(
    val media_id: String,
    val id: Long
)

data class Preview(
    val images: List<Image>,
    val enabled: Boolean
)

data class Image(
    val source: ImageSource,
    val resolutions: List<ImageSource>,
    val id: String
)

data class ImageSource(
    val url: String,
    val width: Int,
    val height: Int
)