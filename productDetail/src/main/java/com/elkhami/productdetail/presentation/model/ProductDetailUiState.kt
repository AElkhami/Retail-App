package com.elkhami.productdetail.presentation.model

import com.elkhami.core_ui.state.ScreenState

data class ProductDetailUiState(
    val screenState: ScreenState<Content>
){
    data class Content(
        val productHeader: ProductHeaderUiModel,
        val productBuy: ProductBuyUiModel
    )
}
