package com.paydayplanner.app.domain.model.basedto


sealed class BaseState{
    data object Loans: BaseState()
}
