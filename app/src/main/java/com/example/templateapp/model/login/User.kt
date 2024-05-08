package com.example.templateapp.model.login

import com.example.templateapp.utils.empty

data class User(
    val email: String = String.empty(),
    val name: String = String.empty(),
    val firstName: String = String.empty(),
    val middleName: String = String.empty(),
    val lastName: String = String.empty(),
    val accessToken: String = String.empty(),
    val refreshToken: String = String.empty(),
    val userId: Int? = -1
)
