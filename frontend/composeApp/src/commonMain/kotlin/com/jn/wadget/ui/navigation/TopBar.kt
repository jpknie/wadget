package com.jn.wadget.ui.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextOverflow
import com.jn.wadget.ui.components.WadgetIconAction
import com.jn.wadget.ui.theme.WadgetPalette
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(drawerState: DrawerState,
           coroutineScope: CoroutineScope) {

    CenterAlignedTopAppBar(
        modifier = Modifier.fillMaxWidth().drawWithContent {
            drawContent()
            drawLine(
                WadgetPalette.Border,
                Offset(0f, size.height - 0.5.dp.toPx()),
                Offset(size.width, size.height - 0.5.dp.toPx()),
                strokeWidth = 1.dp.toPx()
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = WadgetPalette.Surface,
            scrolledContainerColor = WadgetPalette.Surface,
            titleContentColor = WadgetPalette.Text
        ),
        title = {
            Text(
                text = "Wadget v0.1",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            WadgetIconAction(
                icon = Icons.Default.Menu,
                description = "Menu",
                onClick = { coroutineScope.launch { drawerState.open() } }
            )
        },
        actions = {
            WadgetIconAction(Icons.Default.Search, "Search", onClick = {})
            WadgetIconAction(Icons.Default.Settings, "Settings", onClick = {})
        }
    )
}
