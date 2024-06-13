package com.example.androidbasetemplate.ui.team

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.ui.common.bottomappbar.AnimatedBottomAppBar
import com.example.androidbasetemplate.ui.common.error.GenericRetryView
import com.example.androidbasetemplate.ui.common.loader.FullScreenLoader
import com.example.androidbasetemplate.ui.common.mocks.getPokemonListMock
import com.example.androidbasetemplate.ui.common.mocks.getTeamViewModelMock
import com.example.androidbasetemplate.ui.common.navigation.NavigationActions
import com.example.androidbasetemplate.ui.common.navigation.Routes
import com.example.androidbasetemplate.ui.common.preview.TemplatePreviewTheme
import com.example.androidbasetemplate.ui.common.topappbar.AnimatedDrawerTopAppBar
import com.example.androidbasetemplate.ui.team.FabContainerState.Fab

sealed interface FabContainerState {
    data object Fab : FabContainerState
    data object Fullscreen : FabContainerState
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamScreen(
    teamViewModel: TeamViewModel = hiltViewModel(),
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: NavigationActions,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var fabContainerState by remember { mutableStateOf<FabContainerState>(Fab) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AnimatedDrawerTopAppBar(
                isVisible = fabContainerState == Fab,
                drawerState = drawerState,
                title = R.string.team_title,
                scrollBehavior = scrollBehavior
            )
        },
        content = { paddingValues ->
            val uiState by teamViewModel.uiState.collectAsStateWithLifecycle()
            TeamContent(
                paddingValues = paddingValues,
                uiState = uiState,
                fabContainerState = fabContainerState,
                onRetry = { teamViewModel.getTeamList() },
                onFabContainerStateChanged = { fabContainerState = it },
                onSavePokemon = { /* TODO: teamViewModel.savePokemon() */ }
            )
        },
        bottomBar = {
            AnimatedBottomAppBar(
                isVisible = fabContainerState == Fab,
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
    onSavePokemon: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxHeight(),
        contentAlignment = Alignment.BottomEnd
    ) {
        TeamList(paddingValues, uiState) { onRetry() }
        AnimatedFabContainer(
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
            fabContainerState = fabContainerState,
            onFabContainerStateChanged = { onFabContainerStateChanged(it) },
            onSave = { onSavePokemon() }
        )
    }
}

@Composable
fun TeamList(
    paddingValues: PaddingValues,
    uiState: TeamUiState,
    onRetrySelected: () -> Unit,
) {
    when (uiState) {
        is TeamUiState.Loading -> {
            FullScreenLoader()
        }

        is TeamUiState.Error -> {
            GenericRetryView { onRetrySelected() }
        }

        is TeamUiState.Success -> {
            val paddingTop by remember { derivedStateOf { paddingValues.calculateTopPadding() } }
            Box(
                modifier = Modifier.padding(
                    top = paddingTop,
                    bottom = paddingValues.calculateBottomPadding(),
                )
            ) {
                TeamList(pokemonList = uiState.teamList)
            }
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview("Team Screen")
fun TeamScreenPreview() {
    TemplatePreviewTheme {
        TeamScreen(
            teamViewModel = getTeamViewModelMock(),
            drawerState = DrawerState(DrawerValue.Closed),
            currentRoute = Routes.Team.route,
            navigationActions = NavigationActions(rememberNavController())
        )
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Team Content List Preview", showBackground = true)
fun TeamContentPreview() {
    TemplatePreviewTheme {
        TeamContent(
            paddingValues = PaddingValues(0.dp),
            uiState = TeamUiState.Success(getPokemonListMock()),
            fabContainerState = Fab,
            onRetry = {},
            onFabContainerStateChanged = {},
            onSavePokemon = {}
        )
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Team Content Retry Preview", showBackground = true)
fun TeamContentErrorPreview() {
    TemplatePreviewTheme {
        TeamContent(
            paddingValues = PaddingValues(0.dp),
            uiState = TeamUiState.Error,
            fabContainerState = Fab,
            onRetry = {},
            onFabContainerStateChanged = {},
            onSavePokemon = {}
        )
    }
}