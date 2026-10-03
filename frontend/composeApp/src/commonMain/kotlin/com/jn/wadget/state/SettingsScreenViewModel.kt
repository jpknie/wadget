package com.jn.wadget.state

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jn.wadget.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

data class SettingsScreenViewModel(val settingsRepository: SettingsRepository) : ViewModel() {
    private var _state = mutableStateOf(SettingsScreenState())
    val state: State<SettingsScreenState> get() = _state
    val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
}