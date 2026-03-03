package com.jn.wadget

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.jn.wadget.computeclient.ComputeGatewayClientImpl
import com.jn.wadget.repository.CategoryRepository
import com.jn.wadget.state.CategoryEditorViewModel
import com.jn.wadget.state.MainScreenViewModel
import com.jn.wadget.ui.MainScreen

@Composable
fun App(viewModel: MainScreenViewModel) {
    val mainState by viewModel.state
    val categoryEditorViewModel = remember {
        CategoryEditorViewModel(
            CategoryRepository(
                ComputeGatewayClientImpl()
            )
        )
    }
    MaterialTheme {
        MainScreen(mainState,
            categoryEditorViewModel)
    }
}