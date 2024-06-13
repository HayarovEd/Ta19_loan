package com.paydayplanner.app.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AffSub3(
    @SerializedName ("application_token")
    val applicationToken: String,
    @SerializedName ("user_id")
    val userId: String,
    @SerializedName ("payload_affsub3")
    val payloadAffsub: String
): Serializable
