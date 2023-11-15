package com.example.dictionary_app.feature_dictionary.data.remote.dto


import com.google.gson.annotations.SerializedName

data class LicenseDTO(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)