package com.jn.wadget.ui

import com.jn.wadget.ui.navigation.AppShell
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.jn.wadget.state.BudgetScreenViewModel
import com.jn.wadget.state.ComputationsViewModel
import com.jn.wadget.state.TagEditorViewModel
import com.jn.wadget.state.MainScreenState
import com.jn.wadget.state.SettingsScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: MainScreenState,
    tagEditorViewModel: TagEditorViewModel,
    computationsViewModel: ComputationsViewModel,
    settingsViewModel: SettingsScreenViewModel,
    budgetViewModel: BudgetScreenViewModel
) {
    AppShell(state, tagEditorViewModel, computationsViewModel, budgetViewModel)
}
