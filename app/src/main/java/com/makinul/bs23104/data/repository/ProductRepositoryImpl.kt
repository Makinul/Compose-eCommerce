package com.makinul.bs23104.data.repository

import com.makinul.bs23104.data.Data
import com.makinul.bs23104.data.ProductResponse
import com.makinul.bs23104.data.model.Product
import com.makinul.bs23104.data.service.ApiService
import com.makinul.bs23104.utils.AppConstants
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    val api: ApiService
) : ProductRepository {

    override suspend fun fetchProducts(): Data<ProductResponse> {
        AppConstants.showLog("fetchProducts")
        return try {
            val response = api.fetchProducts()
            val isSuccessful = response.isSuccessful
            if (isSuccessful) {
                response.body()?.let { dashboardData ->
                    return Data.Success(dashboardData)
                } ?: run {
                    Data.Error(response.message() ?: "Unknown error")
                }
            } else {
                Data.Error("Error")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Data.Error(e.message ?: "Request failed")
        }
    }
}