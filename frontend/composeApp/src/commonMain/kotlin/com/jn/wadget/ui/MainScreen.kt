package com.jn.wadget.ui

import com.jn.wadget.ui.navigation.NavigationDrawer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.jn.wadget.state.CategoryEditorViewModel
import com.jn.wadget.state.MainScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: MainScreenState,
    categoryEditorViewModel: CategoryEditorViewModel
) {
    NavigationDrawer(state, categoryEditorViewModel)
}
    /*
    Card(modifier = Modifier.fillMaxWidth()) {

        when(state.currentScreen) {
            Screens.CategoryScreen -> {

        }
    }*/

