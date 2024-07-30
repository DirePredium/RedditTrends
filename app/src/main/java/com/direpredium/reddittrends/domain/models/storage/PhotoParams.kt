package com.direpredium.reddittrends.domain.models.storage

data class PhotoParams(
    val url: String,
    val fileName: String = "",
    val format: String = "image/jpeg"
)