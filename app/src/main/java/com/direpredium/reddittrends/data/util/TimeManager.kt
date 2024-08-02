package com.direpredium.reddittrends.data.util

import java.util.concurrent.TimeUnit

object TimeManager {

    fun utcToHoursAgo(createUtc: Long): Long {
        val currentTimeMillis = System.currentTimeMillis()
        val postTimeMillis = createUtc * 1000
        val diffMillis = currentTimeMillis - postTimeMillis
        return TimeUnit.MILLISECONDS.toHours(diffMillis)
    }

}