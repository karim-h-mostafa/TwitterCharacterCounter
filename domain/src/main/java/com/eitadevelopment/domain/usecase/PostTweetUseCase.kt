package com.eitadevelopment.domain.usecase

import com.eitadevelopment.domain.model.Tweet
import com.eitadevelopment.domain.repository.TweetRepository

class PostTweetUseCase (private val tweetRepository: TweetRepository) {
    suspend operator fun invoke(tweet: Tweet) = tweetRepository.postTweet(tweet)
}