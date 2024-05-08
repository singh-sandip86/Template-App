package com.example.templateapp.model.dashboard

data class UITransaction(
    val transactionId: String,
    val propertyType: String,
    val transactionStatus: String,
    val transactionStatusColor: Int,
    val transactionStatusBackgroundColor: Int,
    val propertyAddress: String,
    val personName: String,
    val propertyRate: String,
    val date: String,
    val createdDate: String,
    val lastUpdated: String,
    val commencementClosing: String,
    val expiry: String
)
