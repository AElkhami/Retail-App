package com.elkhami.productdetail.presentation.model

data class ProductDetailScreenAction(
    val onBackClick: () -> Unit,
    val onShareClick: () -> Unit,
    val onFavouriteClick: () -> Unit,
    val onAddToCart: () -> Unit,
    val onBrandClick: () -> Unit,
    val onRetryClick: () -> Unit
)
