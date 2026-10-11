package com.jn.wadget.ui

import com.jn.wadget.ui.navigation.NavigationDrawer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.jn.wadget.state.BudgetScreenViewModel
import com.jn.wadget.state.TagEditorViewModel
import com.jn.wadget.state.MainScreenState
import com.jn.wadget.state.SettingsScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: MainScreenState,
    tagEditorViewModel: TagEditorViewModel,
    settingsViewModel: SettingsScreenViewModel,
    budgetViewModel: BudgetScreenViewModel
) {
    NavigationDrawer(state, tagEditorViewModel, budgetViewModel)
}
