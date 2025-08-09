package com.elkhami.productdetail.presentation

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.elkhami.core_ui.components.OnlineImage
import com.elkhami.core_ui.components.ProductTopBar
import com.elkhami.core_ui.components.RatingStars
import com.elkhami.core_ui.components.StockBadge
import com.elkhami.core_ui.ui.theme.AppTheme
import com.elkhami.core_ui.ui.theme.LocalAppColors
import com.elkhami.core_ui.ui.theme.LocalAppDimens
import com.elkhami.core_ui.ui.theme.LocalAppTypography
import com.elkhami.productdetail.R
import com.elkhami.productdetail.presentation.model.ProductBuyUiModel
import com.elkhami.productdetail.presentation.model.ProductDetailUiState
import com.elkhami.productdetail.presentation.model.ProductHeaderUiModel

@Composable
fun ProductDetailScreen(
    modifier: Modifier = Modifier,
    uiState: ProductDetailUiState
) {
    ProductDetailScreenContent(
        modifier = modifier,
        productHeader = uiState.productHeader,
        productBuy = uiState.productBuy
    )
}

@Composable
fun ProductDetailScreenContent(
    modifier: Modifier = Modifier,
    productHeader: ProductHeaderUiModel,
    productBuy: ProductBuyUiModel
) {
    Column(
        modifier = modifier.fillMaxSize()
        ) {
        ProductHeaderSection(
            model = productHeader
        )
        ProductBuySection(
            model = productBuy
        )
    }
}


@Composable
fun ProductHeaderSection(
    modifier: Modifier = Modifier,
    model: ProductHeaderUiModel
) {
    Column(modifier.fillMaxWidth()) {
        ProductTopBar(
            isFavourite = model.isFavourite,
            onBackClick = { model.onBackClick },
            onShareClick = { model.onShareClick },
            onFavouriteClick = { model.onFavouriteClick }
        )
        OnlineImage(
            modifier = Modifier.fillMaxWidth(),
            imageUrl = model.productImage
        )
    }
}

@Composable
fun ProductBuySection(
    model: ProductBuyUiModel
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
            text = model.priceText,
            color = colors.danger,
            style = type.price
        )
        Text(
            text = stringResource(
                R.string.brand_with_chevron,
                model.brand
            ),
            color = textMuted,
            style = type.brand
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
            if (model.inStock) {
                StockBadge(
                    text = stringResource(R.string.in_stock),
                    border = colors.success,
                    textColor = colors.success
                )
            }
            Text(
                text = stringResource(R.string.select_label),
                color = colors.success,
                style = type.badge
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.delivery_line),
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
        Text(
            text = stringResource(
                R.string.sold_by,
                model.sellerName
            ),
            color = colors.textPrimary,
            style = type.seller
        )
        Button(
            onClick = model.onAddToCart,
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
            productHeader = ProductHeaderUiModel(
                productImage = "",
                isFavourite = false,
                onBackClick = {},
                onShareClick = {},
                onFavouriteClick = {}
            ),
            productBuy = ProductBuyUiModel(
                priceText = "39,",
                brand = "Google",
                title = "Google Chromecast 3 - Media Streamer",
                rating = 4.1,
                reviewsCount = 3686,
                inStock = true,
                sellerName = "bol.com",
                onAddToCart = {}
            )
        )
    }
}