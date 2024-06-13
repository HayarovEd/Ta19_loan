package com.paydayplanner.app.data

import android.util.Log
import com.paydayplanner.app.domain.RepositoryServer
import com.paydayplanner.app.data.Resource.Success
import com.paydayplanner.app.domain.model.basedto.BaseDto
import javax.inject.Inject

class RepositoryServerImpl @Inject constructor(
    private val apiServer: ApiServer
) : RepositoryServer {
    override suspend fun getDataDb(): Resource<BaseDto> {
        return try {
            val folder = apiServer.getDataDb()
            Log.d("DATADB", "dATA DB:${folder.loans.first().id}")
            Success(
                data = folder
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error")
        }
    }
}