package com.example.templateapp.utils

import com.example.templateapp.network.ErrorResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import javax.inject.Inject

class ErrorUtils @Inject constructor(private val gson: Gson) {

    fun parseError(response: Response<*>): ErrorResponse {
        val errorBody = response.errorBody()?.string()
        errorBody?.let { error ->
            return parseErrorResponse(error)
        }
        return ErrorResponse()
    }

    private fun parseErrorResponse(errorBody: String): ErrorResponse {
        val type = object : TypeToken<ErrorResponse>() {}.type
        return gson.fromJson(errorBody, type)
    }
}