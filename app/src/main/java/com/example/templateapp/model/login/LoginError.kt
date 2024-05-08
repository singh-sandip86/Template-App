package com.example.templateapp.model.login

import com.example.templateapp.utils.empty

data class LoginError(
    val emailInvalid: Boolean = false,
    val errorEmail: String = String.empty(),
    val passwordInvalid: Boolean = false,
    val errorPassword: String = String.empty()
)
