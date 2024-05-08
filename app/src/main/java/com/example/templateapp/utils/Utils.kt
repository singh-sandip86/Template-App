package com.example.templateapp.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

object Utils {

    // Date in api response should be in below format
    const val DATE_FORMAT_API = "yyyy-MM-dd"
    // Show date on UI in below format
    private const val DATE_FORMAT_UI = "MMM/dd/yyyy"

    private const val DATE_FORMAT_DD_MMM_YYYY = "dd MMM yyyy"
    const val DATE_FORMAT_YYYY_MM_DD_HH_mm_ss = "yyyy-MM-dd HH:mm:ss"

    // Convert API response date[yyyy-MM-dd] to UI date[MMM/dd/yyyy]
    fun String?.apiDateToUIDate(): String {
        if (this.isNullOrEmpty()) {
            return String.empty()
        }

        return try {
            SimpleDateFormat(DATE_FORMAT_API, Locale.ENGLISH).parse(this)?.let {
                return SimpleDateFormat(DATE_FORMAT_UI, Locale.ENGLISH).format(it)
            }
            this
        } catch (e: Exception) {
            getDDMMMYYYYFromDate(this)
        }
    }

    private fun getDDMMMYYYYFromDate(input: String): String {
        return try {
            SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_mm_ss, Locale.ENGLISH).parse(input)?.let {
                return SimpleDateFormat(DATE_FORMAT_DD_MMM_YYYY, Locale.ENGLISH).format(it)
            }
            input
        } catch (e: Exception) {
            input
        }
    }

    // Convert UI date[MMM/dd/yyyy] to API response date[yyyy-MM-dd]
    fun String.uiDateToAPIDate(): String? {
        return try {
            SimpleDateFormat(DATE_FORMAT_UI, Locale.ENGLISH).parse(this)?.let {
                return SimpleDateFormat(DATE_FORMAT_API, Locale.ENGLISH).format(it)
            }
            null
        } catch (e: Exception) {
            null
        }
    }

    fun formatPrice(originalPrice: String?): String {
        try {
            originalPrice?.let {
                if (originalPrice.isNotEmpty()) {
                    val formatter: DecimalFormat =
                        NumberFormat.getInstance(Locale.US) as DecimalFormat
                    formatter.applyPattern("#,###,###,###.####")
                    return "$${formatter.format(it.toDouble())}"
                } else {
                    return ""
                }
            }
            return ""
        } catch (e: java.lang.NumberFormatException) {
            return ""
        }
    }
}