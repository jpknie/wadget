package com.jn.wadget.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.LayoutDirection
import com.jn.wadget.ui.theme.WadgetPalette
import com.jn.wadget.ui.theme.WadgetSpacing

@Composable
private fun interactionColor(
    source: MutableInteractionSource,
    idle: Color,
    hover: Color,
    active: Color
): Color {
    val pressed by source.collectIsPressedAsState()
    val dragged by source.collectIsDraggedAsState()
    val focused by source.collectIsFocusedAsState()
    val hovered by source.collectIsHoveredAsState()
    val color by animateColorAsState(
        if (pressed || dragged || focused) active else if (hovered) hover else idle,
        animationSpec = tween(120)
    )
    return color
}

@Composable
fun WadgetSurface(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        color = WadgetPalette.Surface,
        border = BorderStroke(1.dp, WadgetPalette.Border),
        shadowElevation = 2.dp,
        content = content
    )
}

@Composable
fun WadgetButton(onClick: () -> Unit, modifier: Modifier = Modifier, text: String) {
    val source = remember { MutableInteractionSource() }
    val color = interactionColor(
        source, WadgetPalette.Teal, WadgetPalette.TealPressed, WadgetPalette.TealPressed
    )
    val border = interactionColor(
        source, Color.Transparent, WadgetPalette.Text.copy(alpha = 0.4f), WadgetPalette.Text
    )
    Button(
        onClick = onClick,
        modifier = modifier.heightIn(min = WadgetSpacing.TouchTarget),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(containerColor = color, contentColor = WadgetPalette.Canvas),
        border = BorderStroke(1.dp, border),
        interactionSource = source,
        contentPadding = PaddingValues(horizontal = WadgetSpacing.Large, vertical = 12.dp)
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun WadgetIconAction(
    icon: ImageVector,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    destructive: Boolean = false
) {
    val source = remember { MutableInteractionSource() }
    val accent = if (destructive) WadgetPalette.Destructive else WadgetPalette.Teal
    val background = interactionColor(
        source, Color.Transparent, WadgetPalette.Raised, accent.copy(alpha = 0.14f)
    )
    val border = interactionColor(source, Color.Transparent, WadgetPalette.Border, accent)
    IconButton(
        onClick = onClick,
        modifier = modifier.size(WadgetSpacing.TouchTarget)
            .background(background, MaterialTheme.shapes.small)
            .border(1.dp, border, MaterialTheme.shapes.small),
        interactionSource = source
    ) {
        Icon(
            icon, contentDescription = description,
            tint = if (destructive) WadgetPalette.Destructive else WadgetPalette.Muted,
            modifier = Modifier.size(20.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WadgetSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: () -> Unit,
    description: String,
    modifier: Modifier = Modifier
) {
    val source = remember { MutableInteractionSource() }
    val border = interactionColor(
        source, Color.Transparent, WadgetPalette.Border, WadgetPalette.Teal
    )
    val thumbColor = interactionColor(
        source, WadgetPalette.Teal, WadgetPalette.TealPressed, WadgetPalette.TealPressed
    )
    val rtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    Slider(
        value = value,
        onValueChange = onValueChange,
        onValueChangeFinished = onValueChangeFinished,
        valueRange = 0f..100f,
        interactionSource = source,
        modifier = modifier.fillMaxWidth().heightIn(min = WadgetSpacing.TouchTarget)
            .hoverable(source)
            .border(1.dp, border, MaterialTheme.shapes.small)
            .semantics { contentDescription = description },
        thumb = {
            Box(
                Modifier.size(20.dp)
                    .background(thumbColor, CircleShape)
                    .border(3.dp, WadgetPalette.Surface, CircleShape)
            )
        },
        track = { sliderState ->
            // Material owns dragging, thumb placement, keyboard input and range semantics.
            Canvas(Modifier.fillMaxWidth().height(4.dp)) {
                val y = size.height / 2
                val fraction = (sliderState.value / 100f).coerceIn(0f, 1f)
                val start = if (rtl) size.width else 0f
                val end = if (rtl) size.width * (1f - fraction) else size.width * fraction
                drawLine(
                    WadgetPalette.Muted.copy(alpha = 0.6f), Offset(0f, y), Offset(size.width, y),
                    strokeWidth = size.height, cap = StrokeCap.Round
                )
                if (fraction > 0f) {
                    drawLine(
                        WadgetPalette.Teal, Offset(start, y), Offset(end, y),
                        strokeWidth = size.height, cap = StrokeCap.Round
                    )
                }
            }
        }
    )
}
