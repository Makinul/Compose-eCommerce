package com.makinul.bs23104.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Review(
    val rating: Int,
    val comment: String,
    val date: String,
    val reviewerName: String,
    @SerializedName("reviewerEmail")
    val reviewerEmail: String
) : Serializable