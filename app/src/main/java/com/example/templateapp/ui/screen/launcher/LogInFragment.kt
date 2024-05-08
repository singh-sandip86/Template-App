package com.example.templateapp.ui.screen.launcher

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.templateapp.R
import com.example.templateapp.databinding.FragmentLogInBinding
import com.example.templateapp.preferences.SharedPrefManager
import com.example.templateapp.ui.base.BaseFragment
import com.example.templateapp.ui.screen.home.HomeActivity
import com.example.templateapp.ui.screen.launcher.viewmodel.LoginViewModel
import com.example.templateapp.utils.show
import kotlinx.coroutines.launch

class LogInFragment : BaseFragment(R.layout.fragment_log_in) {

    private val binding: FragmentLogInBinding by viewBinding()
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        setupViews()
        setupCollectors()
        handleError()
    }

    override fun setupViews() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun setupCollectors() {
        this.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.isLoading.collect { isLoading ->
                        binding.progress.show(isLoading)
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.user.collect {
                        if (it.isSuccess) {
                            it.getOrNull().let { user ->
                                SharedPrefManager.isUserLoggedIn = true
                                SharedPrefManager.token = user?.accessToken.toString()
                                SharedPrefManager.refreshToken = user?.refreshToken.toString()
                                SharedPrefManager.firstName = user?.firstName.toString()
                                SharedPrefManager.lastName = user?.lastName.toString()
                                HomeActivity.startActivity(requireContext())
                            }
                        }
                    }
                }
            }
        }
    }

    override fun handleError() { }
}