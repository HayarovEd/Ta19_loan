package com.paydayplanner.domain

import com.paydayplanner.data.Resource
import com.paydayplanner.domain.model.basedto.BaseDto

interface RepositoryServer {
    suspend fun getDataDb() : Resource<BaseDto>
}