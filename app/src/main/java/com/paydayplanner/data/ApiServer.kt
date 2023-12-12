package com.paydayplanner.data

import com.paydayplanner.domain.model.basedto.BaseDto
import retrofit2.http.GET

interface ApiServer {
    @GET ("536/db.json")
    suspend fun getDataDb () : BaseDto
}