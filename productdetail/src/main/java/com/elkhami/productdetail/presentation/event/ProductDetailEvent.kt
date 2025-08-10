package com.elkhami.productdetail.presentation.event

sealed interface ProductDetailEvent {
    data object BackClicked : ProductDetailEvent
    data object ShareClicked : ProductDetailEvent
    data object FavouriteClicked : ProductDetailEvent
    data object AddToCartClicked : ProductDetailEvent
    data object BrandClicked : ProductDetailEvent
    data object RetryClicked : ProductDetailEvent
}