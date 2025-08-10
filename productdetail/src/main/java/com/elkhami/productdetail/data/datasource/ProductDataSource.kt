package com.elkhami.productdetail.data.datasource

import com.elkhami.core.util.Result
import com.elkhami.productdetail.domain.model.Product

interface ProductDataSource {
    suspend fun getProductDetails(): Result<Product>
}