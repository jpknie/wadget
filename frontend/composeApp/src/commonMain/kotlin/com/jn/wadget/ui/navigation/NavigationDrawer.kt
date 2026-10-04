package com.jn.wadget.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.jn.wadget.ui.theme.WadgetColors
import com.jn.wadget.ui.theme.WadgetShapes
import com.jn.wadget.ui.theme.WadgetSpacing
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(state: MainScreenState,
                     tagEditorViewModel: TagEditorViewModel) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val drawerItemColors = NavigationDrawerItemDefaults.colors(
        selectedContainerColor = WadgetColors.AccentContainer,
        unselectedContainerColor = WadgetColors.Background,
        selectedIconColor = WadgetColors.Accent,
        unselectedIconColor = WadgetColors.Muted,
        selectedTextColor = WadgetColors.Accent,
        unselectedTextColor = WadgetColors.Muted
    )
    val drawerItemModifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = WadgetColors.Background,
                drawerContentColor = WadgetColors.Text
            ) {
                Column(modifier = Modifier.padding(WadgetSpacing.Large)) {
                    Text("Wadget", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(WadgetSpacing.Small))
                    Text(
                        "Navigation",
                        style = MaterialTheme.typography.labelMedium,
                        color = WadgetColors.Muted
                    )
                }
                HorizontalDivider(color = WadgetColors.Border)
                Spacer(Modifier.height(WadgetSpacing.Small))

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Home") },
                    selected = state.currentScreen == Screens.HomeScreen,
                    modifier = drawerItemModifier,
                    shape = WadgetShapes.Control,
                    colors = drawerItemColors,
                    onClick = {
                        scope.launch {
                            state.currentScreen = Screens.HomeScreen
                            drawerState.close()
                        }
                    }
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Category, contentDescription = null) },
                    label = { Text("Categories") },
                    selected = state.currentScreen == Screens.TagScreen,
                    modifier = drawerItemModifier,
                    shape = WadgetShapes.Control,
                    colors = drawerItemColors,
                    onClick = {
                        scope.launch {
                            state.currentScreen = Screens.TagScreen
                            drawerState.close()
                        }
                    }
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                    label = { Text("Settings") },
                    selected = state.currentScreen == Screens.SettingsScreen,
                    modifier = drawerItemModifier,
                    shape = WadgetShapes.Control,
                    colors = drawerItemColors,
                    onClick = {
                        state.currentScreen = Screens.SettingsScreen
                        scope.launch { drawerState.close() }
                    }
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = null) },
                    label = { Text("About") },
                    selected = state.currentScreen == Screens.AboutScreen,
                    modifier = drawerItemModifier,
                    shape = WadgetShapes.Control,
                    colors = drawerItemColors,
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
            containerColor = WadgetColors.Background,
            contentColor = WadgetColors.Text,
            topBar = {
                TopBar(drawerState, scope)
            }
        ) { paddingValues ->

            Box(
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
