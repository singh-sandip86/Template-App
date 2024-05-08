package com.example.templateapp.dto.request.login

import com.example.templateapp.common.Constants
import com.google.gson.annotations.SerializedName
import java.util.TimeZone

data class LoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("source")
    val source: String = Constants.SOURCE,
    @SerializedName("timezone")
    val timezone: String = TimeZone.getDefault().id,
)
