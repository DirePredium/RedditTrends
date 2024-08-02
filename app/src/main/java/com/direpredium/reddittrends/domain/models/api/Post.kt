package com.direpredium.reddittrends.domain.models.api



class Post(
    val name: String,
    val author: String,
    val createUtc: Long,
    val title: String,
    val thumbnailUrl: String,
    val mediaMetadata: Map<String, MediaMetadata>?,
    val galleryData: GalleryData?,
    val imageSource: String?,
    val numComments: Int
)

data class MediaMetadata(
    val fullMedia: MediaPicture
)

data class MediaPicture(
    val url: String
)

data class GalleryData(
    val items: List<GalleryItem>
)

data class GalleryItem(
    val media_id: String,
    val id: Long
)