package com.elkhami.core_ui.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val primary: Color,
    val background: Color,
    val surface: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val divider: Color,
    val error: Color,
    val success: Color,
    val warning: Color,
    val danger: Color
)

private val LightError = Color(0xFFB3261E)
private val DarkError = Color(0xFFF2B8B5)
private val LightTextSecondary = Color(0xFF5A6268)

private val ProductYellow = Color(0xFFFFD814)
private val ProductRed = Color(0xFFE53935)
private val ProductGreen = Color(0xFF28A745)

val LightAppColors = AppColors(
    primary = Color(0xFF0005B1),
    background = Color.White,
    surface = Color(0xFFF7F7F7),
    textPrimary = Color(0xFF292D31),
    textSecondary = LightTextSecondary,
    divider = Color(0xFF000000).copy(alpha = 0.12f),
    error = LightError,
    success = ProductGreen,
    warning = ProductYellow,
    danger = ProductRed
)

val DarkAppColors = AppColors(
    primary = Color(0xFF0005B1),
    background = Color(0xFF1E1E1E),
    surface = Color(0xFF121212),
    textPrimary = Color.White,
    textSecondary = Color(0xFFB0BEC5),
    divider = Color.White.copy(alpha = 0.12f),
    error = DarkError,
    success = ProductGreen,
    warning = ProductYellow,
    danger = ProductRed
)

val LocalAppColors = staticCompositionLocalOf { LightAppColors }
