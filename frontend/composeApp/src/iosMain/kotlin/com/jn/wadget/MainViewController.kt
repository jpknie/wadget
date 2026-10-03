package com.jn.wadget

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.jn.wadget.repository.TagRepository
import com.jn.wadget.state.TagEditorViewModel

fun MainViewController() = ComposeUIViewController {
    val repo = remember { TagRepository(ComputeGatewayClientImpl()) }
    val viewModel = TagEditorViewModel(repo)
    App(viewModel)
}

