package com.jn.wadget.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.jn.wadget.ui.theme.WadgetColors
import com.jn.wadget.ui.theme.WadgetShapes
import com.jn.wadget.ui.theme.WadgetSpacing

@Composable
fun WadgetCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = WadgetShapes.Card,
        color = WadgetColors.Surface,
        contentColor = WadgetColors.Text,
        border = BorderStroke(1.dp, WadgetColors.Border),
        shadowElevation = 1.dp,
        content = content
    )
}

@Composable
fun WadgetButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    val pressed by interactionSource.collectIsPressedAsState()
    val color by animateColorAsState(
        targetValue = when {
            pressed -> WadgetColors.Accent.copy(alpha = 0.8f)
            hovered || focused -> Color(0xFFA0E0D4)
            else -> WadgetColors.Accent
        }
    )

    Button(
        onClick = onClick,
        modifier = modifier.height(52.dp),
        shape = WadgetShapes.Control,
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = WadgetColors.OnAccent
        ),
        elevation = null,
        contentPadding = PaddingValues(horizontal = WadgetSpacing.Medium),
        interactionSource = interactionSource
    ) {
        if (leadingIcon != null) {
            Icon(leadingIcon, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(WadgetSpacing.Small))
        }
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
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    val pressed by interactionSource.collectIsPressedAsState()
    val highlighted = hovered || focused || pressed
    val background by animateColorAsState(
        targetValue = when {
            !highlighted -> Color.Transparent
            destructive -> WadgetColors.DangerContainer
            else -> WadgetColors.Elevated
        }
    )
    val foreground by animateColorAsState(
        targetValue = when {
            destructive && highlighted -> WadgetColors.Danger
            highlighted -> WadgetColors.Text
            else -> WadgetColors.Muted
        }
    )

    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(48.dp)
            .background(background, WadgetShapes.Control)
            .border(
                1.dp,
                if (focused) WadgetColors.Accent else Color.Transparent,
                WadgetShapes.Control
            ),
        colors = IconButtonDefaults.iconButtonColors(contentColor = foreground),
        interactionSource = interactionSource
    ) {
        Icon(icon, contentDescription = description, modifier = Modifier.size(20.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WadgetWeightSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val dragged by interactionSource.collectIsDraggedAsState()
    val hovered by interactionSource.collectIsHoveredAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    val thumbColor by animateColorAsState(
        targetValue = if (dragged || hovered || focused) WadgetColors.Text else WadgetColors.Accent
    )
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl

    Slider(
        value = value,
        onValueChange = onValueChange,
        onValueChangeFinished = onValueChangeFinished,
        valueRange = 0f..100f,
        modifier = modifier.semantics { contentDescription = "Weight" },
        interactionSource = interactionSource,
        thumb = {
            Box(
                Modifier
                    .size(20.dp)
                    .shadow(3.dp, CircleShape)
                    .background(thumbColor, CircleShape)
                    .border(3.dp, WadgetColors.Surface, CircleShape)
            )
        },
        track = {
            Canvas(Modifier.fillMaxWidth().height(6.dp)) {
                val start = Offset(if (isRtl) size.width else 0f, size.height / 2)
                val end = Offset(if (isRtl) 0f else size.width, size.height / 2)
                val fraction = (value / 100f).coerceIn(0f, 1f)
                drawLine(
                    color = WadgetColors.Border,
                    start = start,
                    end = end,
                    strokeWidth = size.height,
                    cap = StrokeCap.Round
                )
                if (fraction > 0f) {
                    drawLine(
                        color = WadgetColors.Accent,
                        start = start,
                        end = Offset(start.x + (end.x - start.x) * fraction, start.y),
                        strokeWidth = size.height,
                        cap = StrokeCap.Round
                    )
                }
            }
        }
    )
}
