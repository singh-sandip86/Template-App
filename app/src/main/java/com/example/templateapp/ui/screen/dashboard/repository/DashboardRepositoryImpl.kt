package com.example.templateapp.ui.screen.dashboard.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.templateapp.api.ApiInterface
import com.example.templateapp.common.Constants
import com.example.templateapp.dto.response.dashboard.Transaction
import com.example.templateapp.ui.screen.dashboard.adapter.DashboardPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : DashboardRepository {

    override fun getTransaction(
        search: String?,
        sortBy: String?,
        sortOrder: String?,
        status: String?,
        type: String?,
        transactionType: String?
    ): Flow<PagingData<Transaction>> = Pager(
        config = PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            prefetchDistance = Constants.PREFETCH_DISTANCE
        ),
        pagingSourceFactory = {
            DashboardPagingSource(
                apiInterface = apiInterface,
                search = search,
                sortBy = sortBy,
                sortOrder = sortOrder,
                status = status,
                type = type,
                transactionType = transactionType
            )
        }
    ).flow
}