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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eitadevelopment.tweetcounter.R

@Composable
fun ScreenContent(modifier: Modifier) {
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
                    text = "Characters Typed",
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    modifier = Modifier.padding(12.dp)
                )
                Text(
                    text = "0/280",
                    textAlign = TextAlign.Center,
                    fontSize = 26.sp,
                    fontWeight = FontWeight(500),
                    modifier = Modifier
                        .padding(1.dp)
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
                    text = "Characters Remaining",
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    modifier = Modifier.padding(12.dp)
                )
                Text(
                    text = "280",
                    textAlign = TextAlign.Center,
                    fontSize = 26.sp,
                    fontWeight = FontWeight(500),
                    modifier = Modifier
                        .padding(1.dp)
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
            value = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text(text = "Start typing! You can enter up to 280 characters",fontSize = 14.sp) },
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
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .widthIn(min = 93.dp)
                    .heightIn(min = 40.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(29, 169, 112, 255),
                )
            ) {
                Text(text = "Copy Text")
            }
            Spacer(modifier = Modifier.weight(1f))
            FilledTonalButton(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .widthIn(min = 93.dp)
                    .heightIn(min = 40.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(220, 6, 37, 255),
                )
            ) {
                Text(text = "Clear Text")
            }
        }
        FilledTonalButton(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(12, 169, 244, 255),
            )
        ) {
            Text(text = "Post tweet", fontSize = 18.sp, fontWeight = FontWeight(700))
        }
    }
}