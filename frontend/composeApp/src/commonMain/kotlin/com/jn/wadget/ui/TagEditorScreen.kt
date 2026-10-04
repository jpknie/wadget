package com.jn.wadget.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.jn.domain.Tag
import com.jn.wadget.state.TagEditorState
import com.jn.wadget.ui.components.WadgetButton
import com.jn.wadget.ui.components.WadgetCard
import com.jn.wadget.ui.components.WadgetIconAction
import com.jn.wadget.ui.components.WadgetWeightSlider
import com.jn.wadget.ui.theme.WadgetColors
import com.jn.wadget.ui.theme.WadgetShapes
import com.jn.wadget.ui.theme.WadgetSpacing

@Composable
fun TagRow(
    tag: Tag,
    onToggleMandatory: (Boolean) -> Unit,
    onWeightChange: (Double) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var sliderValue by remember(tag.id, tag.weight) {
        mutableStateOf(tag.weight.toFloat())
    }

    WadgetCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(WadgetSpacing.Medium)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = tag.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = WadgetSpacing.Small)
                )

                WadgetIconAction(
                    icon = Icons.Outlined.Edit,
                    description = "Edit ${tag.name}",
                    onClick = onEdit
                )
                WadgetIconAction(
                    icon = Icons.Outlined.Delete,
                    description = "Delete ${tag.name}",
                    onClick = onDelete,
                    destructive = true
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = WadgetSpacing.Small),
                color = WadgetColors.Border
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Mandatory", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        if (tag.mandatory) "Required category" else "Optional category",
                        style = MaterialTheme.typography.labelMedium,
                        color = WadgetColors.Muted
                    )
                }
                Switch(
                    checked = tag.mandatory,
                    onCheckedChange = onToggleMandatory,
                    modifier = Modifier.semantics {
                        contentDescription = "Mandatory for ${tag.name}"
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = WadgetColors.OnAccent,
                        checkedTrackColor = WadgetColors.Accent,
                        checkedBorderColor = WadgetColors.Accent,
                        uncheckedThumbColor = WadgetColors.Muted,
                        uncheckedTrackColor = WadgetColors.Elevated,
                        uncheckedBorderColor = WadgetColors.Border
                    )
                )
            }

            Spacer(modifier = Modifier.height(WadgetSpacing.Medium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Weight",
                    style = MaterialTheme.typography.bodyMedium,
                    color = WadgetColors.Muted
                )
                Text(
                    "${sliderValue.toInt()}%",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFeatureSettings = "tnum"
                    ),
                    color = WadgetColors.Accent
                )
            }

            WadgetWeightSlider(
                value = sliderValue,
                onValueChange = {
                    sliderValue = it
                },
                onValueChangeFinished = {
                    onWeightChange(sliderValue.toDouble())
                },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("0%", style = MaterialTheme.typography.labelMedium, color = WadgetColors.Muted)
                Text("100%", style = MaterialTheme.typography.labelMedium, color = WadgetColors.Muted)
            }
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
    onDeleteTag: (tag: Tag) -> Unit
) {
    var newTagName by remember { mutableStateOf("") }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(WadgetColors.Background),
        contentAlignment = Alignment.TopCenter
    ) {
        val compact = maxWidth < 480.dp
        val padding = if (compact) WadgetSpacing.Medium else WadgetSpacing.Large
        Column(
            modifier = Modifier
                .widthIn(max = 880.dp)
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = WadgetSpacing.Medium),
                verticalArrangement = Arrangement.spacedBy(WadgetSpacing.Medium)
            ) {
                item(key = "heading") {
                    Column(modifier = Modifier.padding(bottom = WadgetSpacing.Small)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "Categories",
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier
                                    .weight(1f)
                                    .semantics { heading() }
                            )
                            Surface(
                                color = WadgetColors.Elevated,
                                contentColor = WadgetColors.Muted,
                                shape = WadgetShapes.Control
                            ) {
                                Text(
                                    "${state.tags.size}",
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier.padding(
                                        horizontal = 12.dp,
                                        vertical = WadgetSpacing.Small
                                    )
                                )
                            }
                        }
                        Spacer(Modifier.height(WadgetSpacing.Small))
                        Text(
                            "Fine-tune the priorities behind your budget.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = WadgetColors.Muted
                        )
                    }
                }

                if (state.tags.isEmpty()) {
                    item(key = "empty") {
                        WadgetCard(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(WadgetSpacing.Large)) {
                                Text(
                                    "Make room for what matters",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(Modifier.height(WadgetSpacing.Small))
                                Text(
                                    "Add your first category below to start shaping your budget.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = WadgetColors.Muted
                                )
                            }
                        }
                    }
                }

                items(state.tags, key = { it.id }) { tag ->
                    TagRow(
                        tag = tag,
                        onToggleMandatory = { newValue -> onToggleMandatory(tag, newValue) },
                        onWeightChange = { newWeight -> onWeightChange(tag, newWeight) },
                        onEdit = { onEditTag(tag) },
                        onDelete = { onDeleteTag(tag) }
                    )
                }
            }

            WadgetCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(WadgetSpacing.Medium)) {
                    Text(
                        "Add a category",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(WadgetSpacing.Small))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(WadgetSpacing.Small)
                    ) {
                        OutlinedTextField(
                            value = newTagName,
                            onValueChange = { newTagName = it },
                            modifier = Modifier.weight(1f),
                            label = { Text("New category") },
                            shape = WadgetShapes.Control,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = WadgetColors.Elevated,
                                unfocusedContainerColor = WadgetColors.Elevated,
                                focusedBorderColor = WadgetColors.Accent,
                                unfocusedBorderColor = WadgetColors.Border,
                                focusedLabelColor = WadgetColors.Accent,
                                unfocusedLabelColor = WadgetColors.Muted,
                                cursorColor = WadgetColors.Accent
                            )
                        )

                        WadgetButton(
                            text = "Add",
                            leadingIcon = if (compact) null else Icons.Default.Add,
                            onClick = {
                                if (newTagName.isNotBlank()) {
                                    onAddTag(newTagName)
                                    newTagName = ""
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
