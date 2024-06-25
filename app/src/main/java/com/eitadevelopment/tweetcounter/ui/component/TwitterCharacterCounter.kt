package com.eitadevelopment.tweetcounter.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eitadevelopment.tweetcounter.R
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ScreenContent(modifier: Modifier, tweetCounterState: TweetCounterState) {
    val charactersCount by tweetCounterState.charactersCount.collectAsStateWithLifecycle()
    val tweetContent by tweetCounterState.tweetContent.collectAsStateWithLifecycle()
    val remainingCharacters by tweetCounterState.characterRemaining.collectAsStateWithLifecycle()
    val isPostButtonEnabled by tweetCounterState.isPostButtonEnabled.collectAsStateWithLifecycle()
    val isClearButtonEnabled by tweetCounterState.isClearButtonEnabled.collectAsStateWithLifecycle()
    val isCopyButtonEnabled by tweetCounterState.isCopyButtonEnabled.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.twitter_logo),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = Color(230, 246, 254, 255),
                        shape = RoundedCornerShape(12.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.characters_typed),
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    modifier = Modifier.padding(12.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1

                )
                Text(
                    text = "${charactersCount}/280",
                    textAlign = TextAlign.Center,
                    fontSize = 26.sp,
                    fontWeight = FontWeight(500),
                    modifier = Modifier
                        .padding(2.dp)
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp)
                        )
                        .fillMaxWidth()
                        .padding(20.dp)
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = Color(230, 246, 254, 255),
                        shape = RoundedCornerShape(12.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.characters_remaining),
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    modifier = Modifier.padding(12.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = "" + remainingCharacters,
                    textAlign = TextAlign.Center,
                    fontSize = 26.sp,
                    fontWeight = FontWeight(500),
                    modifier = Modifier
                        .padding(2.dp)
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp)
                        )
                        .fillMaxWidth()
                        .padding(20.dp)
                )
            }
        }
        OutlinedTextField(
            value = tweetContent,
            onValueChange = tweetCounterState.onValueChange,
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Start typing! You can enter up to 280 characters",
                    fontSize = 14.sp
                )
            },
            minLines = 8,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedBorderColor = Color(240, 240, 241, 255),
                focusedBorderColor = Color(33, 150, 243, 255),
                unfocusedLabelColor = Color(94, 97, 96, 255)
            ),
            shape = RoundedCornerShape(12.dp),
        )
        Row(
            modifier = Modifier
                .padding()
                .fillMaxWidth()
        ) {
            FilledTonalButton(
                onClick = tweetCounterState.onCopyClick,
                shape = RoundedCornerShape(12.dp),
                enabled = isCopyButtonEnabled,
                modifier = Modifier
                    .widthIn(min = 93.dp)
                    .heightIn(min = 40.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(29, 169, 112, 255),
                )
            ) {
                Text(text = stringResource(R.string.copy_text))
            }
            Spacer(modifier = Modifier.weight(1f))
            FilledTonalButton(
                onClick = tweetCounterState.onClearClick,
                shape = RoundedCornerShape(12.dp),
                enabled = isClearButtonEnabled,
                modifier = Modifier
                    .widthIn(min = 93.dp)
                    .heightIn(min = 40.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(220, 6, 37, 255),
                )
            ) {
                Text(text = stringResource(R.string.clear_text))
            }
        }
        FilledTonalButton(
            onClick = tweetCounterState.onPostClick,
            shape = RoundedCornerShape(12.dp),
            enabled = isPostButtonEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(12, 169, 244, 255),
            )
        ) {
            Text(
                text = stringResource(R.string.post_tweet),
                fontSize = 18.sp,
                fontWeight = FontWeight(700)
            )
        }
    }
}

data class TweetCounterState(
    val charactersCount: StateFlow<Int>,
    val tweetContent: StateFlow<String>,
    val isPostButtonEnabled: StateFlow<Boolean>,
    val isClearButtonEnabled: StateFlow<Boolean>,
    val isCopyButtonEnabled: StateFlow<Boolean>,
    val characterRemaining: StateFlow<Int>,
    val onValueChange: (String) -> Unit,
    val onCopyClick: () -> Unit,
    val onClearClick: () -> Unit,
    val onPostClick: () -> Unit
)