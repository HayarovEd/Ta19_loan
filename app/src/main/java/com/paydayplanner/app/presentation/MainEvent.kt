package com.paydayplanner.app.presentation

import com.paydayplanner.app.domain.model.StatusApplication
import com.paydayplanner.app.domain.model.basedto.BaseState


sealed class MainEvent {
    data object Reconnect: MainEvent()

    class OnChangeStatusApplication(val statusApplication: StatusApplication): MainEvent()
    class OnChangeBaseState(val baseState: BaseState): MainEvent()

    class OnGoToWeb(
        val urlOffer: String,
        val nameOffer: String
        ): MainEvent()
}
