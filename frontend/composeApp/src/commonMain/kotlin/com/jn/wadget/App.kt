package com.jn.wadget

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.jn.wadget.computeclient.ComputeGatewayClientImpl
import com.jn.wadget.repository.TagRepository
import com.jn.wadget.state.TagEditorViewModel
import com.jn.wadget.state.MainScreenViewModel
import com.jn.wadget.ui.MainScreen

@Composable
fun App(viewModel: MainScreenViewModel) {
    val mainState by viewModel.state
    val tagEditorViewModel = remember {
        TagEditorViewModel(
            TagRepository(
                ComputeGatewayClientImpl()
            )
        )
    }
    MaterialTheme {
        MainScreen(mainState,
            tagEditorViewModel)
    }
}