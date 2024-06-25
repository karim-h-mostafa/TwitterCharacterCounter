package com.eitadevelopment.data.service

import com.eitadevelopment.data.dto.TweetDTO
import com.eitadevelopment.data.dto.TweetResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TweetAPI {
    @POST("/2/tweets")
    suspend fun postTweet(
        @Body tweetDTO: TweetDTO
    ): Response<TweetResponseDTO>
}