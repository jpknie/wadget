package com.jn.wadget

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.jn.wadget.state.CategoryEditorViewModel
import com.jn.wadget.ui.CategoryEditorScreen

@Composable
@Preview
fun App(viewModel: CategoryEditorViewModel) {
    MaterialTheme {
        val state by viewModel.state
        CategoryEditorScreen(
            state = state,
            onAddCategory = { name, type -> viewModel.onAddCategory(name, type) },
            onEditCategory = { viewModel.onEditCategory(it) },
            onToggleMandatory = { category, isMandatory -> viewModel.onToggleMandatory(category, isMandatory) },
            onWeightChange = { category, weight -> viewModel.onWeightChange(category, weight) }
        )
    }
}