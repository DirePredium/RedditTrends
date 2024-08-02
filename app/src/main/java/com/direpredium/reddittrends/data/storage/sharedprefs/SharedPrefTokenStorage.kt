package com.direpredium.reddittrends.data.storage.sharedprefs

import android.content.Context
import com.direpredium.reddittrends.data.models.storage.PostCashState
import com.direpredium.reddittrends.data.storage.PostStateStorage

private const val SHARED_PREFS_NAME = "post_state"
private const val KEY_NAME = "post_name"
private const val KEY_URL = "post_url"

class SharedPrefPostStateStorage(context: Context): PostStateStorage {

    val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun savePostState(postCashState: PostCashState?): Boolean {
        sharedPreferences.edit().putString(KEY_NAME, postCashState?.name).apply()
        sharedPreferences.edit().putString(KEY_URL, postCashState?.url).apply()
        return true
    }

    override fun getPostState(): PostCashState? {
        val name = sharedPreferences.getString(KEY_NAME, null) ?: return null
        val url = sharedPreferences.getString(KEY_URL, null) ?: return null
        return PostCashState(
            name,
            url
        )
    }

}