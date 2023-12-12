package com.expensemanager.plus.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.expensemanager.plus.R
import com.expensemanager.plus.R.string
import com.expensemanager.plus.data.VALUE_ONE
import com.expensemanager.plus.domain.model.basedto.BaseDto
import com.expensemanager.plus.domain.model.basedto.BaseState
import com.expensemanager.plus.domain.model.basedto.BaseState.Cards
import com.expensemanager.plus.domain.model.basedto.BaseState.Credits
import com.expensemanager.plus.domain.model.basedto.BaseState.Loans
import com.expensemanager.plus.domain.model.basedto.CardsCredit
import com.expensemanager.plus.domain.model.basedto.CardsDebit
import com.expensemanager.plus.domain.model.basedto.CardsInstallment
import com.expensemanager.plus.ui.theme.baseBackground
import com.expensemanager.plus.ui.theme.baseText
import com.expensemanager.plus.ui.theme.green
import com.expensemanager.plus.ui.theme.grey
import com.paydayplanner.presentation.MainEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectScreen(
    modifier: Modifier = Modifier,
    db: BaseDto,
    baseState: BaseState,
    creditCards: List<CardsCredit>,
    debitCards: List<CardsDebit>,
    installmentCards: List<CardsInstallment>,
    onEvent: (MainEvent) -> Unit,
    onClickPrimary: () -> Unit,
    onClickLoans: () -> Unit,
    onClickCards: () -> Unit,
    onClickCredits: () -> Unit,
    onClickRules: () -> Unit,
    loanLazyState: LazyListState,
    creditLazyState: LazyListState,
    creditCardloanLazyState: LazyListState,
    debitCardLazyState: LazyListState,
    instalmentCardLazyState: LazyListState,
) {
    val title = when (baseState) {
        is Cards -> stringResource(id = string.cards)
        Credits -> stringResource(id = string.credits)
        Loans -> stringResource(id = string.loans)
        is BaseState.WebPrimary -> db.appConfig.namePrimary ?: ""
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
                            .padding(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = modifier
                                .fillMaxWidth(),
                            color = baseText,
                            fontStyle = FontStyle(R.font.gotham),
                            fontSize = 22.sp,
                            fontWeight = FontWeight(600),
                            text = title,
                            textAlign = TextAlign.Center
                        )
                        /* IconButton(onClick = onClickRules) {
                             Icon(
                                 imageVector = ImageVector.vectorResource(id = R.drawable.info),
                                 tint = black,
                                 contentDescription = "")
                         }*/
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = baseBackground
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = baseBackground,
                modifier = modifier
                //.clip(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    if (!db.appConfig.showedIconPrimary.isNullOrEmpty()
                        && db.appConfig.showedIconPrimary == VALUE_ONE
                        && !db.appConfig.namePrimary.isNullOrEmpty()
                        && !db.appConfig.urlPrimary.isNullOrEmpty()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconButton(
                                modifier = modifier.size(72.dp),
                                onClick = onClickPrimary
                            ) {
                                Image(
                                    imageVector = ImageVector.vectorResource(drawable.money_1),
                                    contentDescription = ""
                                )
                            }
                            /*Text(
                                color = if (baseState is Loans) green else lightGray,
                                fontStyle = FontStyle(R.font.onest_400),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Normal,
                                text = db.appConfig.namePrimary
                            )*/
                        }
                    }
                    if (!db.loans.isNullOrEmpty()) {
                        ItemBottomBar(
                            color = if (baseState is Loans) green else grey,
                            content = stringResource(id = string.loans),
                            icon = if (baseState is Loans) ImageVector.vectorResource(id = drawable.loans_fill) else ImageVector.vectorResource(
                                id = drawable.loans
                            ),
                            onClick = onClickLoans
                        )
                    }
                    if (!db.cards.isNullOrEmpty()) {
                        ItemBottomBar(
                            color = if (baseState is Cards) green else grey,
                            content = stringResource(id = string.cards),
                            icon = if (baseState is Cards) ImageVector.vectorResource(id = drawable.cards_fill) else ImageVector.vectorResource(
                                id = drawable.cards
                            ),
                            onClick = onClickCards
                        )
                    }
                    if (!db.credits.isNullOrEmpty()) {
                        ItemBottomBar(
                            color = if (baseState is Credits) green else grey,
                            content = stringResource(id = string.credits),
                            icon = if (baseState is Credits) ImageVector.vectorResource(id = drawable.credits_fill) else ImageVector.vectorResource(
                                id = drawable.credits
                            ),
                            onClick = onClickCredits
                        )
                    }
                }

            }
        }
    ) { valuePaddings ->
        when (baseState) {
            is Cards -> {
                CardsScreen(
                    valuePaddings = valuePaddings,
                    creditCards = creditCards,
                    debitCards = debitCards,
                    installmentCards = installmentCards,
                    typeCard = baseState.typeCard,
                    onEvent = onEvent,
                    baseState = baseState,
                    creditCardloanLazyState = creditCardloanLazyState,
                    debitCardLazyState = debitCardLazyState,
                    instalmentCardLazyState = instalmentCardLazyState,
                )
            }

            Credits -> {
                Credits(
                    valuePaddings = valuePaddings,
                    credits = db.credits,
                    onEvent = onEvent,
                    baseState = baseState,
                    creditLazyState = creditLazyState
                )
            }

            Loans -> {
                Loans(
                    valuePaddings = valuePaddings,
                    loans = db.loans,
                    onEvent = onEvent,
                    baseState = baseState,
                    loanLazyState = loanLazyState
                )
            }

            is BaseState.WebPrimary -> {
                WebViewScreenPrimary(
                    url = db.appConfig.urlPrimary ?: "",
                    offerName = db.appConfig.namePrimary ?: "",
                    valuePaddings = valuePaddings,
                    onEvent = onEvent
                )
            }
        }
    }
}

@Composable
fun ItemBottomBar(
    color: Color,
    icon: ImageVector,
    content: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = color
            ),
            onClick = onClick
        ) {
            Icon(imageVector = icon, contentDescription = "")
        }
        Text(
            color = color,
            fontStyle = FontStyle(R.font.gotham),
            fontSize = 11.sp,
            fontWeight = FontWeight(700),
            text = content
        )
    }
}
