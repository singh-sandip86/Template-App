package com.example.templateapp.dto.response.dashboard

import com.example.templateapp.R
import com.example.templateapp.model.dashboard.UITransaction
import com.example.templateapp.utils.Utils
import com.example.templateapp.utils.Utils.apiDateToUIDate
import com.example.templateapp.utils.empty
import com.example.templateapp.utils.toDoubleOrZero
import com.google.gson.annotations.SerializedName
import kotlin.math.roundToInt

data class TransactionsResponseData(
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("data")
    val transactionsList: List<Transaction>,
    @SerializedName("first_page_url")
    val firstPageUrl: String,
    @SerializedName("from")
    val from: Int,
    @SerializedName("last_page")
    val lastPage: Int,
    @SerializedName("last_page_url")
    val lastPageUrl: String,
    @SerializedName("next_page_url")
    val nextPageUrl: String,
    @SerializedName("path")
    val path: String,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("prev_page_url")
    val prevPageUrl: Any?,
    @SerializedName("to")
    val to: Int,
    @SerializedName("total")
    val total: Int
)

data class Transaction(
    @SerializedName("agent")
    val agent: Agent,
    @SerializedName("agent_id")
    val agentId: Int,
    @SerializedName("apt_suit_unit_no")
    val aptSuitUnitNo: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("client_first_name")
    val clientFirstName: String?,
    @SerializedName("client_last_name")
    val clientLastName: String?,
    @SerializedName("commencement_date")
    val commencementDate: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("deal_processing_transaction_people_id")
    val dealProcessingTransactionPeopleId: Int?,
    @SerializedName("deal_transaction_type")
    val dealTransactionType: Int?,
    @SerializedName("expiry_date")
    val expiryDate: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_converted_to_deal")
    val isConvertedToDeal: Int,
    @SerializedName("is_listing_sale_or_lease")
    val isListingSaleOrLease: Int?,
    @SerializedName("is_referrer_listing")
    val isReferrerListing: Boolean,
    @SerializedName("listing_property_deal_processing_transaction_id")
    val listingPropertyDealProcessingTransactionId: Int?,
    @SerializedName("new_created_at")
    val newCreatedAt: String,
    @SerializedName("new_updated_at")
    val newUpdatedAt: String,
    @SerializedName("office_name")
    val officeName: String?,
    @SerializedName("people_deal_processing_transaction_id")
    val peopleDealProcessingTransactionId: Int?,
    @SerializedName("postal_code")
    val postalCode: String?,
    @SerializedName("project_name")
    val projectName: String?,
    @SerializedName("property_price")
    val propertyPrice: String?,
    @SerializedName("province")
    val province: String?,
    @SerializedName("status")
    val status: Int,
    @SerializedName("street_address")
    val streetAddress: String?,
    @SerializedName("ticket_no")
    val ticketNo: String,
    @SerializedName("transaction_status_detail")
    val transactionStatusDetail: TransactionStatusDetail,
    @SerializedName("transaction_type")
    val transactionType: Int,
    @SerializedName("transaction_type_detail")
    val transactionTypeDetail: TransactionTypeDetail,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("closing_date")
    val closingDate: String?
)

data class Agent(
    @SerializedName("id")
    val id: Int,
    @SerializedName("profile_detail")
    val profileDetail: ProfileDetail,
    @SerializedName("user_profile_detail_id")
    val userProfileDetailId: Int,
)

data class ProfileDetail(
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("profile_photo")
    val profilePhoto: String?,
    @SerializedName("driver_license_presign_url")
    val driverLicensePresignUrl: String?,
    @SerializedName("profile_photo_presign_url")
    val profilePhotoPresignUrl: String?,
)

data class TransactionStatusDetail(
    @SerializedName("status_id")
    val statusId: Int,
    @SerializedName("status_name")
    val statusName: String,
)

data class TransactionTypeDetail(
    @SerializedName("status_id")
    val statusId: Int,
    @SerializedName("status_name")
    val statusName: String,
)

fun Transaction.toTransaction(): UITransaction {
    val suiteUnitNo =
        if (aptSuitUnitNo.isNullOrEmpty()) String.empty() else "#$aptSuitUnitNo - "
    val streetAdd =
        if (streetAddress.isNullOrEmpty()) String.empty() else streetAddress
    val ct = if (city.isNullOrEmpty()) String.empty() else ", $city"
    val pv = if (province.isNullOrEmpty()) String.empty() else ", $province"
    val pCode = if (postalCode.isNullOrEmpty()) String.empty() else ", $postalCode"
    val cnt = if (country.isNullOrEmpty()) String.empty() else ", $country"
    val transactionName = "$suiteUnitNo$streetAdd$ct$pv$pCode$cnt".ifEmpty { "-" }

    val dealType = transactionTypeDetail.statusName

    val firstName = clientFirstName ?: agent.profileDetail.firstName ?: String.empty()
    val lastName = clientLastName ?: agent.profileDetail.lastName ?: String.empty()
    val personName = if (firstName.isEmpty()) "-" else "$firstName $lastName"
    val propertyPriceAmount =
        Utils.formatPrice(propertyPrice?.toDoubleOrZero()?.roundToInt().toString())
    val createdDate = newCreatedAt.apiDateToUIDate()
    val updatedDate = newUpdatedAt.apiDateToUIDate()
    val commencementClosingDate =
        commencementDate.apiDateToUIDate().ifEmpty { closingDate.apiDateToUIDate() }
    val expiryDate = expiryDate.apiDateToUIDate()

    return UITransaction(
        transactionId = ticketNo,
        propertyType = dealType,
        transactionStatus = transactionStatusDetail.statusName,
        transactionStatusColor = R.color.purple_6,
        transactionStatusBackgroundColor = R.color.purple_1,
        propertyAddress = transactionName,
        personName = personName,
        propertyRate = propertyPriceAmount.ifEmpty { "-" },
        date = createdAt.apiDateToUIDate(),
        createdDate = createdDate.ifEmpty { "-" },
        lastUpdated = updatedDate.ifEmpty { "-" },
        commencementClosing = commencementClosingDate.ifEmpty { "N/A" },
        expiry = expiryDate.ifEmpty { "N/A" },
    )
}