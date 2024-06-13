package com.paydayplanner.app.domain

import com.paydayplanner.app.data.Resource
import com.paydayplanner.app.domain.model.basedto.BaseDto

interface RepositoryServer {
    suspend fun getDataDb() : Resource<BaseDto>
}