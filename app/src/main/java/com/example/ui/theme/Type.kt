package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.R

val DevanagariFontFamily = FontFamily(
  Font(R.font.noto_serif_devanagari, FontWeight.Normal),
  Font(R.font.noto_serif_devanagari, FontWeight.SemiBold),
  Font(R.font.noto_serif_devanagari, FontWeight.Bold),
)

val DisplayFontFamily = FontFamily(
  Font(R.font.rozha_one, FontWeight.Normal),
)

val EnglishNumericFontFamily = FontFamily(
  Font(R.font.poppins, FontWeight.Normal),
  Font(R.font.poppins, FontWeight.SemiBold),
  Font(R.font.poppins, FontWeight.Bold),
  Font(R.font.poppins, FontWeight.ExtraBold),
)

val GsCalendarTypography = Typography(
  displayLarge = TextStyle(
    fontFamily = DisplayFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 36.sp,
    lineHeight = 42.sp,
  ),
  headlineMedium = TextStyle(
    fontFamily = DevanagariFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp,
    lineHeight = 32.sp,
  ),
  titleLarge = TextStyle(
    fontFamily = DevanagariFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp,
    lineHeight = 28.sp,
  ),
  titleMedium = TextStyle(
    fontFamily = DevanagariFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp,
    lineHeight = 22.sp,
  ),
  bodyLarge = TextStyle(
    fontFamily = DevanagariFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 15.sp,
    lineHeight = 22.sp,
  ),
  bodyMedium = TextStyle(
    fontFamily = DevanagariFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 13.sp,
    lineHeight = 19.sp,
  ),
  labelMedium = TextStyle(
    fontFamily = DevanagariFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 12.sp,
    lineHeight = 16.sp,
  ),
  labelSmall = TextStyle(
    fontFamily = DevanagariFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 10.sp,
    lineHeight = 14.sp,
  ),
)
