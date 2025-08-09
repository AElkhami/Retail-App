package com.elkhami.productdetail.presentation.model

data class ProductHeaderUiModel(
    val productImage: String,
    val isFavourite: Boolean,
    val onBackClick: () -> Unit,
    val onShareClick: () -> Unit,
    val onFavouriteClick: () -> Unit
)
