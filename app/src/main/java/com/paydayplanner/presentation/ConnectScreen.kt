package com.paydayplanner.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paydayplanner.R
import com.paydayplanner.R.string
import com.paydayplanner.domain.model.basedto.BaseDto
import com.paydayplanner.domain.model.basedto.BaseState
import com.paydayplanner.domain.model.basedto.BaseState.Loans
import com.paydayplanner.ui.theme.baseBackground
import com.paydayplanner.ui.theme.baseText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectScreen(
    modifier: Modifier = Modifier,
    db: BaseDto,
    baseState: BaseState,
    onEvent: (MainEvent) -> Unit,
    loanLazyState: LazyListState,
) {
    val title = when (baseState) {
        Loans -> stringResource(id = string.loans)
    }
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 50.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = modifier
                                .fillMaxWidth(),
                            color = baseText,
                            fontStyle = FontStyle(R.font.sf_pro_text),
                            fontSize = 20.sp,
                            fontWeight = FontWeight(500),
                            text = title,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = baseBackground
                )
            )
        }
    ) { valuePaddings ->
        when (baseState) {
            Loans -> {
                Loans(
                    valuePaddings = valuePaddings,
                    loans = db.loans,
                    onEvent = onEvent,
                    baseState = baseState,
                    loanLazyState = loanLazyState
                )
            }
        }
    }
}
