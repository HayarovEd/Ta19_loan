
package com.paydayplanner.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paydayplanner.domain.model.basedto.BaseState
import com.paydayplanner.domain.model.basedto.Loan
import com.paydayplanner.ui.theme.baseBackground

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Loans(
    modifier: Modifier = Modifier,
    valuePaddings: PaddingValues,
    loans: List<Loan>,
    onEvent: (MainEvent) -> Unit,
    baseState: BaseState,
    loanLazyState: LazyListState,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = baseBackground)
            .padding(valuePaddings),
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = loanLazyState
        ) {
            items(loans) { loan ->
                ItemLoan(
                    loan = loan,
                    onEvent = onEvent,
                    baseState = baseState,
                )
            }
        }
    }
}
