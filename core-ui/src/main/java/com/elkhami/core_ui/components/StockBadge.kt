package com.elkhami.core_ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.elkhami.core_ui.R
import com.elkhami.core_ui.ui.theme.AppTheme
import com.elkhami.core_ui.ui.theme.LocalAppColors
import com.elkhami.core_ui.ui.theme.LocalAppDimens

@Composable
fun StockBadge(
    inStock: Boolean
) {
    val dimens = LocalAppDimens.current
    val colors = LocalAppColors.current

    val text =
        if (inStock) stringResource(R.string.in_stock)
        else stringResource(R.string.out_of_stock)
    
    val badgeColor = if (inStock) colors.success else colors.danger

    Box(
        modifier = Modifier
            .border(
                dimens.one,
                badgeColor,
                RoundedCornerShape(dimens.smallPadding)
            )
            .padding(horizontal = dimens.spacing, vertical = dimens.xSmallPadding)
    ) {
        Text(
            text,
            color = badgeColor,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview(
    showBackground = true,
    name = "In Stock"
)
@Composable
private fun StockBadgePreviewInStock() {
    AppTheme {
        StockBadge(
            inStock = true
        )
    }
}

@Preview(
    showBackground = true,
    name = "Out of Stock"
)
@Composable
private fun StockBadgePreviewOutOfStock() {
    AppTheme {
        StockBadge(
            inStock = false
        )
    }
}