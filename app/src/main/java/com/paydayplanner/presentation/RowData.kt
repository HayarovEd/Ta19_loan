package com.expensemanager.plus.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.expensemanager.plus.R
import com.expensemanager.plus.ui.theme.baseText
import com.expensemanager.plus.ui.theme.lightGrey
import com.expensemanager.plus.ui.theme.secondText

@Composable
fun RowData(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    fontWeight: Int = 400,
    colorBackground: Color = lightGrey
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .background(color = colorBackground)
            .padding(vertical = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            color = secondText,
            fontStyle = FontStyle(R.font.gotham),
            fontSize = 13.sp,
            fontWeight = FontWeight(400),
            text = title,
            textAlign = TextAlign.Start
        )
        Text(
            color = baseText,
            fontStyle = FontStyle(R.font.gotham),
            fontSize = 13.sp,
            fontWeight = FontWeight(fontWeight),
            text = content,
            textAlign = TextAlign.End
        )
    }
}
/*
@Preview
@Composable
private fun SampleRowData () {
    RowData(
        title = stringResource(id = R.string.bet),
        content = "elementOffer.bet"
    )
}*/
