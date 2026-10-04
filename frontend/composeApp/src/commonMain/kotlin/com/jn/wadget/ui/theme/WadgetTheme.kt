package com.jn.wadget.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object WadgetColors {
    val Background = Color(0xFF111518)
    val Surface = Color(0xFF1A2024)
    val Elevated = Color(0xFF222B30)
    val Border = Color(0xFF344047)
    val Accent = Color(0xFF80CFC1)
    val AccentContainer = Color(0xFF273E3A)
    val OnAccent = Color(0xFF102521)
    val Text = Color(0xFFE8EFEE)
    val Muted = Color(0xFFA0AFB5)
    val Danger = Color(0xFFE5AAA5)
    val DangerContainer = Color(0xFF422E2E)
}

object WadgetSpacing {
    val Small = 8.dp
    val Medium = 16.dp
    val Large = 24.dp
    val ExtraLarge = 32.dp
}

object WadgetShapes {
    val Card = RoundedCornerShape(18.dp)
    val Control = RoundedCornerShape(12.dp)
}

private val WadgetColorScheme = darkColorScheme(
    primary = WadgetColors.Accent,
    onPrimary = WadgetColors.OnAccent,
    primaryContainer = WadgetColors.AccentContainer,
    onPrimaryContainer = WadgetColors.Accent,
    secondary = WadgetColors.Accent,
    onSecondary = WadgetColors.OnAccent,
    secondaryContainer = WadgetColors.AccentContainer,
    onSecondaryContainer = WadgetColors.Accent,
    tertiary = WadgetColors.Accent,
    onTertiary = WadgetColors.OnAccent,
    tertiaryContainer = WadgetColors.AccentContainer,
    onTertiaryContainer = WadgetColors.Accent,
    background = WadgetColors.Background,
    onBackground = WadgetColors.Text,
    surface = WadgetColors.Surface,
    onSurface = WadgetColors.Text,
    surfaceVariant = WadgetColors.Elevated,
    onSurfaceVariant = WadgetColors.Muted,
    surfaceTint = Color.Transparent,
    surfaceDim = WadgetColors.Background,
    surfaceBright = WadgetColors.Elevated,
    surfaceContainerLowest = WadgetColors.Background,
    surfaceContainerLow = WadgetColors.Surface,
    surfaceContainer = WadgetColors.Surface,
    surfaceContainerHigh = WadgetColors.Elevated,
    surfaceContainerHighest = WadgetColors.Elevated,
    inverseSurface = WadgetColors.Text,
    inverseOnSurface = WadgetColors.Background,
    inversePrimary = WadgetColors.OnAccent,
    outline = WadgetColors.Muted,
    outlineVariant = WadgetColors.Border,
    error = WadgetColors.Danger,
    onError = WadgetColors.Background,
    errorContainer = WadgetColors.DangerContainer,
    onErrorContainer = WadgetColors.Danger
)

private val WadgetTypography = Typography(
    headlineMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.5).sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp
    )
)

@Composable
fun WadgetTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = WadgetColorScheme,
        typography = WadgetTypography,
        shapes = Shapes(
            small = WadgetShapes.Control,
            medium = WadgetShapes.Card,
            large = WadgetShapes.Card
        ),
        content = content
    )
}
