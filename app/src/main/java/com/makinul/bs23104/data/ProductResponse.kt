package com.makinul.bs23104.data

import com.google.gson.annotations.SerializedName
import com.makinul.bs23104.data.model.Product

data class ProductResponse(
    @SerializedName("products") val products: ArrayList<Product>,
    @SerializedName("total") val total: Int,
    @SerializedName("skip") val skip: Int,
    @SerializedName("limit") val limit: Int,
)
