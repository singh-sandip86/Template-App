package com.example.templateapp.ui.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.templateapp.common.ToolbarType
import com.example.templateapp.ui.screen.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    abstract fun setupViews()

    abstract fun setupCollectors()

    /*
    * Handle generic errors thrown from view-model while making network calls
    * */
    abstract fun handleError()

    fun setToolbar(toolbarType: ToolbarType) {
        if (requireActivity() is HomeActivity) {
            (requireActivity() as HomeActivity).setToolBar(toolbarType)
        }
    }
}