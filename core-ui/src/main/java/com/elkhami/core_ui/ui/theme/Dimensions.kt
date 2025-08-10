package com.elkhami.core_ui.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppDimens(
    val none: Dp = 0.dp,
    val one: Dp = 1.dp,
    val xSmallPadding: Dp = 4.dp,
    val smallPadding: Dp = 8.dp,
    val mediumPadding: Dp = 16.dp,
    val cornerRadius: Dp = 12.dp,
    val cardElevation: Dp = 4.dp,
    val cardSpacing: Dp = 12.dp,
    val spacing: Dp = 10.dp,
    val tabHeight: Dp = 40.dp,
    val topBarHeight: Dp = 56.dp,
    val topBarPadding: Dp = 12.dp,
    val tabCornerRadius: Dp = 20.dp,
    val loadingStrokeWidth: Dp = 4.dp,
    val productImageSize: Dp = 350.dp,
    val buttonHeight: Dp = 52.dp,
    val starSize: Dp = 20.dp
)

val DefaultAppDimens = AppDimens()

val LocalAppDimens = staticCompositionLocalOf { DefaultAppDimens }
