package com.example.templateapp.api

enum class Environment(
    val authUrl: String,
    val transactionUrl: String
) {
    DEV(
        authUrl = "https://41fcuecg1h.execute-api.ca-central-1.amazonaws.com/",
        transactionUrl = "https://b2yy7yyk5d.execute-api.ca-central-1.amazonaws.com/",
    ), QA(
        authUrl = "https://b8o8pyfv7d.execute-api.ca-central-1.amazonaws.com/",
        transactionUrl = "https://tay31vk1zc.execute-api.ca-central-1.amazonaws.com/",
    );
    companion object {
        fun fromBuildFlavor(flavor: String): Environment {
            val env = enumValues<Environment>().find {
                flavor.equals(it.name, ignoreCase = true)
            }
            if (env != null) {
                return env
            }
            throw UnsupportedOperationException("Invalid flavor type $flavor")
        }
    }
}