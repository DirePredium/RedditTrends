package com.direpredium.reddittrends.data.models.storage

data class SavePhotoParams(
    val name: String,
    val url: String,
    val format: SavedFileFormat = SavedFileFormat.JPEG
)

enum class SavedFileFormat(val format: String) {
    JPEG("image/jpeg"), PNG("image/png"), GIF("image/gif")
}