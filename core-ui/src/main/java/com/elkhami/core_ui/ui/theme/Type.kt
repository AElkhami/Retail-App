package com.elkhami.core_ui.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.staticCompositionLocalOf

data class AppTypography(
    val price: TextStyle,
    val brand: TextStyle,
    val title: TextStyle,
    val ratingCount: TextStyle,
    val badge: TextStyle,
    val delivery: TextStyle,
    val seller: TextStyle,
    val cta: TextStyle
)

val DefaultAppTypography = AppTypography(
    price = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Black),
    brand = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium),
    title = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, lineHeight = 32.sp),
    ratingCount = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal),
    badge = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.SemiBold),
    delivery = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium),
    seller = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
    cta = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
)

val LocalAppTypography = staticCompositionLocalOf { DefaultAppTypography }