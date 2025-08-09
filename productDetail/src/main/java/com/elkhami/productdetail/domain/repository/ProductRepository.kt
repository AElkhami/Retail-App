package com.elkhami.productdetail.domain.repository

import com.elkhami.core.util.Result
import com.elkhami.productdetail.domain.model.Product

interface ProductRepository {
    suspend fun getProductDetails(): Result<Product>
}