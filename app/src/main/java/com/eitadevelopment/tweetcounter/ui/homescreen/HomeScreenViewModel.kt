package com.eitadevelopment.tweetcounter.ui.homescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eitadevelopment.domain.model.Tweet
import com.eitadevelopment.domain.usecase.AuthToTwitterUseCase
import com.eitadevelopment.domain.usecase.CountTweetCharactersUseCase
import com.eitadevelopment.domain.usecase.PostTweetUseCase
import com.eitadevelopment.tweetcounter.util.Constants
import com.github.scribejava.core.model.OAuth1AccessToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val countTweetCharactersUseCase: CountTweetCharactersUseCase,
    private val postTweetUseCase: PostTweetUseCase,
    private val authToTwitterUseCase: AuthToTwitterUseCase
) : ViewModel() {
    private val _requestToken = flow {
        authToTwitterUseCase.getRequestToken().collect {
            emit(it)
            Log.d(Constants.VIEW_MODEL_TAG, "requestToken is: $it")
        }
    }.catch {
        Log.e(Constants.VIEW_MODEL_TAG, "requestToken: ", it)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    val authUrl: StateFlow<String?> = flow {
        _requestToken.collect { requestToken ->
            requestToken?.let {
                Log.d(
                    Constants.VIEW_MODEL_TAG,
                    "authUrl is: ${authToTwitterUseCase.getAuthorizationUrl(it)}"
                )
                emit(authToTwitterUseCase.getAuthorizationUrl(it))
            }
        }
    }.catch {
        Log.e(Constants.VIEW_MODEL_TAG, "authUrl: ", it)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )
    val accessToken: StateFlow<OAuth1AccessToken?> = flow {
        _requestToken.value?.let { requestTokenValue ->
            authToTwitterUseCase.getAccessToken(requestTokenValue, verifier.value)
                .collect{
                    emit(it)
                }
        }
    }.catch {
        Log.e(Constants.VIEW_MODEL_TAG, "accessToken: ", it)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )
    val isPostButtonEnabled: StateFlow<Boolean>
        get() = _charactersCount.map { it in 1..280 }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false
        )
    val isClearButtonEnabled: StateFlow<Boolean>
        get() = _charactersCount.map { it > 0 }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false
        )
    val isCopyButtonEnabled: StateFlow<Boolean>
        get() = _charactersCount.map { it > 0 }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false
        )
    val characterRemaining: StateFlow<Int>
        get() = _charactersCount.map { 280 - it }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            280
        )

    private val _tweetContent = MutableStateFlow("")
    val tweetContent: StateFlow<String>
        get() = _tweetContent

    private val _charactersCount = MutableStateFlow(0)
    val charactersCount = _charactersCount.asStateFlow()


    fun updateCharactersCount(content: String) {
        _charactersCount.value = countTweetCharactersUseCase(content)
        _tweetContent.value = content
    }

    fun clearCharactersCount() {
        _charactersCount.value = 0
        _tweetContent.value = ""
    }

    private val _verifier = MutableStateFlow("")
    val verifier = _verifier.asStateFlow()
    fun updateVerifier(verifier: String) {
        _verifier.value = verifier
    }

    val postTweet = flow {
        postTweetUseCase(Tweet(_tweetContent.value)).collect {
            emit(it)
        }
    }.catch {
        Log.e(Constants.VIEW_MODEL_TAG, "postTweet: ", it)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )
}