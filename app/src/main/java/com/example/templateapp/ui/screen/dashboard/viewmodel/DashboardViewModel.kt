package com.example.templateapp.ui.screen.dashboard.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.templateapp.dto.response.dashboard.Transaction
import com.example.templateapp.ui.base.BaseViewModel
import com.example.templateapp.ui.screen.dashboard.repository.DashboardRepository
import com.example.templateapp.utils.ErrorUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository,
    errorUtils: ErrorUtils
) : BaseViewModel(errorUtils) {

    fun getTransaction(): Flow<PagingData<Transaction>> {
        return repository.getTransaction(
            search = null,
            sortBy = null,
            sortOrder = null,
            status = null,
            type = null,
            transactionType = null
        ).cachedIn(viewModelScope)
    }
}