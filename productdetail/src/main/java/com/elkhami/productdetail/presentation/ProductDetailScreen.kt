package com.elkhami.productdetail.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.elkhami.core_ui.components.ErrorContent
import com.elkhami.core_ui.components.ImageCarouselWithIndicator
import com.elkhami.core_ui.components.LoadingIndicator
import com.elkhami.core_ui.components.ProductTopBar
import com.elkhami.core_ui.components.RatingStars
import com.elkhami.core_ui.components.StockBadge
import com.elkhami.core_ui.state.ScreenState
import com.elkhami.core_ui.ui.theme.AppTheme
import com.elkhami.core_ui.ui.theme.LocalAppColors
import com.elkhami.core_ui.ui.theme.LocalAppDimens
import com.elkhami.core_ui.ui.theme.LocalAppTypography
import com.elkhami.productdetail.R
import com.elkhami.productdetail.presentation.effect.ProductDetailEffect
import com.elkhami.productdetail.presentation.event.ProductDetailEvent
import com.elkhami.productdetail.presentation.model.ProductBuyUiModel
import com.elkhami.productdetail.presentation.model.ProductDetailScreenAction
import com.elkhami.productdetail.presentation.model.ProductDetailUiState
import com.elkhami.productdetail.presentation.model.ProductHeaderUiModel

@Composable
fun ProductDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) { viewModel.getProductDetails() }

    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    LaunchedEffect(viewModel.effect) {

        viewModel.effect
            .flowWithLifecycle(
                lifecycleOwner.lifecycle,
                Lifecycle.State.STARTED
            )
            .collect { effect ->
                when (effect) {
                    is ProductDetailEffect.NavigateBack -> { /* TODO navController.popBackStack() */
                    }

                    is ProductDetailEffect.ShareProduct -> { /* TODO shareProduct */
                    }

                    is ProductDetailEffect.NavigateToBrand -> { /* TODO navigateToBrandScreen */
                    }
                }
            }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProductDetailScreenContent(
        modifier = modifier,
        screenState = uiState.screenState,
        action = ProductDetailScreenAction(
            onBackClick = { viewModel.onEvent(ProductDetailEvent.BackClicked) },
            onShareClick = { viewModel.onEvent(ProductDetailEvent.ShareClicked) },
            onFavouriteClick = { viewModel.onEvent(ProductDetailEvent.FavouriteClicked) },
            onAddToCart = { viewModel.onEvent(ProductDetailEvent.AddToCartClicked) },
            onBrandClick = { viewModel.onEvent(ProductDetailEvent.BrandClicked) },
            onRetryClick = { viewModel.onEvent(ProductDetailEvent.RetryClicked) }
        )
    )
}

@Composable
fun ProductDetailScreenContent(
    modifier: Modifier = Modifier,
    screenState: ScreenState<ProductDetailUiState.Content>,
    action: ProductDetailScreenAction
) {
    val colors = LocalAppColors.current

    Column(
        modifier = modifier.fillMaxSize().background(color = colors.surface)
    ) {
        when (val state = screenState) {
            is ScreenState.Content -> {
                ProductHeaderSection(
                    model = state.data.productHeader,
                    onBackClick = action.onBackClick,
                    onShareClick = action.onShareClick,
                    onFavouriteClick = action.onFavouriteClick
                )
                ProductBuySection(
                    model = state.data.productBuy,
                    onAddToCart = action.onAddToCart,
                    onBrandClick = action.onBrandClick
                )
            }

            is ScreenState.Error -> {
                ErrorContent(
                    message = screenState.message.asString(),
                    onRetry = action.onRetryClick
                )
            }

            ScreenState.Loading -> {
                LoadingIndicator()
            }
        }
    }
}


@Composable
fun ProductHeaderSection(
    modifier: Modifier = Modifier,
    model: ProductHeaderUiModel,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onFavouriteClick: () -> Unit
) {
    Column(modifier.fillMaxWidth()) {
        ProductTopBar(
            isFavourite = model.isFavourite,
            onBackClick = onBackClick,
            onShareClick = onShareClick,
            onFavouriteClick = onFavouriteClick
        )
        ImageCarouselWithIndicator(
            modifier = Modifier.fillMaxWidth(),
            imageUrls = model.productImage
        )
    }
}

