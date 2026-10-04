package com.jn.wadget.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.border
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.jn.domain.Tag
import com.jn.wadget.state.TagEditorState
import com.jn.wadget.ui.components.*
import com.jn.wadget.ui.theme.WadgetPalette
import com.jn.wadget.ui.theme.WadgetSpacing

@Composable
fun TagRow(
    tag: Tag,
    onToggleMandatory: (Boolean) -> Unit,
    onWeightChange: (Double) -> Unit,
    onSoftnessChange: (Double) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var weightSliderValue by remember(tag.id, tag.weight) {
        mutableStateOf(tag.weight.toFloat())
    }

    var softnessSliderValue by remember(tag.id, tag.softness) {
        mutableStateOf(tag.softness.toFloat())
    }

    WadgetSurface(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(WadgetSpacing.Medium)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = tag.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f).padding(end = WadgetSpacing.Small)
                )
                WadgetIconAction(Icons.Outlined.Edit, "Edit ${tag.name}", onEdit)
                WadgetIconAction(
                    Icons.Outlined.Delete, "Delete ${tag.name}", onDelete, destructive = true
                )
            }
            Spacer(Modifier.height(WadgetSpacing.Small))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Mandatory",
                    style = MaterialTheme.typography.bodyMedium,
                    color = WadgetPalette.Muted,
                    modifier = Modifier.weight(1f).padding(end = WadgetSpacing.Small)
                )
                val source = remember { MutableInteractionSource() }
                val focused by source.collectIsFocusedAsState()
                val hovered by source.collectIsHoveredAsState()
                val pressed by source.collectIsPressedAsState()
                val border by animateColorAsState(
                    if (focused || pressed) WadgetPalette.Teal
                    else if (hovered) WadgetPalette.Border else Color.Transparent,
                    animationSpec = tween(120)
                )
                Switch(
                    checked = tag.mandatory,
                    onCheckedChange = onToggleMandatory,
                    interactionSource = source,
                    modifier = Modifier.width(64.dp).heightIn(min = WadgetSpacing.TouchTarget)
                        .border(1.dp, border, MaterialTheme.shapes.small)
                        .semantics { contentDescription = "Mandatory for ${tag.name}" },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = WadgetPalette.Canvas,
                        checkedTrackColor = WadgetPalette.Teal,
                        checkedBorderColor = WadgetPalette.Teal,
                        uncheckedThumbColor = WadgetPalette.Muted,
                        uncheckedTrackColor = WadgetPalette.Raised,
                        uncheckedBorderColor = WadgetPalette.Border
                    )
                )
            }
            Spacer(Modifier.height(WadgetSpacing.Small))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Weight",
                    style = MaterialTheme.typography.bodyMedium,
                    color = WadgetPalette.Muted,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    "${weightSliderValue.toInt()}%",
                    style = MaterialTheme.typography.headlineMedium,
                    color = WadgetPalette.Text
                )
            }
            WadgetSlider(
                value = weightSliderValue,
                onValueChange = {
                    weightSliderValue = it
                },
                onValueChangeFinished = {
                    onWeightChange(weightSliderValue.toDouble())
                },
                description = "Weight for ${tag.name}"
            )
            Spacer(Modifier.height(WadgetSpacing.Small))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Softness",
                    style = MaterialTheme.typography.bodyMedium,
                    color = WadgetPalette.Muted,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    "${softnessSliderValue.toInt()}%",
                    style = MaterialTheme.typography.headlineMedium,
                    color = WadgetPalette.Text
                )
            }
            WadgetSlider(
                value = softnessSliderValue,
                onValueChange = {
                    softnessSliderValue = it
                },
                onValueChangeFinished = {
                    onSoftnessChange(softnessSliderValue.toDouble())
                },
                description = "Softness for ${tag.name}"
            )
        }
    }
}

@Composable
fun TagEditorScreen(
    state: TagEditorState,
    onAddTag: (String) -> Unit,
    onEditTag: (tag: Tag) -> Unit,
    onToggleMandatory: (tag: Tag, Boolean) -> Unit,
    onWeightChange: (tag: Tag, Double) -> Unit,
    onSoftnessChange: (tag: Tag, Double) -> Unit,
    onDeleteTag: (tag: Tag) -> Unit
) {
    var newTagName by remember { mutableStateOf("") }

    BoxWithConstraints(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        val compact = maxWidth < 480.dp
        val gutter = if (compact) WadgetSpacing.Medium else WadgetSpacing.Large
        Column(
            modifier = Modifier
                .widthIn(max = WadgetSpacing.EditorWidth)
                .fillMaxSize()
                .padding(horizontal = gutter, vertical = WadgetSpacing.Medium)
        ) {
            Text("Tags", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(4.dp))
            Text(
                "Set priorities for your budget.",
                style = MaterialTheme.typography.bodyMedium,
                color = WadgetPalette.Muted
            )
            Spacer(Modifier.height(WadgetSpacing.Small))
            Text(
                "${state.tags.size} tags",
                style = MaterialTheme.typography.labelSmall,
                color = WadgetPalette.Muted
            )
            Spacer(modifier = Modifier.height(WadgetSpacing.Medium))

            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 2.dp),
                verticalArrangement = Arrangement.spacedBy(WadgetSpacing.Medium)
            ) {
                items(state.tags, key = { it.id }) { tag ->
                    TagRow(
                        tag = tag,
                        onToggleMandatory = { newValue -> onToggleMandatory(tag, newValue) },
                        onWeightChange = { newWeight -> onWeightChange(tag, newWeight) },
                        onSoftnessChange = { newSoftness -> onSoftnessChange(tag, newSoftness) },
                        onEdit = { onEditTag(tag) },
                        onDelete = { onDeleteTag(tag) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(WadgetSpacing.Medium))
            val addTag = {
                if (newTagName.isNotBlank()) {
                    onAddTag(newTagName)
                    newTagName = ""
                }
            }
            val input: @Composable (Modifier) -> Unit = { modifier ->
                OutlinedTextField(
                    value = newTagName,
                    onValueChange = { newTagName = it },
                    modifier = modifier,
                    label = { Text("New category") },
                    maxLines = 3,
                    shape = MaterialTheme.shapes.small,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = WadgetPalette.Teal,
                        unfocusedBorderColor = WadgetPalette.Border,
                        focusedContainerColor = WadgetPalette.Surface,
                        unfocusedContainerColor = WadgetPalette.Surface,
                        cursorColor = WadgetPalette.Teal
                    )
                )
            }
            if (compact) {
                Column(verticalArrangement = Arrangement.spacedBy(WadgetSpacing.Small)) {
                    input(Modifier.fillMaxWidth())
                    WadgetButton(onClick = addTag, modifier = Modifier.fillMaxWidth(), text = "Add")
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(WadgetSpacing.Small)
                ) {
                    input(Modifier.weight(1f))
                    WadgetButton(onClick = addTag, text = "Add")
                }
            }
        }
    }
}
