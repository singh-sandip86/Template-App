package com.example.templateapp.ui.base

import com.google.gson.annotations.SerializedName

abstract class BaseResponse<T> {
    @SerializedName("status_code")
    val statusCode: Int? = null

    @SerializedName("data")
    var data: T? = null

    @SerializedName("message")
    val message: String? = null

    @SerializedName("success")
    val success: Boolean? = null
}