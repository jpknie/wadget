package com.jn.wadget.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jn.wadget.state.TagEditorViewModel
import com.jn.wadget.state.MainScreenState
import com.jn.wadget.state.Screens
import com.jn.wadget.ui.TagEditorScreen
import com.jn.wadget.ui.theme.WadgetPalette
import com.jn.wadget.ui.theme.WadgetSpacing
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(state: MainScreenState,
                     tagEditorViewModel: TagEditorViewModel) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val itemColors = NavigationDrawerItemDefaults.colors(
        selectedContainerColor = WadgetPalette.TealSubtle,
        selectedIconColor = WadgetPalette.Teal,
        selectedTextColor = WadgetPalette.Text,
        unselectedContainerColor = WadgetPalette.Surface,
        unselectedIconColor = WadgetPalette.Muted,
        unselectedTextColor = WadgetPalette.Muted
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = WadgetPalette.Surface,
                drawerContentColor = WadgetPalette.Text,
                drawerTonalElevation = 0.dp
            ) {

                Text(
                    "Navigation",
                    style = MaterialTheme.typography.labelLarge,
                    color = WadgetPalette.Muted,
                    modifier = Modifier.padding(WadgetSpacing.Large)
                )

                NavigationDrawerItem(
                    modifier = Modifier.padding(horizontal = WadgetSpacing.Small, vertical = 4.dp),
                    shape = MaterialTheme.shapes.small,
                    colors = itemColors,
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Home") },
                    selected = state.currentScreen == Screens.HomeScreen,
                    onClick = {
                        scope.launch {
                            state.currentScreen = Screens.HomeScreen
                            drawerState.close()
                        }
                    }
                )

                NavigationDrawerItem(
                    modifier = Modifier.padding(horizontal = WadgetSpacing.Small, vertical = 4.dp),
                    shape = MaterialTheme.shapes.small,
                    colors = itemColors,
                    icon = { Icon(Icons.Default.Category, contentDescription = null) },
                    label = { Text("Categories") },
                    selected = state.currentScreen == Screens.TagScreen,
                    onClick = {
                        scope.launch {
                            state.currentScreen = Screens.TagScreen
                            drawerState.close()
                        }
                    }
                )

                NavigationDrawerItem(
                    modifier = Modifier.padding(horizontal = WadgetSpacing.Small, vertical = 4.dp),
                    shape = MaterialTheme.shapes.small,
                    colors = itemColors,
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                    label = { Text("Settings") },
                    selected = state.currentScreen == Screens.SettingsScreen,
                    onClick = {
                        state.currentScreen = Screens.SettingsScreen
                        scope.launch { drawerState.close() }
                    }
                )

                NavigationDrawerItem(
                    modifier = Modifier.padding(horizontal = WadgetSpacing.Small, vertical = 4.dp),
                    shape = MaterialTheme.shapes.small,
                    colors = itemColors,
                    icon = { Icon(Icons.Default.Info, contentDescription = null) },
                    label = { Text("About") },
                    selected = state.currentScreen == Screens.AboutScreen,
                    onClick = {
                        scope.launch {
                            state.currentScreen = Screens.AboutScreen
                            drawerState.close()
                        }
                    }
                )
            }
        }
    ) {

        Scaffold(
            containerColor = WadgetPalette.Canvas,
            topBar = {
                TopBar(drawerState, scope)
            }
        ) { paddingValues ->

            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when(state.currentScreen) {
                    Screens.HomeScreen -> {
                        Text("HomeScreen")
                    }
                    Screens.TagScreen -> {
                        val tagState by tagEditorViewModel.state
                        TagEditorScreen(
                            state = tagState,
                            onAddTag = { name ->
                                scope.launch {
                                    tagEditorViewModel.onAddTag(name)
                                }
                            },
                            onEditTag = {
                                scope.launch {
                                    tagEditorViewModel.onEditTag(it)
                                }
                            },
                            onToggleMandatory = { tag, isMandatory ->
                                scope.launch {
                                    tagEditorViewModel.onToggleMandatory(
                                        tag,
                                        isMandatory
                                    )
                                }
                            },
                            onWeightChange = { tag, weight ->
                                scope.launch {
                                    tagEditorViewModel.onWeightChange(tag, weight)
                                }
                            },
                            
                            onDeleteTag = { tag ->
                                scope.launch {
                                    tagEditorViewModel.onDeleteTag(tag)
                                }
                            }
                        )
                    }
                    Screens.SettingsScreen -> {
                        Text("SettingsScreen")
                    }
                    Screens.AboutScreen -> {
                        Text("AboutScreen")
                    }
                }
            }
        }
    }
}
/*
Card(modifier = Modifier.fillMaxWidth()) {

    when(state.currentScreen) {
        Screens.TagScreen -> {
            val tagState by tagEditorViewModel.state
            TagEditorScreen(
                state = tagState,
                onAddTag = { name ->
                    scope.launch {
                        tagEditorViewModel.onAddTag(name)
                    }
                },
                onEditTag = { tagEditorViewModel.onEditTag(it) },
                onToggleMandatory = { tag, isMandatory -> tagEditorViewModel.onToggleMandatory(tag, isMandatory) },
                onWeightChange = { tag, weight -> tagEditorViewModel.onWeightChange(tag, weight) }
            )
        }
    }
}*/
