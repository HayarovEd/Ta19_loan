package com.paydayplanner.app.presentation

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import com.paydayplanner.app.R
import com.paydayplanner.app.data.VALUE_ONE
import com.paydayplanner.app.domain.model.ElementOffer
import com.paydayplanner.app.domain.model.StatusApplication
import com.paydayplanner.app.domain.model.basedto.BaseState
import com.paydayplanner.app.theme.baseBackground
import com.paydayplanner.app.theme.baseText
import com.paydayplanner.app.theme.burgundy
import com.paydayplanner.app.theme.shadow

@SuppressLint("ResourceAsColor")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfferScreen(
    modifier: Modifier = Modifier,
    elementOffer: ElementOffer,
    baseState: BaseState,
    onEvent: (MainEvent) -> Unit,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = baseBackground
                ),
                title = {
                    Row(
                        modifier = modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                onEvent(
                                    MainEvent.OnChangeStatusApplication(
                                        StatusApplication.Connect(baseState)
                                    )
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = burgundy
                            ),
                            contentPadding = PaddingValues(horizontal = 8.dp)
                        ) {
                            Icon(
                                modifier = modifier.padding(horizontal = 10.dp),
                                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_back_24),
                                tint = baseBackground,
                                contentDescription = ""
                            )
                        }
                        Spacer(modifier = modifier.width(15.dp))
                        Text(
                            color = baseText,
                            fontStyle = FontStyle(R.font.sf_pro_text),
                            fontSize = 19.sp,
                            fontWeight = FontWeight(500),
                            text = elementOffer.name
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = baseBackground
            ) {
                Button(
                    modifier = modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50.dp),
                    contentPadding = PaddingValues(
                        vertical = 16.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = burgundy,
                        contentColor = baseBackground,
                    ),
                    onClick = {
                        onEvent(
                            MainEvent.OnGoToWeb(
                            urlOffer = elementOffer.order,
                            nameOffer = elementOffer.name
                        )
                    ) }
                ) {
                    Text(
                        text = stringResource(id = R.string.checkout),
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_text)),
                            fontWeight = FontWeight(400),
                        )
                    )
                }
                /* Row (
                     modifier = modifier
                         .fillMaxWidth()
                         .padding(horizontal = 24.dp),
                     verticalAlignment = Alignment.CenterVertically
                 ) {
                     OutlinedButton(
                         modifier = modifier.weight(1f),
                         onClick = {
                         onEvent(
                             MainEvent.OnChangeStatusApplication(
                                 StatusApplication.Connect(baseState)
                             )
                         )
                     },
                         shape = RoundedCornerShape(5.dp),
                         border = BorderStroke(
                             width = 2.dp,
                             color = blue
                         ),
                         contentPadding = PaddingValues(
                             vertical = 16.dp,
                         ),
                         colors = ButtonDefaults.outlinedButtonColors(
                             containerColor = baseBackground,
                             contentColor = blue,
                         ),
                     ) {
                         Icon(
                             imageVector = ImageVector.vectorResource(id = R.drawable.route_left),
                             tint = blue,
                             contentDescription = ""
                         )
                     }
                     Spacer(modifier = modifier.width(12.dp))
                     Button(
                         modifier = modifier
                             .weight(3f),
                             //.padding(horizontal = 15.dp)
                             //.fillMaxWidth(),
                         shape = RoundedCornerShape(5.dp),
                         contentPadding = PaddingValues(
                             vertical = 16.dp,
                         ),
                         colors = ButtonDefaults.buttonColors(
                             containerColor = blue,
                             contentColor = baseBackground,
                         ),
                         onClick = {
                             onEvent(
                                 MainEvent.OnGoToWeb(
                                     urlOffer = elementOffer.order,
                                     nameOffer = elementOffer.name
                                 )
                             )
                         }
                     ) {
                         Text(
                             text = stringResource(id = R.string.checkout),
                             style = TextStyle(
                                 fontSize = 20.sp,
                                 fontFamily = FontFamily(Font(R.font.inter)),
                                 fontWeight = FontWeight(600),
                             )
                         )
                     }
                 }*/
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                //.shadow(elevation = 10.dp, spotColor = grey, ambientColor = grey, shape = RoundedCornerShape(20.dp))
                .background(color = baseBackground)
                .padding(20.dp)
            //.clip(shape = RoundedCornerShape(20.dp))
            //.background(color = lightGrey)
            //.padding(16.dp),
        ) {
            AsyncImage(
                modifier = modifier
                    .shadow(elevation = 10.dp, spotColor = shadow, ambientColor = shadow)
                    .fillMaxWidth(),
                model = elementOffer.pathImage,
                contentScale = ContentScale.FillWidth,
                contentDescription = ""
            )
            Spacer(modifier = modifier.height(20.dp))
            AndroidView(
                modifier = modifier
                    .fillMaxWidth(),
                // .clip(shape = RoundedCornerShape(5.dp))
                // .background(color = white)
                //.padding(5.dp),
                factory = { context -> TextView(context) },
                update = {
                    it.setTextColor(R.color.white)
                    it.text = HtmlCompat.fromHtml(
                        elementOffer.description,
                        HtmlCompat.FROM_HTML_MODE_COMPACT
                    )
                }
            )
            Spacer(modifier = modifier.height(20.dp))
            /*Text(
                text = elementOffer.name,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.montserrat)),
                    fontWeight = FontWeight(600),
                ),
                color = baseText,
            )
            Spacer(modifier = modifier.height(12.dp))*/
            RowData(
                title = stringResource(id = R.string.amount),
                content = elementOffer.amount
            )
            if (elementOffer.showPercent == VALUE_ONE) {
                /*Divider(
                    thickness = 1.dp,
                    color = grey
                )*/
                //Spacer(modifier = modifier.height(8.dp))
                RowData(
                    title = stringResource(id = R.string.bet),
                    content = elementOffer.bet
                )
            }
            if (elementOffer.showTerm == VALUE_ONE) {
                //Spacer(modifier = modifier.height(8.dp))
                /*Divider(
                    thickness = 1.dp,
                    color = grey
                )*/
                RowData(
                    title = stringResource(id = R.string.term),
                    content = elementOffer.term
                )
            }
            Spacer(modifier = modifier.height(20.dp))
            RowCard(
                showVisa = elementOffer.showVisa,
                showMaster = elementOffer.showMaster,
                showYandex = elementOffer.showYandex,
                showMir = elementOffer.showMir,
                showQivi = elementOffer.showQiwi,
                showCache = elementOffer.showCache
            )
            /*Spacer(modifier = modifier.height(35.dp))
            Button(
                modifier = modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(25.dp),
                contentPadding = PaddingValues(
                    vertical = 7.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = blue,
                    contentColor = white,
                ),
                onClick = {
                    onEvent(
                        MainEvent.OnGoToWeb(
                            urlOffer = elementOffer.order,
                            nameOffer = elementOffer.name
                        )
                    )
                }
            ) {
                Text(
                    text = stringResource(id = R.string.checkout),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(font.nunito)),
                        fontWeight = FontWeight(700),
                    )
                )
            }*/
        }
    }
}