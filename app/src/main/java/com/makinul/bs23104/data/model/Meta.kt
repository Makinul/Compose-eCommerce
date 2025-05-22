package com.makinul.bs23104.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Meta(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    val barcode: String,
    @SerializedName("qrCode")
    val qrCode: String
) : Serializable
