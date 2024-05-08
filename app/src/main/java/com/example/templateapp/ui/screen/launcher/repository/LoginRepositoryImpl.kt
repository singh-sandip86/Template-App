package com.example.templateapp.ui.screen.launcher.repository

import com.example.templateapp.api.ApiInterface
import com.example.templateapp.dto.request.login.LoginRequest
import com.example.templateapp.dto.response.login.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : LoginRepository {

    override suspend fun loginUser(email: String, password: String): Response<LoginResponse> {
        return apiInterface.loginUser(
            request = LoginRequest(
                email = email,
                password = password
            )
        )
    }
}