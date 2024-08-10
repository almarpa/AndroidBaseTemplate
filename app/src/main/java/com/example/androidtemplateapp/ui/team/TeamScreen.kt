package com.example.androidtemplateapp.ui.team

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.entity.Pokemon
import com.example.androidtemplateapp.ui.common.bottomappbar.AnimatedBottomAppBar
import com.example.androidtemplateapp.ui.common.error.GenericRetryView
import com.example.androidtemplateapp.ui.common.loader.FullScreenLoader
import com.example.androidtemplateapp.ui.common.mocks.getPokemonListMock
import com.example.androidtemplateapp.ui.common.navigation.NavigationActions
import com.example.androidtemplateapp.ui.common.navigation.Routes
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme
import com.example.androidtemplateapp.ui.common.topappbar.AnimatedTopAppBar

sealed interface FabContainerState {
    data object Fab : FabContainerState
    data object Fullscreen : FabContainerState
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamScreen(
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: NavigationActions,
    uiState: TeamUiState,
    onRetry: () -> Unit,
    onSave: (pokemon: Pokemon) -> Unit,
) {
    var fabContainerState by remember { mutableStateOf<FabContainerState>(FabContainerState.Fab) }
    Scaffold(
        topBar = {
            AnimatedTopAppBar(
                isVisible = fabContainerState == FabContainerState.Fab,
                drawerState = drawerState,
                title = R.string.team_title,
            )
        },
        content = { paddingValues ->
            TeamContent(
                paddingValues = paddingValues,
                uiState = uiState,
                fabContainerState = fabContainerState,
                onRetry = { onRetry() },
                onFabContainerStateChanged = { fabContainerState = it },
                onSavePokemon = { pokemon ->
                    fabContainerState = FabContainerState.Fab
                    onSave(pokemon)
                }
            )
        },
        bottomBar = {
            AnimatedBottomAppBar(
                isVisible = fabContainerState == FabContainerState.Fab,
                drawerState = drawerState,
                currentRoute = currentRoute,
                navigationActions = navigationActions,
            )
        }
    )
}

@Composable
private fun TeamContent(
    paddingValues: PaddingValues,
    uiState: TeamUiState,
    fabContainerState: FabContainerState,
    onRetry: () -> Unit,
    onFabContainerStateChanged: (FabContainerState) -> Unit,
    onSavePokemon: (Pokemon) -> Unit,
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(paddingValues)
        ) {
            when (uiState) {
                is TeamUiState.Loading -> {
                    FullScreenLoader()
                }

                is TeamUiState.Error -> {
                    GenericRetryView { onRetry() }
                }

                is TeamUiState.Success -> {
                    TeamPager(pokemonList = uiState.teamList)
                }
            }
        }
        if (uiState !is TeamUiState.Error) {
            AnimatedFabContainer(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.End)
                    .padding(bottom = paddingValues.calculateBottomPadding()),
                fabContainerState = fabContainerState,
                onFabContainerStateChanged = { onFabContainerStateChanged(it) },
                onSave = { onSavePokemon(it) }
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview("Team Screen")
@Preview(name = "Tablet Team Screen", device = Devices.TABLET)
fun TeamScreenPreview() {
    TemplatePreviewTheme {
        TeamScreen(
            drawerState = DrawerState(DrawerValue.Closed),
            currentRoute = Routes.Team.route,
            navigationActions = NavigationActions(rememberNavController()),
            uiState = TeamUiState.Success(getPokemonListMock()),
            onRetry = {},
            onSave = {}
        )
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Team Content Fullscreen Preview", showBackground = true)
@Preview(name = "Tablet Team Content Fullscreen Preview", device = Devices.TABLET)
fun TeamContentFullscreenPreview() {
    TemplatePreviewTheme {
        TeamContent(
            paddingValues = PaddingValues(0.dp),
            uiState = TeamUiState.Success(getPokemonListMock()),
            fabContainerState = FabContainerState.Fullscreen,
            onRetry = {},
            onFabContainerStateChanged = {},
            onSavePokemon = {}
        )
    }
}
