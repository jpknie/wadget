package com.jn.wadget

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.jn.wadget.computeclient.ComputeGatewayClient
import com.jn.wadget.computeclient.ComputeGatewayClientImpl
import com.jn.wadget.repository.CategoryRepository
import com.jn.wadget.state.CategoryEditorViewModel

fun MainViewController() = ComposeUIViewController {
    val repo = remember { CategoryRepository(ComputeGatewayClientImpl()) }
    val viewModel = CategoryEditorViewModel(repo)
    App(viewModel)
}

