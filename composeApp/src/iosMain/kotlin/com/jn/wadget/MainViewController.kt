package com.jn.wadget

import androidx.compose.ui.window.ComposeUIViewController
import com.jn.wadget.repository.InMemoryCategoryRepository
import com.jn.wadget.state.CategoryEditorViewModel

fun MainViewController() = ComposeUIViewController {
    val repo = InMemoryCategoryRepository()
    val viewModel = CategoryEditorViewModel(repo)
    App(viewModel)
}