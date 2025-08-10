package com.elkhami.productdetail.domain.usecase

import com.elkhami.core.util.Result
import com.elkhami.productdetail.domain.model.Product
import com.elkhami.productdetail.domain.repository.ProductDetailRepository
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val repository: ProductDetailRepository
){
    suspend operator fun invoke(): Result<Product>{
        return repository.getProductDetails()
    }
}