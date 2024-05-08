package com.example.templateapp.ui.screen.dashboard.repository

import androidx.paging.PagingData
import com.example.templateapp.dto.response.dashboard.Transaction
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {

    fun getTransaction(
        search: String?,
        sortBy: String?,
        sortOrder: String?,
        status: String?,
        type: String?,
        transactionType: String?,
    ): Flow<PagingData<Transaction>>
}