package com.jn.wadget.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jn.wadget.models.Category
import com.jn.wadget.models.CategoryType
import com.jn.wadget.state.CategoryEditorState

@Composable
fun CategoryRow(
    category: Category,
    onToggleMandatory: (Boolean) -> Unit,
    onWeightChange: (Float) -> Unit,
    onEdit: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )

                Switch(
                    checked = category.isMandatory,
                    onCheckedChange = onToggleMandatory
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Slider(
                value = category.weight,
                onValueChange = onWeightChange,
                valueRange = 0f..100f
            )

            Text("Weight: ${category.weight.toInt()}%")

            Spacer(modifier = Modifier.height(4.dp))

            TextButton(onClick = onEdit) {
                Text("Edit")
            }
        }
    }
}

@Composable
fun CategoryEditorScreen(
    state: CategoryEditorState,
    onAddCategory: (String, CategoryType) -> Unit,
    onEditCategory: (category: Category) -> Unit,
    onToggleMandatory: (category: Category, Boolean) -> Unit,
    onWeightChange: (category: Category, Float) -> Unit
) {
    var newCategoryName by remember { mutableStateOf("") }

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
            items(state.categories, key = { it.id }) { category ->
                CategoryRow(
                    category = category,
                    onToggleMandatory = { newValue -> onToggleMandatory(category, newValue) },
                    onWeightChange = { newWeight -> onWeightChange(category, newWeight) },
                    onEdit = { onEditCategory(category) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add new category section
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = newCategoryName,
                onValueChange = { newCategoryName = it },
                modifier = Modifier.weight(1f),
                label = { Text("New category") }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                if (newCategoryName.isNotBlank()) {
                    onAddCategory(newCategoryName, CategoryType.EXPENSE)
                    newCategoryName = ""
                }
            }) {
                Text("Add")
            }
        }
    }
}
