package com.paydayplanner.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paydayplanner.R
import com.paydayplanner.presentation.MainEvent.Reconnect
import com.paydayplanner.ui.theme.baseBackground
import com.paydayplanner.ui.theme.burgundy

@Composable
fun NoInternetScreen(
    modifier: Modifier = Modifier,
    onEvent: (MainEvent) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = baseBackground)
            .padding(27.dp)
    ) {
        Column(
            modifier = modifier.align(alignment = Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = modifier.size(250.dp),
                painter = painterResource(
                    id = R.drawable.no_connection
                ),
                contentDescription = ""
            )
            Spacer(modifier = modifier.height(60.dp))
            Text(
                text = stringResource(id = R.string.not_connect),
                fontSize = 20.sp,
                fontWeight = FontWeight(500),
                fontStyle = FontStyle(R.font.sf_pro_text),
                color = burgundy,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(40.dp))
            Text(
                text = stringResource(id = R.string.try_internet),
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                fontStyle = FontStyle(R.font.sf_pro_text),
                color = burgundy,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(40.dp))
            Button(
                modifier = modifier,
                shape = RoundedCornerShape(50.dp),
                contentPadding = PaddingValues(
                    vertical = 16.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = burgundy,
                    contentColor = baseBackground,
                ),
                onClick = { onEvent(Reconnect) }
            ) {
                Text(
                    text = stringResource(id = R.string.reconnect),
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font(R.font.sf_pro_text)),
                        fontWeight = FontWeight(400),
                    )
                )
            }
        }
        /*Button(
            modifier = modifier
                .fillMaxWidth()
                .align(alignment = Alignment.BottomCenter),
            shape = RoundedCornerShape(10.dp),
            *//*border = BorderStroke(
                width = 1.dp,
                color = green
            ),*//*
            contentPadding = PaddingValues(
                vertical = 16.dp
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = green,
                contentColor = baseBackground,
            ),
            onClick = { onEvent(Reconnect) }
        ) {
            Text(
                text = stringResource(id = R.string.reconnect),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.gotham)),
                    fontWeight = FontWeight(500),
                )
            )
        }*/
    }
}

@Preview
@Composable
fun SampleNoInternetScreen() {
    NoInternetScreen(onEvent = {})
}
