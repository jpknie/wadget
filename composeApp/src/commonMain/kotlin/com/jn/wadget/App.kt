package com.jn.wadget

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.jn.wadget.state.CategoryEditorViewModel
import com.jn.wadget.ui.CategoryEditorScreen
import kotlinx.coroutines.launch

@Composable
@Preview
fun App(viewModel: CategoryEditorViewModel) {
    val scope = rememberCoroutineScope()
    MaterialTheme {
        val state by viewModel.state
        CategoryEditorScreen(
            state = state,
            onAddCategory = { name ->
                scope.launch {
                    viewModel.onAddCategory(name)
                }
            },
            onEditCategory = { viewModel.onEditCategory(it) },
            onToggleMandatory = { category, isMandatory -> viewModel.onToggleMandatory(category, isMandatory) },
            onWeightChange = { category, weight -> viewModel.onWeightChange(category, weight) }
        )
    }
}