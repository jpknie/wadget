package com.jn.wadget

import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.jn.wadget.state.MainScreenViewModel

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        val viewModel = remember { MainScreenViewModel() }
        App(viewModel)
    }
}