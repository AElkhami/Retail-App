package com.elkhami.core_ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.elkhami.core_ui.ui.theme.LocalAppDimens

@Composable
fun ProductTopBar(
    isFavourite: Boolean,
    tint: Color = Color(0xFF1E40FF),
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onFavouriteClick: () -> Unit,
) {
    val dimens = LocalAppDimens.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(dimens.topBarHeight)
            .padding(horizontal = dimens.topBarPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = "Back",
                tint = tint
            )
        }

        Spacer(Modifier.weight(1f))

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onShareClick) {
                Icon(Icons.Outlined.Share, "Share", tint = tint)
            }
            IconButton(onClick = onFavouriteClick) {
                Icon(
                    imageVector = if (isFavourite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = tint
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable()
fun ProductTopBarPreview() {
    ProductTopBar(isFavourite = false, onBackClick = {}, onShareClick = {}, onFavouriteClick = {})

}

@Preview(showBackground = true)
@Composable()
fun ProductTopBarPreviewFavouriteSelected() {
    ProductTopBar(isFavourite = true, onBackClick = {}, onShareClick = {}, onFavouriteClick = {})
}