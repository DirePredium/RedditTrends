package com.direpredium.reddittrends.data.api.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://www.reddit.com/"

class RedditRetrofitClient() {

    private var _tempRetrofitClient : Retrofit? = null

    val instance: Retrofit
        get() {
            if(_tempRetrofitClient == null) {
                _tempRetrofitClient = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return _tempRetrofitClient!!
        }

}