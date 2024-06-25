package com.eitadevelopment.data.dto

import com.eitadevelopment.domain.model.TweetData
import com.eitadevelopment.domain.model.TweetResponse

data class TweetResponseDTO(
    val data: TweetDataDTO = TweetDataDTO()
)

fun TweetResponseDTO.tweetResponseDTOToTweetResponse(): TweetResponse {
    return TweetResponse(
        data = this.data.tweetDataDTOToTweetData()
    )
}

fun TweetResponse.tweetResponseToTweetResponseDTO(): TweetResponseDTO {
    return TweetResponseDTO(
        data = this.data.tweetDataToTweetDataDTO()
    )
}

