package com.paydayplanner.domain.model.basedto


sealed class BaseState{
    data object Loans: BaseState()
}
