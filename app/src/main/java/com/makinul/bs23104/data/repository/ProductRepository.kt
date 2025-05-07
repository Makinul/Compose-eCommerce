package com.makinul.bs23104.data.repository

import com.makinul.bs23104.data.Data
import com.makinul.bs23104.data.ProductResponse

interface ProductRepository {
    suspend fun fetchProducts(): Data<ProductResponse>
}