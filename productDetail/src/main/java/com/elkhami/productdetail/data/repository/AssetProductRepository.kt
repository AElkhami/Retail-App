package com.elkhami.productdetail.data.repository

import com.elkhami.core.util.Result
import com.elkhami.productdetail.data.datasource.ProductDataSource
import com.elkhami.productdetail.domain.model.Product
import com.elkhami.productdetail.domain.repository.ProductRepository

class AssetProductRepository(
    private val dataSource: ProductDataSource
) : ProductRepository {
    override suspend fun getProductDetails(): Result<Product> {
        return dataSource.getProductDetails()
    }
}