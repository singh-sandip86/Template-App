package com.example.templateapp.api

import com.example.templateapp.TemplateApp
import com.example.templateapp.dto.request.login.LoginRequest
import com.example.templateapp.dto.response.dashboard.TransactionsResponse
import com.example.templateapp.dto.response.login.LoginResponse
import com.example.templateapp.utils.empty
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface ApiInterface {

    companion object {
        val AUTH_URL = TemplateApp.environment.authUrl
        val TRANSACTION_URL = TemplateApp.environment.transactionUrl

        val USER_AUTHENTICATE by TemplateEnvironment(AUTH_URL, "api/v1/login")
        val TRANSACTIONS_LIST by TemplateEnvironment(TRANSACTION_URL, "api/v1/transactions")
    }

    @POST
    suspend fun loginUser(
        @Url url: String = USER_AUTHENTICATE,
        @Body request: LoginRequest,
    ): Response<LoginResponse>

    @GET
    suspend fun getTransaction(
        @Url url: String = TRANSACTIONS_LIST,
        @Query("page") page: Int,
        @Query("search") search: String? = null,
        @Query("sortBy") sortBy: String? = null,
        @Query("sordOrder") sortOrder: String? = null,
        @Query("status") status: String? = null,
        @Query("type") type: String? = null,
        @Query("transaction_type") transactionType: String? = null
    ): Response<TransactionsResponse>

    class TemplateEnvironment(
        private val baseUrl: String,
        private var urlPath: String,
        private val pathMap: Map<String, String>? = null,
    ) : ReadWriteProperty<Any?, String> {

        private var completeUrl: String = String.empty()

        override fun getValue(thisRef: Any?, property: KProperty<*>): String {
            replacePathValues()
            return baseUrl + urlPath
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            replacePathValues()
            completeUrl = baseUrl + urlPath
        }

        private fun replacePathValues() {
            if (!pathMap.isNullOrEmpty()) {
                pathMap.keys.forEach { key ->
                    pathMap[key]?.let {
                        urlPath = urlPath.replace(key, it)
                    }
                }
            }
        }
    }
}