package com.jn.wadget.state

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel

class MainScreenViewModel: ViewModel() {
    // _state is a MutableState<CategoryEditorState>
    private var _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> get() = _state

}