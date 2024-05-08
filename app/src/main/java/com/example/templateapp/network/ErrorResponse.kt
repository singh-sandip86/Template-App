package com.example.templateapp.network

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code") val code: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("success") val success: Boolean? = null,
    @SerializedName("status_code") val statusCode: Int? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("error") val error: JsonObject? = null,
    @SerializedName("errors") val errors: JsonObject? = null
)
