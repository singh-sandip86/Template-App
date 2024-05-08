package com.example.templateapp.api

import com.example.templateapp.dto.request.auth.RefreshTokenRequest
import com.example.templateapp.dto.response.auth.RefreshTokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface AuthApiInterface {
    companion object {
        val REFRESH_TOKEN by ApiInterface.TemplateEnvironment(
            ApiInterface.AUTH_URL,
            "api/v1/refresh-token"
        )
    }

    @POST
    fun getAccessToken(
        @Url url: String = REFRESH_TOKEN,
        @Body accessTokenRequest: RefreshTokenRequest
    ): Call<RefreshTokenResponse>
}