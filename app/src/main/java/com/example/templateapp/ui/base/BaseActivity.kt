package com.example.templateapp.ui.base

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity(@LayoutRes layoutId: Int) : AppCompatActivity(layoutId) {

    abstract fun setupViews()

    abstract fun setupCollectors()

    /*
    * Handle generic errors thrown from view-model while making network calls
    * */
    abstract fun handleError()
}