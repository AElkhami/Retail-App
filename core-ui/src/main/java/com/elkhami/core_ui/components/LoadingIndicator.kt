package com.elkhami.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.elkhami.core_ui.ui.theme.AppTheme
import com.elkhami.core_ui.ui.theme.LocalAppColors
import com.elkhami.core_ui.ui.theme.LocalAppDimens


@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    val dimens = LocalAppDimens.current
    val colors = LocalAppColors.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
            .testTag("LoadingIndicator"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = colors.primary,
                strokeWidth = dimens.loadingStrokeWidth
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingIndicatorPreview() {
    AppTheme {
        LoadingIndicator()
    }
}
