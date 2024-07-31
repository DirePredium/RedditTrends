package com.direpredium.reddittrends.di

import com.direpredium.reddittrends.domain.repository.FileRepository
import com.direpredium.reddittrends.domain.repository.PostStateRepository
import com.direpredium.reddittrends.domain.repository.PostsApiRepository
import com.direpredium.reddittrends.domain.usecase.GetPostDetailsByIdUseCase
import com.direpredium.reddittrends.domain.usecase.GetPostsByPageUseCase
import com.direpredium.reddittrends.domain.usecase.SavePostStateUseCase
import com.direpredium.reddittrends.domain.usecase.SaveWebPhotoToGalleryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun provideGetPostDetailsByIdUseCase(postsApiRepository: PostsApiRepository): GetPostDetailsByIdUseCase =
        GetPostDetailsByIdUseCase(postsApiRepository)

    @Provides
    fun provideGetPostsByPageUseCase(postsApiRepository: PostsApiRepository): GetPostsByPageUseCase =
        GetPostsByPageUseCase(postsApiRepository)

    @Provides
    fun provideSavePostStateUseCase(postStateRepository: PostStateRepository): SavePostStateUseCase =
        SavePostStateUseCase(postStateRepository)

    @Provides
    fun provideSaveWebPhotoToGalleryUseCase(fileRepository: FileRepository): SaveWebPhotoToGalleryUseCase =
        SaveWebPhotoToGalleryUseCase(fileRepository)

}