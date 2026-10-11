package com.jn.wadget.state

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob


class BudgetScreenViewModel : ViewModel() {
    private var _state = mutableStateOf(BudgetScreenState())
    val state: State<BudgetScreenState> get() = _state
    val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
}

