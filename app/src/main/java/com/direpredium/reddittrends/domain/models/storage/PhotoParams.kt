package com.direpredium.reddittrends.domain.models.storage

data class PhotoParams(
    val url: String,
    val fileName: String,
    val format: FileFormat = FileFormat.JPEG
)

enum class FileFormat {
    JPEG,
    PNG
}