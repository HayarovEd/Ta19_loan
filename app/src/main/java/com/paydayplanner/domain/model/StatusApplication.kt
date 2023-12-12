package com.paydayplanner.domain.model

import com.paydayplanner.domain.model.basedto.BaseState

sealed class StatusApplication {
    data object Loading: StatusApplication()
    data object Mock : StatusApplication()
    data object Splash: StatusApplication()
    class Connect (val baseState: BaseState): StatusApplication()

    class Offer (
        val currentBaseState: BaseState,
        val elementOffer: ElementOffer
        ): StatusApplication()

    class Info (
        val currentBaseState: BaseState,
        val content: String
        ): StatusApplication()

    class Web (
        val url: String,
        val offerName: String
    ): StatusApplication()

    data object NoConnect: StatusApplication()
}
