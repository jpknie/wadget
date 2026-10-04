package com.jn.wadget

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.jn.wadget.computeclient.ComputeGatewayClientImpl
import com.jn.wadget.repository.TagRepository
import com.jn.wadget.state.TagEditorViewModel
import com.jn.wadget.state.MainScreenViewModel
import com.jn.wadget.ui.MainScreen
import com.jn.wadget.ui.theme.WadgetTheme

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
    WadgetTheme {
        MainScreen(mainState,
            tagEditorViewModel)
    }
}
