
package com.expensemanager.plus.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.expensemanager.plus.R
import com.expensemanager.plus.data.VALUE_ONE
import com.expensemanager.plus.domain.model.ElementOffer
import com.expensemanager.plus.domain.model.StatusApplication
import com.expensemanager.plus.domain.model.basedto.BaseState
import com.expensemanager.plus.domain.model.basedto.CardsCredit
import com.expensemanager.plus.ui.theme.green
import com.expensemanager.plus.ui.theme.lightGrey
import com.paydayplanner.presentation.MainEvent

@Composable
fun ItemCreditCard(
    modifier: Modifier = Modifier,
    card: CardsCredit,
    onEvent: (MainEvent) -> Unit,
    baseState: BaseState
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
           /* .shadow(
                elevation = 20.dp,
                spotColor = grey,
                ambientColor = grey,
                shape = RoundedCornerShape(20.dp)
            )*/
            //.border(width = 3.dp, color = grey, shape = RoundedCornerShape(15.dp))
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = lightGrey)
            .padding(13.dp)
    ) {
        AsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    onEvent(
                        MainEvent.OnChangeStatusApplication(
                            StatusApplication.Offer(
                                currentBaseState = baseState,
                                ElementOffer(
                                    nameButton = card.orderButtonText,
                                    name = card.name,
                                    pathImage = card.screen,
                                    rang = card.score,
                                    description = card.description,
                                    amount = card.summPrefix + " " + card.summMin + " " + card.summMid + " " + card.summMax + " " + card.summPostfix,
                                    bet = card.percentPrefix + " " + card.percent + " " + card.percentPostfix,
                                    term = card.termPrefix + " " + card.termMin + " " + card.termMid + " " + card.termMax + " " + card.termPostfix,
                                    showMir = card.showMir,
                                    showVisa = card.showVisa,
                                    showMaster = card.showMastercard,
                                    showQiwi = card.showQiwi,
                                    showYandex = card.showYandex,
                                    showCache = card.showCash,
                                    showPercent = card.hidePercentFields,
                                    showTerm = card.hideTermFields,
                                    order = card.order
                                )
                            )
                        )
                    )
                },
            model = card.screen,
            contentScale = ContentScale.FillWidth,
            contentDescription = ""
        )
        Spacer(modifier = modifier.height(15.dp))
        Text(
            color = green,
            fontStyle = FontStyle(R.font.gotham),
            fontSize = 18.sp,
            fontWeight = FontWeight(500),
            text = card.name
        )
        /*Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                color = black,
                fontStyle = FontStyle(R.font.soyuz_grotesk_bold),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                text = card.name
            )
            Rang(
                rang = card.score
            )
        }*/
        Spacer(modifier = modifier.height(15.dp))
        RowData(
            title = stringResource(id = R.string.amount),
            content = card.summPrefix +" " + card.summMin +" " + card.summMid +" " + card.summMax +" " + card.summPostfix
        )
        if (card.hidePercentFields == VALUE_ONE) {
            //Spacer(modifier = modifier.height(8.dp))
           /* Divider(
                thickness = 1.dp,
                color = grey
            )*/
            RowData(
                title = stringResource(id = R.string.bet),
                content = card.percentPrefix +" " + card.percent +" " + card.percentPostfix
            )
        }
        if (card.hideTermFields == VALUE_ONE) {
            /*Divider(
                thickness = 1.dp,
                color = grey
            )*/
            //Spacer(modifier = modifier.height(8.dp))
            RowData(
                title = stringResource(id = R.string.term),
                content = card.termPrefix +" "+ card.termMin +" " + card.termMid +" " + card.termMax +" " + card.termPostfix
            )
        }
        Spacer(modifier = modifier.height(20.dp))
        RowCard(
            showVisa = card.showVisa,
            showMaster = card.showMastercard,
            showYandex = card.showYandex,
            showMir = card.showMir,
            showQivi = card.showQiwi,
            showCache = card.showCash
        )
        Spacer(modifier = modifier.height(20.dp))
        RowButtons(
            titleOffer = card.orderButtonText,
            onEvent = onEvent,
            currentBaseState = baseState,
            name = card.name,
            pathImage = card.screen,
            rang = card.score,
            description = card.description,
            amount = card.summPrefix +" " + card.summMin +" " + card.summMid +" " + card.summMax +" " + card.summPostfix,
            bet  = card.percentPrefix +" " + card.percent +" " + card.percentPostfix,
            term = card.termPrefix +" "+ card.termMin +" " + card.termMid +" " + card.termMax +" " + card.termPostfix,
            showMir = card.showMir,
            showVisa = card.showVisa,
            showMaster = card.showMastercard,
            showQiwi = card.showQiwi,
            showYandex = card.showYandex,
            showCache = card.showCash,
            showPercent = card.hidePercentFields,
            showTerm = card.hideTermFields,
            order = card.order,
        )
    }
}
