package com.example.gotoit.theme.typography

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val DefaultTextStyle = TextStyle.Default.copy(
    fontFamily = SFfontFamily,
)

object BoldTokens{
    val Bold15 = DefaultTextStyle.copy(
        fontSize = 15.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.W700,
    )

    val Bold24 = DefaultTextStyle.copy(
        fontSize = 24.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeight.W700,
    )
}

object SemiBoldTokens{
    val SemiBold13 = DefaultTextStyle.copy(
        fontSize = 13.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.W600,
    )
}

