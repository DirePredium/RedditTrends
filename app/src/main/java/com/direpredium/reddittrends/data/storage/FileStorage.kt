package com.direpredium.reddittrends.data.storage

import com.direpredium.reddittrends.data.models.storage.SavePhotoParams

interface FileStorage {
    fun savePhoto(photoParams: SavePhotoParams): Boolean
}