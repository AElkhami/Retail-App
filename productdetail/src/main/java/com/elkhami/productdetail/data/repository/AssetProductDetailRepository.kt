package com.elkhami.productdetail.data.repository

import com.elkhami.core.util.Result
import com.elkhami.productdetail.data.datasource.ProductDataSource
import com.elkhami.productdetail.domain.model.Product
import com.elkhami.productdetail.domain.repository.ProductDetailRepository

class AssetProductDetailRepository(
    private val dataSource: ProductDataSource
) : ProductDetailRepository {
    override suspend fun getProductDetails(): Result<Product> {
        return dataSource.getProductDetails()
    }
}