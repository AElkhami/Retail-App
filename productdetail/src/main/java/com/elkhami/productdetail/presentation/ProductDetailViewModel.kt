package com.elkhami.productdetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elkhami.core.util.fold
import com.elkhami.core_ui.mapper.toUiText
import com.elkhami.core_ui.state.ScreenState
import com.elkhami.productdetail.domain.usecase.GetProductDetailUseCase
import com.elkhami.productdetail.presentation.effect.ProductDetailEffect
import com.elkhami.productdetail.presentation.event.ProductDetailEvent
import com.elkhami.productdetail.presentation.mapper.toUiContent
import com.elkhami.productdetail.presentation.model.ProductDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    val getProductDetailUseCase: GetProductDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProductDetailUiState(screenState = ScreenState.Loading)
    )
    val uiState = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<ProductDetailEffect>(extraBufferCapacity = 1)
    val effect = _effect.asSharedFlow()

    fun getProductDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(screenState = ScreenState.Loading) }

            getProductDetailUseCase().fold(
                onSuccess = { data ->
                    _uiState.update {
                        it.copy(
                            screenState = ScreenState.Content(
                                data.toUiContent()
                            )
                        )
                    }

                },
                onError = { error ->
                    _uiState.update {
                        it.copy(
                            screenState = ScreenState.Error(
                                message = error.toUiText()
                            )
                        )
                    }
                }
            )
        }
    }

    fun onEvent(event: ProductDetailEvent) {
        when (event) {
            ProductDetailEvent.BackClicked -> emitEffect(ProductDetailEffect.NavigateBack)
            ProductDetailEvent.ShareClicked -> emitEffect(ProductDetailEffect.ShareProduct)
            ProductDetailEvent.FavouriteClicked -> toggleFavourite()
            ProductDetailEvent.AddToCartClicked -> addToCart()
            ProductDetailEvent.RetryClicked -> retry()
            ProductDetailEvent.BrandClicked -> emitEffect(ProductDetailEffect.NavigateToBrand)
        }
    }

    private fun toggleFavourite() {
        //TODO: implement toggleFavourite function
    }

    private fun addToCart() {
        //TODO: implement addToCart function
    }

    private fun retry() {
        getProductDetails()
    }

    private fun emitEffect(effect: ProductDetailEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}