package com.paydayplanner.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.paydayplanner.domain.model.StatusApplication

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Sample(
    viewModel: MainViewModel = hiltViewModel(),
) {

    val state = viewModel.state.collectAsState()

    val loanLazyState = rememberLazyListState()

    when (val currentState = state.value.statusApplication) {
        is StatusApplication.Connect -> {
            ConnectScreen(
                baseState = currentState.baseState,
                db = state.value.dbData!!,
                loanLazyState = loanLazyState,
                onEvent = viewModel::onEvent
            )
        }

        StatusApplication.Loading -> {
            LoadingScreen()
        }

        is StatusApplication.Mock -> {
            NoInternetScreen(onEvent = viewModel::onEvent)
        }

        is StatusApplication.Info -> {
        }

        is StatusApplication.Offer -> {
            OfferScreen(
                elementOffer = (state.value.statusApplication as StatusApplication.Offer).elementOffer,
                baseState = (state.value.statusApplication as StatusApplication.Offer).currentBaseState,
                onEvent = viewModel::onEvent,
            )
        }

        is StatusApplication.Web -> {
            WebViewScreen(
                url = currentState.url,
                offerName = currentState.offerName,
                onEvent = viewModel::onEvent,
            )
        }

        StatusApplication.NoConnect -> {
            NoInternetScreen(onEvent = viewModel::onEvent)
        }

        StatusApplication.Splash -> {
            SplashScreen()
        }
    }

}