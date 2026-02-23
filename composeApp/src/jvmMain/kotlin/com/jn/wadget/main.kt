package com.jn.wadget

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.jn.wadget.repository.InMemoryCategoryRepository
import com.jn.wadget.state.CategoryEditorViewModel

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "wadget",
    ) {
        val repo = remember { InMemoryCategoryRepository() }
        val viewModel = remember { CategoryEditorViewModel(repo) }
        App(viewModel)
    }
}