package com.jn.wadget.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jn.domain.Tag
import com.jn.wadget.state.TagEditorState

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

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = tag.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )

                Switch(
                    checked = tag.mandatory,
                    onCheckedChange = onToggleMandatory
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Slider(
                value = sliderValue,
                onValueChange = {
                    sliderValue = it
                },
                onValueChangeFinished = {
                    onWeightChange(sliderValue.toDouble())
                },
                valueRange = 0f..100f
            )

            Text("Weight: ${sliderValue.toInt()}%")

            TextButton(onClick = onDelete) {
                Text("Delete")
            }

            Spacer(modifier = Modifier.height(4.dp))

            TextButton(onClick = onEdit) {
                Text("Edit")
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Categories", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
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

        Spacer(modifier = Modifier.height(16.dp))

        // Add new tag section
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = newTagName,
                onValueChange = { newTagName = it },
                modifier = Modifier.weight(1f),
                label = { Text("New category") }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                if (newTagName.isNotBlank()) {
                    onAddTag(newTagName)
                    newTagName = ""
                }
            }) {
                Text("Add")
            }
        }
    }
}