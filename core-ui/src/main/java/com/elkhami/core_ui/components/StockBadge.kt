package com.elkhami.core_ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.elkhami.core_ui.ui.theme.AppTheme
import com.elkhami.core_ui.ui.theme.LocalAppColors
import com.elkhami.core_ui.ui.theme.LocalAppDimens

@Composable
fun StockBadge(
    text: String,
    border: Color,
    textColor: Color
) {
    val dimens = LocalAppDimens.current
    Box(
        modifier = Modifier
            .border(dimens.one, border, RoundedCornerShape(dimens.smallPadding))
            .padding(horizontal = dimens.spacing, vertical = dimens.xSmallPadding)
    ) {
        Text(text, color = textColor, style = MaterialTheme.typography.labelMedium)
    }
}

@Preview(
    showBackground = true,
    name = "In Stock"
    )
@Composable
private fun StockBadgePreviewInstock(){
    val colors = LocalAppColors.current
    AppTheme {
        StockBadge(
            text = "Op voorraad",
            border = colors.success,
            textColor = colors.success
        )
    }
}

@Preview(
    showBackground = true,
    name = "Out of Stock"
)
@Composable
private fun StockBadgePreviewOutOfStock(){
    val colors = LocalAppColors.current
    AppTheme {
        StockBadge(
            text = "Niet op voorraad",
            border = colors.danger,
            textColor = colors.danger
        )
    }
}