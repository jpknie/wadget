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

object WadgetPalette {
    val Canvas = Color(0xFF121619)
    val Surface = Color(0xFF1B2227)
    val Raised = Color(0xFF242D33)
    val Border = Color(0xFF354149)
    val Text = Color(0xFFF3F1EB)
    val Muted = Color(0xFFAAB7BF)
    val Teal = Color(0xFF63C6B0)
    val TealPressed = Color(0xFF87D7C5)
    val TealSubtle = Color(0xFF223F39)
    val Destructive = Color(0xFFFF9A96)
}

object WadgetSpacing {
    val Small = 8.dp
    val Medium = 16.dp
    val Large = 24.dp
    val EditorWidth = 800.dp
    val TouchTarget = 48.dp
}

private val wadgetTypography = Typography(
    headlineMedium = TextStyle(
        fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp, lineHeight = 38.sp, letterSpacing = (-0.5).sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp, lineHeight = 30.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp, lineHeight = 26.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif, fontSize = 16.sp, lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif, fontSize = 14.sp, lineHeight = 20.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp, lineHeight = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Medium,
        fontSize = 12.sp, lineHeight = 16.sp
    )
)

@Composable
fun WadgetTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = WadgetPalette.Teal,
            onPrimary = WadgetPalette.Canvas,
            primaryContainer = WadgetPalette.TealSubtle,
            onPrimaryContainer = WadgetPalette.Teal,
            secondary = WadgetPalette.Teal,
            onSecondary = WadgetPalette.Canvas,
            secondaryContainer = WadgetPalette.TealSubtle,
            onSecondaryContainer = WadgetPalette.Text,
            tertiary = WadgetPalette.Teal,
            background = WadgetPalette.Canvas,
            onBackground = WadgetPalette.Text,
            surface = WadgetPalette.Surface,
            onSurface = WadgetPalette.Text,
            surfaceVariant = WadgetPalette.Raised,
            onSurfaceVariant = WadgetPalette.Muted,
            surfaceTint = WadgetPalette.Teal,
            surfaceContainerLowest = WadgetPalette.Canvas,
            surfaceContainerLow = WadgetPalette.Surface,
            surfaceContainer = WadgetPalette.Surface,
            surfaceContainerHigh = WadgetPalette.Raised,
            surfaceContainerHighest = WadgetPalette.Raised,
            outline = WadgetPalette.Muted,
            outlineVariant = WadgetPalette.Border,
            error = WadgetPalette.Destructive,
            onError = WadgetPalette.Canvas
        ),
        typography = wadgetTypography,
        shapes = Shapes(
            extraSmall = RoundedCornerShape(6.dp),
            small = RoundedCornerShape(10.dp),
            medium = RoundedCornerShape(14.dp),
            large = RoundedCornerShape(18.dp),
            extraLarge = RoundedCornerShape(24.dp)
        ),
        content = content
    )
}
