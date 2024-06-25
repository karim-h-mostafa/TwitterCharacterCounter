package com.eitadevelopment.tweetcounter.di.module

import android.content.Context
import com.eitadevelopment.domain.repository.TweetRepository
import com.eitadevelopment.domain.usecase.AuthToTwitterUseCase
import com.eitadevelopment.domain.usecase.CountTweetCharactersUseCase
import com.eitadevelopment.domain.usecase.PostTweetUseCase
import com.eitadevelopment.tweetcounter.util.AppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun countTweetCharactersUseCase() =
        CountTweetCharactersUseCase()

    @Provides
    fun postTweetUseCase(tweetRepository: TweetRepository) =
        PostTweetUseCase(tweetRepository)

    @Suppress
    @Provides
    fun appPreferences(@ApplicationContext context: Context) = AppPreferences(context)

    @Provides
    fun authToTwitterUseCase(tweetRepository: TweetRepository) = AuthToTwitterUseCase(tweetRepository)



}