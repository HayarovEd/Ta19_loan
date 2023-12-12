package com.expensemanager.plus.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.expensemanager.plus.R
import com.expensemanager.plus.ui.theme.green

@Preview
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = green)
            .padding(25.dp)
    ) {
        Image(
            modifier = modifier
                .fillMaxWidth()
                .align(alignment = Alignment.Center),
            painter = painterResource(id = R.drawable.logo_no_fon),
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )
    }
}