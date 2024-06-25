package com.eitadevelopment.tweetcounter.ui.homescreen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eitadevelopment.tweetcounter.R
import com.eitadevelopment.tweetcounter.ui.component.ScreenContent
import com.eitadevelopment.tweetcounter.ui.component.TweetCounterState
import com.eitadevelopment.tweetcounter.ui.component.TweeterAuthScreen
import com.eitadevelopment.tweetcounter.ui.component.TweeterAuthState
import com.eitadevelopment.tweetcounter.ui.theme.TweetCounterTheme
import com.eitadevelopment.tweetcounter.util.AppPreferences
import com.eitadevelopment.tweetcounter.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeScreenActivity : ComponentActivity() {
    private val viewModel: HomeScreenViewModel by viewModels()

    @Inject
    lateinit var appPreferences: AppPreferences

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val nevController = rememberNavController()
            TweetCounterTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = stringResource(R.string.twitter_character_count),
                                fontSize = 18.sp,
                                fontWeight = FontWeight(500),
                            )
                        },
                        actions = {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_outlined_chevronright),
                                    contentDescription = null
                                )
                            }
                        },
                        modifier = Modifier.shadow(1.dp)
                    )
                }) { innerPadding ->
                    NavHost(navController = nevController, startDestination = "post") {
                        composable("post") {
                            ScreenContent(
                                modifier = Modifier.padding(innerPadding),
                                tweetCounterState = TweetCounterState(
                                    charactersCount = viewModel.charactersCount,
                                    tweetContent = viewModel.tweetContent,
                                    isPostButtonEnabled = viewModel.isPostButtonEnabled,
                                    isClearButtonEnabled = viewModel.isClearButtonEnabled,
                                    isCopyButtonEnabled = viewModel.isCopyButtonEnabled,
                                    characterRemaining = viewModel.characterRemaining,
                                    onValueChange = viewModel::updateCharactersCount,
                                    onCopyClick = ::copyTextToClipboard,
                                    onClearClick = viewModel::clearCharactersCount,
                                    onPostClick = {
                                        lifecycleScope.launch {
                                            viewModel.authUrl
                                                .collect { authUrl ->
                                                    authUrl?.let {
                                                        nevController.navigate("auth")
                                                    }
                                                }
                                        }
                                    }
                                )
                            )
                        }
                        composable("auth") {
                            TweeterAuthScreen(
                                tweeterAuthState = TweeterAuthState(
                                    authUrl = viewModel.authUrl.value,
                                    onAuthorize = {
                                        lifecycleScope.launch {
                                            viewModel.accessToken.collect { accessToken ->
                                                accessToken?.let {
                                                    appPreferences.setAccessToken(accessToken.token)
                                                    appPreferences.setAccessTokenSecret(accessToken.tokenSecret)
                                                    viewModel.postTweet.collect {
                                                        it?.let {
                                                            Toast.makeText(
                                                                this@HomeScreenActivity,
                                                                "Your Tweet is now available on Twitter",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                            viewModel.clearCharactersCount()
                                                            nevController.navigate("post")
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    },
                                    onVerifierValueChanged = viewModel::updateVerifier,
                                    verifierValue = viewModel.verifier
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun copyTextToClipboard() {
        val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", viewModel.tweetContent.value)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
    }
}

