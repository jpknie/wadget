package com.jn.wadget.ui

import com.jn.wadget.ui.navigation.NavigationDrawer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.jn.wadget.state.TagEditorViewModel
import com.jn.wadget.state.MainScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: MainScreenState,
    tagEditorViewModel: TagEditorViewModel
) {
    NavigationDrawer(state, tagEditorViewModel)
}
    /*
    Card(modifier = Modifier.fillMaxWidth()) {

        when(state.currentScreen) {
            Screens.TagScreen -> {

        }
    }*/

