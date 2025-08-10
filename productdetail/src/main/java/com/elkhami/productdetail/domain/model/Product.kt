package com.elkhami.productdetail.domain.model

import com.elkhami.productdetail.domain.model.value.ImageUrl

data class Product(
    val title: String,
    val brand: Brand,
    val rating: Rating,
    val priceText: String,
    val images: List<ImageUrl>,
    val delivery: DeliveryInfo,
    val seller: Seller,
    val isSelect: Boolean
)
