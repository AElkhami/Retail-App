package com.elkhami.productdetail.presentation.effect

sealed class ProductDetailEffect {
    object NavigateBack : ProductDetailEffect()
    object ShareProduct : ProductDetailEffect()
    object NavigateToBrand : ProductDetailEffect()
}