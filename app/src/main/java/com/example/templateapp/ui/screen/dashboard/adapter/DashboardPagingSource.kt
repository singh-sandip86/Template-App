package com.example.templateapp.ui.screen.dashboard.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.templateapp.api.ApiInterface
import com.example.templateapp.dto.response.dashboard.Transaction

class DashboardPagingSource(
    private val apiInterface: ApiInterface,
    private val search: String?,
    private val sortBy: String?,
    private val sortOrder: String?,
    private val status: String?,
    private val type: String?,
    private val transactionType: String?,
) : PagingSource<Int, Transaction>() {

    override fun getRefreshKey(state: PagingState<Int, Transaction>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Transaction> {
        return try {
            val position = params.key ?: 1
            val response = apiInterface.getTransaction(
                page = position,
                search = search,
                sortBy = sortBy,
                sortOrder = sortOrder,
                status = status,
                type = type,
                transactionType = transactionType
            )

            val data = response.body()?.data!!
            return LoadResult.Page(
                data = data.transactionsList,
                nextKey = if (position >= data.lastPage) null else position + 1,
                prevKey = if (position == 1) null else position - 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}