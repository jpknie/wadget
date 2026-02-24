package com.jn.wadget


import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.jn.wadget.computeclient.ComputeGatewayClientImpl
import com.jn.wadget.repository.CategoryRepository
import com.jn.wadget.state.CategoryEditorViewModel

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "wadget",
    ) {
        val repo = remember { CategoryRepository(ComputeGatewayClientImpl()) }
        val viewModel = remember { CategoryEditorViewModel(repo) }
        App(viewModel)
    }
}