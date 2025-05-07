package com.makinul.bs23104.data.model

import android.os.Message
import com.google.gson.annotations.SerializedName

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    @SerializedName("discountPercentage")
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val tags: List<String>,
    val brand: String,
    val sku: String,
    val weight: Int,
    val dimensions: Dimensions,
    val warrantyInformation: String,
    val shippingInformation: String,
    val availabilityStatus: String,
    val reviews: List<Review>,
    val returnPolicy: String,
    val minimumOrderQuantity: Int,
    val meta: Meta,
    val images: List<String>,
    val thumbnail: String
) {
    var key = -1
    var message = ""
    var state: Int = -1 // -1 = loading, 1 = success, 0 = failed

    // Secondary constructor that takes only the most common fields.
    constructor(
        key: Int,
        message: String,
        state: Int
    ) : this(
        id = -1,
        title = "",
        description = "", // Default values for the other parameters
        category = "",
        price = 0.0,
        discountPercentage = 0.0,
        rating = 0.0,
        stock = 0,
        tags = emptyList(),
        brand = "",
        sku = "",
        weight = 0,
        dimensions = Dimensions(0.0, 0.0, 0.0),
        warrantyInformation = "",
        shippingInformation = "",
        availabilityStatus = "",
        reviews = emptyList(),
        returnPolicy = "",
        minimumOrderQuantity = 0,
        meta = Meta("", "", "", ""),
        images = emptyList(),
        thumbnail = ""
    )
}