@Composable
fun ProductBuySection(
    model: ProductBuyUiModel,
    onAddToCart: () -> Unit,
    onBrandClick: () -> Unit
) {
    val colors = LocalAppColors.current
    val dimens = LocalAppDimens.current
    val type = LocalAppTypography.current

    val textMuted = colors.textSecondary

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimens.mediumPadding),
        verticalArrangement = Arrangement.spacedBy(dimens.spacing)
    ) {
        Text(
            text = stringResource(R.string.price_with_comma, model.priceText),
            color = colors.danger,
            style = type.price
        )
        Text(
            text = stringResource(
                R.string.brand_with_chevron,
                model.brand
            ),
            color = textMuted,
            style = type.brand,
            modifier = Modifier
                .clickable(onClick = onBrandClick)
        )
        Text(
            text = model.title,
            color = colors.textPrimary,
            style = type.title
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            RatingStars(rating = model.rating)
            Spacer(Modifier.width(dimens.smallPadding))
            Text(
                text = stringResource(
                    R.string.reviews_count,
                    model.reviewsCount
                ),
                color = textMuted,
                style = type.ratingCount
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                dimens.smallPadding
            )
        ) {
            StockBadge(
                inStock = model.inStock
            )
            if (model.isSelect) {
                Text(
                    text = stringResource(R.string.select_label),
                    color = colors.success,
                    style = type.badge
                )
            }
        }
        if (model.deliveryTime.isNotBlank()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = model.deliveryTime,
                    color = colors.success,
                    style = type.delivery
                )
                Spacer(Modifier.width(dimens.xSmallPadding))
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    tint = colors.success,
                    modifier = Modifier.size(dimens.mediumPadding)
                )
            }
        }
        Text(
            text = stringResource(
                R.string.sold_by,
                model.sellerName
            ),
            color = colors.textPrimary,
            style = type.seller
        )
        Button(
            onClick = onAddToCart,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimens.buttonHeight),
            shape = RoundedCornerShape(dimens.cornerRadius),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.warning,
                contentColor = Color.Black
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = dimens.none
            )
        ) {
            Text(
                text = stringResource(R.string.add_to_cart),
                style = type.cta
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    AppTheme {
        ProductDetailScreenContent(
            screenState = ScreenState.Content(
                ProductDetailUiState.Content(
                    productHeader = ProductHeaderUiModel(
                        productImage = emptyList(),
                        isFavourite = false
                    ),
                    productBuy = ProductBuyUiModel(
                        priceText = "39",
                        brand = "Google",
                        title = "Google Chromecast 3 - Media Streamer",
                        rating = 3.5,
                        reviewsCount = 3686,
                        inStock = true,
                        sellerName = "bol.com",
                        isSelect = true,
                        deliveryTime = "Voor 23:59 besteld, morgen in huis"
                    )
                )
            ),
            action = ProductDetailScreenAction(
                onBackClick = { },
                onShareClick = { },
                onFavouriteClick = { },
                onAddToCart = { },
                onBrandClick = { },
                onRetryClick = { }
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductDetailScreenPreviewOtherOptions() {
    AppTheme {
        ProductDetailScreenContent(
            screenState = ScreenState.Content(
                ProductDetailUiState.Content(
                    productHeader = ProductHeaderUiModel(
                        productImage = emptyList(),
                        isFavourite = true
                    ),
                    productBuy = ProductBuyUiModel(
                        priceText = "39",
                        brand = "Google",
                        title = "Google Chromecast 3 - Media Streamer",
                        rating = 5.0,
                        reviewsCount = 3686,
                        inStock = false,
                        sellerName = "bol.com",
                        isSelect = false,
                        deliveryTime = ""
                    )
                )
            ),
            action = ProductDetailScreenAction(
                onBackClick = { },
                onShareClick = { },
                onFavouriteClick = { },
                onAddToCart = { },
                onBrandClick = { },
                onRetryClick = { }
            )
        )
    }
}