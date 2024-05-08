package com.example.templateapp.network

import com.example.templateapp.api.AuthApiInterface
import com.example.templateapp.dto.request.auth.RefreshTokenRequest
import com.example.templateapp.preferences.SharedPrefManager
import com.example.templateapp.utils.empty
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val authApiInterface: AuthApiInterface
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return if (SharedPrefManager.refreshToken.isEmpty())
            fetchNewTokenAndUpdateInRequest(response)
        else null
    }

    @Synchronized
    private fun fetchNewTokenAndUpdateInRequest(response: Response): Request {
        val tokenResponse = authApiInterface.getAccessToken(
            accessTokenRequest = RefreshTokenRequest(
                refreshToken = SharedPrefManager.refreshToken
            )
        ).execute()

        tokenResponse.body()?.let { responseBody ->
            SharedPrefManager.token = responseBody.data?.accessToken ?: String.empty()
            SharedPrefManager.refreshToken = responseBody.data?.refreshToken ?: String.empty()
        }.run {
            // If token request fail and we are unable to receive new token then empty token parameter
            SharedPrefManager.token = String.empty()
            SharedPrefManager.refreshToken = String.empty()
        }

        // Overriding Authorization header only
        return response.request.newBuilder().header(
            "Authorization",
            "Bearer ${SharedPrefManager.token}"
        ).build()
    }
}