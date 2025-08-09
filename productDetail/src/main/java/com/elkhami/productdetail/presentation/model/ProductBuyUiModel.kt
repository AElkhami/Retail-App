package com.elkhami.productdetail.presentation.model

data class ProductBuyUiModel(
    val priceText: String,
    val brand: String,
    val title: String,
    val rating: Double,
    val reviewsCount: Int,
    val inStock: Boolean,
    val sellerName: String,
    val onAddToCart: () -> Unit
)
