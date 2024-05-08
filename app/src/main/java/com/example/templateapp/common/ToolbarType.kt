package com.example.templateapp.common

import com.example.templateapp.R

sealed class ToolbarType {
    data object Default : ToolbarType()
    data class Title(
        val title: String,
        val enableBackButton: Boolean = true,
        val backButtonIcon: Int = R.drawable.ic_back_arrow_black,
    ) : ToolbarType()
}
