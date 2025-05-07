package com.makinul.bs23104.data.service

import com.makinul.bs23104.data.ProductResponse
import com.makinul.bs23104.utils.AppConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    suspend fun fetchProducts(
        @Query("limit") limit: Int = AppConstants.productFetchInitialLimit,
        @Query("skip") skip: Int = 0,
        @Query("sortBy") sortBy: String = "price,title",
        @Query("order") order: String = "asc"
    ): Response<ProductResponse>
}