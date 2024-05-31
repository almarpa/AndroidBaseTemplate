package com.example.androidbasetemplate.ui.common.bottomappbar

import android.content.res.Configuration
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ManageSearch
import androidx.compose.material.icons.filled.PeopleOutline
import androidx.compose.material.icons.outlined.PeopleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.ui.common.navigation.NavigationActions
import com.example.androidbasetemplate.ui.common.navigation.Routes
import com.example.androidbasetemplate.ui.theme.TemplateTheme
import kotlinx.coroutines.launch

@Composable
fun TemplateBottomAppBar(
    drawerState: DrawerState = DrawerState(DrawerValue.Closed),
    currentRoute: String = Routes.PokemonList.route,
    navigationActions: NavigationActions? = null,
) {
    val coroutineScope = rememberCoroutineScope()

    NavigationBar(
        modifier = Modifier.clip(RoundedCornerShape(20.dp)),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.AutoMirrored.Outlined.ManageSearch,
                    contentDescription = "List",
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
            label = {
                Text(
                    stringResource(R.string.pokedex_title),
                    color = MaterialTheme.colorScheme.primary,
                )
            },
            selected = currentRoute == Routes.PokemonList.route,
            onClick = {
                navigationActions?.navigateToPokemonList?.invoke()
                coroutineScope.launch { drawerState.close() }
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    if (currentRoute == Routes.Team.route) {
                        Icons.Outlined.PeopleOutline
                    } else {
                        Icons.Default.PeopleOutline
                    },
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Team",
                )
            },
            label = {
                Text(
                    stringResource(R.string.team_title),
                    color = MaterialTheme.colorScheme.primary,
                )
            },
            selected = currentRoute == Routes.Team.route,
            onClick = {
                navigationActions?.navigateToTeamList?.invoke()
                coroutineScope.launch { drawerState.close() }
            }
        )
    }
}


@Composable
@Preview("Bottom App Bar")
@Preview("Bottom App Bar", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun TemplateBottomAppBarPreview() {
    TemplateTheme {
        TemplateBottomAppBar(
            drawerState = DrawerState(DrawerValue.Closed),
            Routes.PokemonList.route,
        )
    }
}