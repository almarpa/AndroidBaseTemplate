package com.example.androidbasetemplate.ui.team

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.entity.Pokemon
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
    var fabContainerState by remember { mutableStateOf<FabContainerState>(Fab) }
    Scaffold(
        topBar = {
            AnimatedDrawerTopAppBar(
                isVisible = fabContainerState == Fab,
                drawerState = drawerState,
                title = R.string.team_title,
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
                onSavePokemon = { /* TODO: teamViewModel.savePokemon(it) */ }
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
    onSavePokemon: (Pokemon) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize(),
) {
        val initialPaddingValues by remember { mutableStateOf(paddingValues) }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(initialPaddingValues)
                .padding(top = 50.dp)
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