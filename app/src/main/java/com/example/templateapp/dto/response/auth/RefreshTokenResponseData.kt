package com.example.templateapp.dto.response.auth

import com.google.gson.annotations.SerializedName

data class RefreshTokenResponseData(
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("refresh_token")
    val refreshToken: String?,
    @SerializedName("expires_at")
    val expiresAt: String?,
    @SerializedName("token_type")
    val tokenType: String?
)
