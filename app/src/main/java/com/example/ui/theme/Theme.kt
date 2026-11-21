package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
  primary = CrimsonPrimary,
  onPrimary = Color.White,
  primaryContainer = AmberSoft,
  onPrimaryContainer = CrimsonDarkest,
  secondary = SaffronGold,
  onSecondary = Color.White,
  secondaryContainer = ParchmentDeep,
  onSecondaryContainer = InkDark,
  tertiary = EmeraldHijri,
  onTertiary = Color.White,
  tertiaryContainer = EmeraldBg,
  onTertiaryContainer = EmeraldHijri,
  background = ParchmentCream,
  onBackground = InkDark,
  surface = ParchmentCream,
  onSurface = InkDark,
  surfaceVariant = ParchmentWarm,
  onSurfaceVariant = InkMuted,
  outline = SaffronGold,
  outlineVariant = AmberBorder,
  error = CrimsonBright,
  onError = Color.White,
)

private val DarkColorScheme = darkColorScheme(
  primary = MarigoldBright,
  onPrimary = CrimsonDarkest,
  primaryContainer = CrimsonDeep,
  onPrimaryContainer = GoldLight,
  secondary = SaffronGold,
  onSecondary = Color.White,
  secondaryContainer = TempleDarkSurfaceVariant,
  onSecondaryContainer = TempleDarkText,
  tertiary = EmeraldLight,
  onTertiary = Color(0xFF003828),
  tertiaryContainer = Color(0xFF064E3B),
  onTertiaryContainer = EmeraldLight,
  background = TempleDarkBg,
  onBackground = TempleDarkText,
  surface = TempleDarkSurface,
  onSurface = TempleDarkText,
  surfaceVariant = TempleDarkSurfaceVariant,
  onSurfaceVariant = TempleDarkMuted,
  outline = SaffronGold,
  outlineVariant = Color(0xFF6B4423),
  error = Color(0xFFF87171),
  onError = CrimsonDarkest,
)

val GsCalendarShapes = Shapes(
  small = RoundedCornerShape(8.dp),
  medium = RoundedCornerShape(14.dp),
  large = RoundedCornerShape(20.dp),
)

@Composable
fun GsCalendarTheme(
  darkTheme: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = GsCalendarTypography,
    shapes = GsCalendarShapes,
    content = content,
  )
}
