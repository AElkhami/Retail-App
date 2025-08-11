package com.elkhami.productdetail.presentation.mapper

import com.elkhami.productdetail.domain.model.Product
import com.elkhami.productdetail.presentation.model.ProductBuyUiModel
import com.elkhami.productdetail.presentation.model.ProductDetailUiState
import com.elkhami.productdetail.presentation.model.ProductHeaderUiModel

fun Product.toUiContent(
): ProductDetailUiState.Content {
    val header = ProductHeaderUiModel(
        productImage = images.map { it.value },
        isFavourite = false
    )
    val buy = ProductBuyUiModel(
        priceText = priceText,
        brand = brand.name,
        title = title,
        rating = rating.value,
        reviewsCount = rating.count,
        inStock = delivery.inStock.scarcityLevel
            .equals("in_stock", ignoreCase = true),
        sellerName = seller.name,
        deliveryTime = delivery.description,
        isSelect = isSelect
    )
    return ProductDetailUiState.Content(
        productHeader = header,
        productBuy = buy
    )
}