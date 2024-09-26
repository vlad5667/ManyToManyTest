package com.example.manytomanytest.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalAppTypography provides appTypography,
        LocalAppColors provides appColors,
        content = content
    )
}

object AppTheme {

    val typography: AppTypography
        @Composable
        get() = LocalAppTypography.current

    val colors: AppColors
        @Composable
        get() = LocalAppColors.current
}

// Colors

private val LocalAppColors = staticCompositionLocalOf { appColors }

private val appColors = AppColors(
    pink = Color(0xFFE36198)
)

// Fonts

val LocalAppTypography = staticCompositionLocalOf { appTypography }

private val appTypography = AppTypography(
    bodyL = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    )
)