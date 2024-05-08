package com.example.templateapp.ui.screen.dashboard

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.templateapp.R
import com.example.templateapp.common.ToolbarType
import com.example.templateapp.databinding.FragmentDashboardBinding
import com.example.templateapp.preferences.SharedPrefManager
import com.example.templateapp.ui.base.BaseFragment
import com.example.templateapp.ui.screen.dashboard.adapter.DashboardPagingAdapter
import com.example.templateapp.ui.screen.dashboard.viewmodel.DashboardViewModel
import com.example.templateapp.utils.hide
import com.example.templateapp.utils.show
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    private val binding: FragmentDashboardBinding by viewBinding()
    private val viewModel: DashboardViewModel by viewModels()

    private lateinit var adapter: DashboardPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar(
            ToolbarType.Title(
                getString(R.string.title_hello_agent_name, SharedPrefManager.firstName),
                enableBackButton = false,
            )
        )

        setupViews()
        setupCollectors()
        handleError()
    }

    override fun setupViews() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner

            adapter = DashboardPagingAdapter {
                Toast.makeText(requireContext(), "${it.ticketNo} selected", Toast.LENGTH_LONG).show()
            }

            rvDashboard.adapter = adapter

            adapter.addLoadStateListener { loadState ->
                if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) {
                    viewModel.startLoading()
                } else {
                    viewModel.stopLoading()
                }
                updateCount()
            }
        }
    }

    override fun setupCollectors() {
        this.apply {
            getTransactions()
        }
    }

    override fun handleError() { }

    private fun updateCount() {
        viewModel.startLoading()
        runBlocking {
            delay(1000)
        }
        binding.resultCountTv.text = getString(R.string.showing_count_result, adapter.itemCount)
        updateNoTransactionsAvailableScreen()
        viewModel.stopLoading()
    }

    private fun updateNoTransactionsAvailableScreen() {
        if (adapter.itemCount > 0) {
            binding.rvDashboard.show()
            binding.noItemAvailable.hide()
        } else {
            binding.rvDashboard.hide()
            binding.noItemAvailable.show()
        }
    }

    private fun getTransactions() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getTransaction().collect{
                    adapter.submitData(viewLifecycleOwner.lifecycle, it)
                }
            }
        }
    }
}