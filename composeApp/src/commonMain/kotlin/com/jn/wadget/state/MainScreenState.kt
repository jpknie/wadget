package com.jn.wadget.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class Screens {
    HomeScreen,
    CategoryScreen,
    SettingsScreen,
    AboutScreen
}

class MainScreenState(initialScreen: Screens = Screens.HomeScreen
) {
    var currentScreen by mutableStateOf(initialScreen)
}