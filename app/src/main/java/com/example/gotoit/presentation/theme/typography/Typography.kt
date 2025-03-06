package com.example.gotoit.presentation.theme.typography

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

@Immutable
data class Typography(
    val bold15: TextStyle = BoldTokens.Bold15,
    val bold24: TextStyle = BoldTokens.Bold24,

    val semiBold13: TextStyle = SemiBoldTokens.SemiBold13,
)

internal val LocalTypography = staticCompositionLocalOf { Typography() }