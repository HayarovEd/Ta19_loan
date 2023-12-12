package com.expensemanager.plus.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.expensemanager.plus.R
import com.paydayplanner.presentation.MainEvent.Reconnect
import com.expensemanager.plus.ui.theme.baseBackground
import com.expensemanager.plus.ui.theme.baseText
import com.expensemanager.plus.ui.theme.green
import com.expensemanager.plus.ui.theme.secondText
import com.paydayplanner.presentation.MainEvent

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
            Spacer(modifier = modifier.height(50.dp))
            Text(
                text = stringResource(id = R.string.not_connect),
                fontSize = 18.sp,
                fontWeight = FontWeight(500),
                fontStyle = FontStyle(R.font.gotham),
                color = baseText,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(31.dp))
            Text(
                text = stringResource(id = R.string.try_internet),
                fontSize = 14.sp,
                fontWeight = FontWeight(400),
                fontStyle = FontStyle(R.font.gotham),
                color = secondText,
                textAlign = TextAlign.Center
            )
            /*Spacer(modifier = modifier.height(64.dp))
            Button(
                modifier = modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(25.dp),
                contentPadding = PaddingValues(
                    vertical = 9.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = blue,
                    contentColor = white,
                ),
                onClick = { onEvent(Reconnect) }
            ) {
                Text(
                    text = stringResource(id = R.string.reconnect),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.nunito)),
                        fontWeight = FontWeight(700),
                    )
                )
            }*/
        }
        Button(
            modifier = modifier
                .fillMaxWidth()
                .align(alignment = Alignment.BottomCenter),
            shape = RoundedCornerShape(10.dp),
            /*border = BorderStroke(
                width = 1.dp,
                color = green
            ),*/
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
        }
    }
}

@Preview
@Composable
fun SampleNoInternetScreen() {
    NoInternetScreen(onEvent = {})
}
