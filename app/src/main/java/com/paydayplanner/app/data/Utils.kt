package com.paydayplanner.app.data

import com.paydayplanner.app.domain.model.StatusApplication
import com.paydayplanner.app.domain.model.basedto.Loan
import com.paydayplanner.app.domain.model.ElementOffer
import com.paydayplanner.app.domain.model.StatusApplication.Connect
import com.paydayplanner.app.domain.model.StatusApplication.Offer
import com.paydayplanner.app.domain.model.basedto.BaseState.Loans


fun String.setStatusByPush(
    loans:List<Loan>,
): StatusApplication {
    val subString = this.split("/")
    if (subString.size == 1) {
        return when (subString.first()) {
            MESSAGE_LOANS -> {
                Connect(Loans)
            }

            else -> {
                Connect(Loans)
            }
        }
    } else {
        val position = subString.last().toInt()
        return when (subString.first()) {
            MESSAGE_LOANS -> {
                if (position<=loans.size-1) {
                    val loan = loans[position]
                    Offer(
                        currentBaseState = Loans,
                        elementOffer = ElementOffer(
                            nameButton = loan.orderButtonText,
                            name = loan.name,
                            pathImage = loan.screen,
                            rang = loan.score,
                            description = loan.description,
                            amount = loan.summPrefix + " " + loan.summMin + " " + loan.summMid + " " + loan.summMax + " " + loan.summPostfix,
                            bet = loan.percentPrefix + " " + loan.percent + " " + loan.percentPostfix,
                            term = loan.termPrefix + " " + loan.termMin + " " + loan.termMid + " " + loan.termMax + " " + loan.termPostfix,
                            showMir = loan.showMir,
                            showVisa = loan.showVisa,
                            showMaster = loan.showMastercard,
                            showQiwi = loan.showQiwi,
                            showYandex = loan.showYandex,
                            showCache = loan.showCash,
                            showPercent = loan.hidePercentFields,
                            showTerm = loan.hideTermFields,
                            order = loan.order
                        )
                    )
                } else {
                    Connect(Loans)
                }
            }

            else -> {
                val loan = loans[0]
                Offer(
                    currentBaseState = Loans,
                    elementOffer = ElementOffer(
                        nameButton = loan.orderButtonText,
                        name = loan.name,
                        pathImage = loan.screen,
                        rang = loan.score,
                        description = loan.description,
                        amount = loan.summPrefix + " " + loan.summMin + " " + loan.summMid + " " + loan.summMax + " " + loan.summPostfix,
                        bet = loan.percentPrefix + " " + loan.percent + " " + loan.percentPostfix,
                        term = loan.termPrefix + " " + loan.termMin + " " + loan.termMid + " " + loan.termMax + " " + loan.termPostfix,
                        showMir = loan.showMir,
                        showVisa = loan.showVisa,
                        showMaster = loan.showMastercard,
                        showQiwi = loan.showQiwi,
                        showYandex = loan.showYandex,
                        showCache = loan.showCash,
                        showPercent = loan.hidePercentFields,
                        showTerm = loan.hideTermFields,
                        order = loan.order
                    )
                )
            }
        }

    }
}