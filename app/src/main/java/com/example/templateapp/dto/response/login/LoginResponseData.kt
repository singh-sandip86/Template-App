package com.example.templateapp.dto.response.login

import com.example.templateapp.model.login.User
import com.example.templateapp.utils.empty
import com.google.gson.annotations.SerializedName

data class LoginResponseData(
    @SerializedName("user_id")
    val userId: Int? = null,
    @SerializedName("first_name")
    val firstName: String? = null,
    @SerializedName("middle_name")
    val middleName: String? = null,
    @SerializedName("last_name")
    val lastName: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("role")
    val role: Int? = null,
    @SerializedName("token_type")
    val tokenType: String? = null,
    @SerializedName("access_token")
    val accessToken: String? = null,
    @SerializedName("refresh_token")
    val refreshToken: String? = null
)

fun LoginResponseData.toUser(): User {
    return User(
        email ?: String.empty(),
        "${(firstName ?: String.empty())} ${(middleName ?: String.empty())} ${(lastName ?: String.empty())}",
        firstName ?: String.empty(),
        middleName ?: String.empty(),
        lastName ?: String.empty(),
        accessToken ?: String.empty(),
        refreshToken ?: String.empty(),
        userId
    )
}