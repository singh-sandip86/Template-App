package com.example.templateapp.ui.screen.dashboard.viewmodel

import androidx.lifecycle.asLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import app.cash.turbine.test
import com.example.templateapp.api.ApiInterface
import com.example.templateapp.common.Constants
import com.example.templateapp.dto.response.dashboard.Transaction
import com.example.templateapp.dto.response.dashboard.TransactionsResponse
import com.example.templateapp.ui.screen.dashboard.adapter.DashboardPagingSource
import com.example.templateapp.ui.screen.dashboard.repository.DashboardRepository
import com.example.templateapp.ui.screen.dashboard.repository.DashboardRepositoryImpl
import com.example.templateapp.util.MainDispatcherRule
import com.example.templateapp.util.convertJsonToModel
import com.example.templateapp.util.loadJsonAsString
import com.example.templateapp.utils.ErrorUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class DashboardViewModelTest {

    companion object {
        private const val DASHBOARD_RESPONSE = "dashboard/transaction_listing_data.json"
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var repository: DashboardRepository

    @Mock
    private lateinit var errorUtils: ErrorUtils

    @Mock
    private lateinit var apiInterface: ApiInterface

    @Mock
    private lateinit var repositoryImpl: DashboardRepositoryImpl

    @Mock
    private lateinit var pagingSource: DashboardPagingSource

    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = DashboardViewModel(repository, errorUtils)
    }

    @Test
    fun `verify transaction listing success`() = runTest {
        val jsonString = loadJsonAsString(javaClass, DASHBOARD_RESPONSE)
        val model = convertJsonToModel(jsonString, TransactionsResponse::class.java)
        val response = Response.success(model)

        Mockito.`when`(apiInterface.getTransaction(page = 0)).thenReturn(response)

//        Mockito.`when`(repositoryImpl.getTransaction())

//        val paging = PagingSource.LoadResult.Page(
//            data = model.data!!.transactionsList,
//            prevKey = null,
//            nextKey = null
//        )

//        val pager: Flow<PagingData<Transaction>> = Pager(
//            config = PagingConfig(
//                pageSize = Constants.PAGE_SIZE,
//                prefetchDistance = Constants.PREFETCH_DISTANCE
//            ),
//            pagingSourceFactory = {
//                paging.data
//                }
//            ).flow.map {
//
//        }

//        Mockito.`when`(viewModel.getTransaction()).thenReturn(pager)

        val sut = viewModel.getTransaction()

//        viewModel.getTransaction().test {
//            val item = awaitItem()
//            assertEquals(item, sut.firstOrNull())
//        }
    }
}