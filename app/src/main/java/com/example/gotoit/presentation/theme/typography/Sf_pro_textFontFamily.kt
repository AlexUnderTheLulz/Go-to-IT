package com.example.gotoit.presentation.theme.typography

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.gotoit.R

private val semibold = Font(R.font.sf_pro_text_semibold, FontWeight.SemiBold)
private val bold = Font(R.font.sf_pro_text_bold, FontWeight.Bold)
private val medium = Font(R.font.sf_pro_text_medium, FontWeight.Medium)


val SFfontFamily = FontFamily(bold, medium, semibold)