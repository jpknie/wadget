import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jn.wadget.state.MainScreenState
import com.jn.wadget.state.Screens
import com.jn.wadget.ui.navigation.TopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(state: MainScreenState) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {

                Text(
                    "Navigation",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )

                NavigationDrawerItem(
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
                    icon = { Icon(Icons.Default.Category, contentDescription = null) },
                    label = { Text("Categories") },
                    selected = state.currentScreen == Screens.CategoryScreen,
                    onClick = {
                        scope.launch {
                            state.currentScreen = Screens.CategoryScreen
                            drawerState.close()
                        }
                    }
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                    label = { Text("Settings") },
                    selected = state.currentScreen == Screens.SettingsScreen,
                    onClick = {
                        state.currentScreen = Screens.SettingsScreen
                        scope.launch { drawerState.close() }
                    }
                )

                NavigationDrawerItem(
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
            topBar = {
                TopBar(drawerState, scope)
            }
        ) { paddingValues ->

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                when(state.currentScreen) {
                    Screens.HomeScreen -> {
                        Text("HomeScreen")
                    }
                    Screens.CategoryScreen -> {
                        Text("CategoryScreen")
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
        Screens.CategoryScreen -> {
            val categoryState by categoryEditorViewModel.state
            CategoryEditorScreen(
                state = categoryState,
                onAddCategory = { name ->
                    scope.launch {
                        categoryEditorViewModel.onAddCategory(name)
                    }
                },
                onEditCategory = { categoryEditorViewModel.onEditCategory(it) },
                onToggleMandatory = { category, isMandatory -> categoryEditorViewModel.onToggleMandatory(category, isMandatory) },
                onWeightChange = { category, weight -> categoryEditorViewModel.onWeightChange(category, weight) }
            )
        }
    }
}*/
