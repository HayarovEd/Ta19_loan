package com.paydayplanner.presentation

import com.paydayplanner.domain.model.StatusApplication
import com.paydayplanner.domain.model.basedto.BaseState


sealed class MainEvent {
    data object Reconnect: MainEvent()

    class OnChangeStatusApplication(val statusApplication: StatusApplication): MainEvent()
    class OnChangeBaseState(val baseState: BaseState): MainEvent()

    class OnGoToWeb(
        val urlOffer: String,
        val nameOffer: String
        ): MainEvent()
}
