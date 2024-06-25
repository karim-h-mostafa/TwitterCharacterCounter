package com.eitadevelopment.tweetcounter.di.module

import com.eitadevelopment.data.datasource.TweetDataSource
import com.eitadevelopment.data.datasource.TweetDataSourceImpl
import com.eitadevelopment.data.repository.TweetRepositoryImpl
import com.eitadevelopment.data.service.TweetAPI
import com.eitadevelopment.domain.repository.TweetRepository
import com.github.scribejava.core.oauth.OAuth10aService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideTweetDataSource(tweetAPI: TweetAPI, authService: OAuth10aService): TweetDataSource =
        TweetDataSourceImpl(tweetAPI, authService)

    @Provides
    fun provideTweetRepository(tweetDataSource: TweetDataSource): TweetRepository =
        TweetRepositoryImpl(tweetDataSource)
}