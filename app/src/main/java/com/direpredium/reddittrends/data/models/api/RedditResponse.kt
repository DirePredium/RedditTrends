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
    val media_metadata: Map<String, MediaMetadata>?,
    val num_comments: Int
)

data class MediaMetadata(
    val s: MediaPicture
)

data class MediaPicture(
    val u: String
)

