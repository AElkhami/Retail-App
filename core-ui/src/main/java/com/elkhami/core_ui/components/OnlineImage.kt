package com.elkhami.core_ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.imageLoader
import com.elkhami.core_ui.R
import com.elkhami.core_ui.ui.theme.AppTheme
import com.elkhami.core_ui.ui.theme.LocalAppDimens

@Composable
fun OnlineImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader = LocalContext.current.imageLoader
) {
    val dimens = LocalAppDimens.current

    AsyncImage(
        modifier = modifier
            .size(dimens.productImageSize),
        model = imageUrl,
        imageLoader = imageLoader,
        contentDescription = stringResource(R.string.product_image),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.ic_image_placeholder),
        error = painterResource(R.drawable.ic_error)
    )
}

@Preview(showBackground = true)
@Composable
fun OnlineImagePreview(){
    AppTheme {
        OnlineImage(imageUrl = "https://media.s-bol.com/VPRPDq8Ry6E1/NMBxmz/550x228.jpg")
    }
}