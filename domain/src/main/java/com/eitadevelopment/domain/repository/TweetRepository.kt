package com.eitadevelopment.domain.repository

import com.eitadevelopment.domain.model.Tweet
import com.eitadevelopment.domain.model.TweetResponse
import com.github.scribejava.core.model.OAuth1AccessToken
import com.github.scribejava.core.model.OAuth1RequestToken
import kotlinx.coroutines.flow.Flow

interface TweetRepository {
    suspend fun postTweet(tweet: Tweet): Flow<TweetResponse>
    suspend fun getRequestToken(): Flow<OAuth1RequestToken>
    fun getAuthorizationUrl(requestToken: OAuth1RequestToken): String
    suspend fun getAccessToken(
        requestToken: OAuth1RequestToken,
        oauthVerifier: String
    ): Flow<OAuth1AccessToken>
}