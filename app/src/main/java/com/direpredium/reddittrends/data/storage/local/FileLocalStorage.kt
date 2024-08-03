package com.direpredium.reddittrends.data.storage.local

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.WorkerThread
import com.bumptech.glide.Glide
import com.direpredium.reddittrends.data.models.storage.SavePhotoParams
import com.direpredium.reddittrends.data.models.storage.SavedFileFormat
import com.direpredium.reddittrends.data.storage.FileStorage
import com.direpredium.reddittrends.domain.models.api.AsyncResult
import com.direpredium.reddittrends.domain.models.api.ErrorResult
import com.direpredium.reddittrends.domain.models.api.PendingResult
import com.direpredium.reddittrends.domain.models.api.SuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class FileLocalStorage(val context: Context): FileStorage {

    override fun savePhoto(photoParams: SavePhotoParams): Flow<AsyncResult<Unit>> = flow {
        emit(PendingResult())

        val result = try {
            val bitmap = withContext(Dispatchers.IO) {
                loadBitmap(photoParams.url)
            }
            saveBitmapToGallery(photoParams.name, bitmap, photoParams.format)
            emit(SuccessResult(Unit))
        } catch (e: Exception) {
            emit(ErrorResult(e))
        }
    }

    @WorkerThread
    private fun loadBitmap(url: String): Bitmap {
        return Glide.with(context)
            .asBitmap()
            .load(url)
            .submit()
            .get()
    }

    @WorkerThread
    private fun saveBitmapToGallery(imageName: String, bitmap: Bitmap, savedFileFormat: SavedFileFormat = SavedFileFormat.JPEG) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$imageName.${getFileExtension(savedFileFormat.format)}")
            put(MediaStore.MediaColumns.MIME_TYPE, savedFileFormat.format)
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let {
            context.contentResolver.openOutputStream(it).use { outputStream ->
                if (outputStream != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }
            }
        }
    }

    private fun getFileExtension(format: String): String {
        val extensions = mapOf(
            "image/jpeg" to "jpg",
            "image/png" to "png",
            "image/gif" to "gif"
        )
        return extensions.get(format) ?: throw IllegalArgumentException("Unknown file format")
    }

}