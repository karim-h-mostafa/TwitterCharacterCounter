package com.eitadevelopment.data.datasource

import com.eitadevelopment.data.dto.TweetDTO
import com.eitadevelopment.data.dto.TweetResponseDTO
import com.github.scribejava.core.model.OAuth1AccessToken
import com.github.scribejava.core.model.OAuth1RequestToken
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface TweetDataSource {
    suspend fun postTweet(tweetDTO: TweetDTO): Response<TweetResponseDTO>
    suspend fun getRequestToken(): Flow<OAuth1RequestToken>
    fun getAuthorizationUrl(requestToken: OAuth1RequestToken): String
    suspend fun getAccessToken(requestToken: OAuth1RequestToken, oauthVerifier: String) : Flow<OAuth1AccessToken>

}