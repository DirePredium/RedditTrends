package com.direpredium.reddittrends.data.models.storage

data class SavePhotoParams(
    val url: String,
    val fileName: String = "",
    val format: String = "image/jpeg"
)
