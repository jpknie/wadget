package com.jn.wadget

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.jn.wadget.computeclient.ComputeGatewayClientImpl
import com.jn.wadget.repository.SettingsRepository
import com.jn.wadget.repository.TagRepository
import com.jn.wadget.state.BudgetScreenViewModel
import com.jn.wadget.state.ComputationsViewModel
import com.jn.wadget.state.TagEditorViewModel
import com.jn.wadget.state.MainScreenViewModel
import com.jn.wadget.state.SettingsScreenViewModel
import com.jn.wadget.ui.MainScreen
import com.jn.wadget.ui.theme.WadgetTheme

@Composable
fun App(viewModel: MainScreenViewModel) {

    val computeGateway = remember { ComputeGatewayClientImpl() }
    val mainState by viewModel.state
    val computationsViewModel = remember {
        ComputationsViewModel(
            computeGateway
        )
    }
    val tagEditorViewModel = remember {
        TagEditorViewModel(
            TagRepository(
                computeGateway
            )
        )
    }

    val settingsViewModel = remember {
        SettingsScreenViewModel(
            SettingsRepository(
                computeGateway
            )
        )
    }

    val budgetViewModel = remember {
        BudgetScreenViewModel()
    }

    WadgetTheme {
        MainScreen(mainState,
            tagEditorViewModel,
            computationsViewModel,

            settingsViewModel,
            budgetViewModel)
    }
}
