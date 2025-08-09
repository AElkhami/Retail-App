package com.elkhami.core_ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.elkhami.core_ui.ui.theme.LocalAppColors
import com.elkhami.core_ui.ui.theme.LocalAppDimens
import kotlin.math.floor

@Composable
fun RatingStars(
    rating: Double,
    max: Int = 5
) {
    val colors = LocalAppColors.current
    val dimens = LocalAppDimens.current

    val clampedRating = rating.coerceIn(0.0, max.toDouble())
    val fullStars = floor(clampedRating).toInt()
    val hasHalfStar = (clampedRating - fullStars) >= 0.25 && (clampedRating - fullStars) < 0.75
    val emptyStars = max - fullStars - if (hasHalfStar) 1 else 0

    Row {
        repeat(fullStars) {
            Icon(
                Icons.Filled.Star,
                contentDescription = null,
                tint = colors.warning,
                modifier = Modifier.size(dimens.starSize)
            )
        }
        if (hasHalfStar) {
            Icon(
                Icons.AutoMirrored.Filled.StarHalf,
                null,
                Modifier.size(dimens.starSize),
                tint = colors.warning
            )
        }
        repeat(emptyStars) {
            Icon(
                Icons.Outlined.StarOutline,
                null,
                Modifier.size(dimens.starSize),
                tint = colors.warning
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "Full Five stars"
)
@Composable
private fun RatingStarsPreviewFullStars() {
    RatingStars(rating = 5.0)
}

@Preview(
    showBackground = true,
    name = "Three and Half stars"
)
@Composable
private fun RatingStarsPreviewHalfStars() {
    RatingStars(rating = 3.5)
}

@Preview(
    showBackground = true,
    name = "Three stars"
)
@Composable
private fun RatingStarsPreviewThreeStars() {
    RatingStars(rating = 3.0)
}