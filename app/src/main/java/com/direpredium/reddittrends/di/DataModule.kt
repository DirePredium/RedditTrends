package com.direpredium.reddittrends.di

import android.content.Context
import com.direpredium.reddittrends.data.api.ApiRedditTopPostService
import com.direpredium.reddittrends.data.api.retrofit.RedditRetrofitClient
import com.direpredium.reddittrends.data.repository.FileRepositoryImpl
import com.direpredium.reddittrends.data.repository.PostStateRepositoryImpl
import com.direpredium.reddittrends.data.repository.PostsRepositoryImpl
import com.direpredium.reddittrends.data.storage.FileStorage
import com.direpredium.reddittrends.data.storage.PostStateStorage
import com.direpredium.reddittrends.data.storage.local.FileLocalStorage
import com.direpredium.reddittrends.data.storage.sharedprefs.SharedPrefPostStateStorage
import com.direpredium.reddittrends.domain.repository.FileRepository
import com.direpredium.reddittrends.domain.repository.PostStateRepository
import com.direpredium.reddittrends.domain.repository.PostsApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRetrofitClient() : RedditRetrofitClient = RedditRetrofitClient()

    @Provides
    @Singleton
    fun providePostsRepositoryImpl(redditRetrofitClient: RedditRetrofitClient) : PostsApiRepository = PostsRepositoryImpl(redditRetrofitClient.instance.create(
        ApiRedditTopPostService::class.java)
    )

    @Provides
    @Singleton
    fun provideFileRepositoryImpl(fileStorage: FileStorage): FileRepository = FileRepositoryImpl(fileStorage)

    @Provides
    @Singleton
    fun provideFileLocalStorage(@ApplicationContext appContext: Context) : FileStorage = FileLocalStorage(appContext)


    @Provides
    @Singleton
    fun providePostStateRepositoryImpl(postStateStorage: PostStateStorage): PostStateRepository = PostStateRepositoryImpl(postStateStorage)

    @Provides
    @Singleton
    fun provideSharedPrefPostStateStorage(@ApplicationContext appContext: Context) : PostStateStorage = SharedPrefPostStateStorage(appContext)

}