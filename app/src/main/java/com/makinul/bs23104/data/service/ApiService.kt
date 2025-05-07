package com.makinul.bs23104.data.service

import com.makinul.bs23104.data.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("products?limit=10&skip=0&sortBy=price%2Ctitle&order=asc")
    suspend fun fetchProducts(): Response<ProductResponse>
}