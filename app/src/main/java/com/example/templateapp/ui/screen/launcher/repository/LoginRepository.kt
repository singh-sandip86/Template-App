package com.example.templateapp.ui.screen.launcher.repository

import com.example.templateapp.dto.response.login.LoginResponse
import retrofit2.Response

interface LoginRepository {
    suspend fun loginUser(email: String, password: String): Response<LoginResponse>
}