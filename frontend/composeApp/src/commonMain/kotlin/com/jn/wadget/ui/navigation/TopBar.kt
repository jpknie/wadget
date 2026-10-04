package com.jn.wadget.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.jn.wadget.ui.components.WadgetIconAction
import com.jn.wadget.ui.theme.WadgetColors
import com.jn.wadget.ui.theme.WadgetSpacing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(drawerState: DrawerState,
           coroutineScope: CoroutineScope) {

    Column {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = WadgetColors.Background,
                titleContentColor = WadgetColors.Text
            ),
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Wadget",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.width(WadgetSpacing.Small))
                    Text(
                        text = "v0.1",
                        style = MaterialTheme.typography.labelMedium,
                        color = WadgetColors.Muted
                    )
                }
            },
            navigationIcon = {
                WadgetIconAction(
                    icon = Icons.Default.Menu,
                    description = "Menu",
                    onClick = { coroutineScope.launch { drawerState.open() } }
                )
            },
            actions = {
                WadgetIconAction(
                    icon = Icons.Default.Search,
                    description = "Search",
                    onClick = {}
                )
                WadgetIconAction(
                    icon = Icons.Default.Settings,
                    description = "Settings",
                    onClick = {}
                )
            }
        )
        HorizontalDivider(color = WadgetColors.Border)
    }
}
