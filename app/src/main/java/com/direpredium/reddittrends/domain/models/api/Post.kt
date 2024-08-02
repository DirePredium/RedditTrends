package com.direpredium.reddittrends.domain.models.api


class Post(
    val name: String,
    val author: String,
    val createUtc: Long,
    val thumbnailUrl: String,
    val title: String,
    val mediaMetadata: Map<String, MediaMetadata>?,
    val numComments: Int
)

data class MediaMetadata(
    val fullMedia: MediaPicture
)

data class MediaPicture(
    val url: String
)