package com.jn.wadget.ui

import NavigationDrawer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.jn.wadget.state.CategoryEditorViewModel
import com.jn.wadget.state.MainScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: MainScreenState,
    categoryEditorViewModel: CategoryEditorViewModel
) {
    NavigationDrawer(state)
}
    /*
    Card(modifier = Modifier.fillMaxWidth()) {

        when(state.currentScreen) {
            Screens.CategoryScreen -> {
                val categoryState by categoryEditorViewModel.state
                CategoryEditorScreen(
                    state = categoryState,
                    onAddCategory = { name ->
                        scope.launch {
                            categoryEditorViewModel.onAddCategory(name)
                        }
                    },
                    onEditCategory = { categoryEditorViewModel.onEditCategory(it) },
                    onToggleMandatory = { category, isMandatory -> categoryEditorViewModel.onToggleMandatory(category, isMandatory) },
                    onWeightChange = { category, weight -> categoryEditorViewModel.onWeightChange(category, weight) }
                )
            }
        }
    }*/

