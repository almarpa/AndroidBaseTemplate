package com.example.androidbasetemplate.ui.team

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.ui.common.bottomappbar.TemplateBottomAppBar
import com.example.androidbasetemplate.ui.common.error.GenericRetryView
import com.example.androidbasetemplate.ui.common.loader.FullScreenLoader
import com.example.androidbasetemplate.ui.common.mocks.getPokemonListMock
import com.example.androidbasetemplate.ui.common.mocks.getTeamViewModelMock
import com.example.androidbasetemplate.ui.common.navigation.NavigationActions
import com.example.androidbasetemplate.ui.common.navigation.Routes
import com.example.androidbasetemplate.ui.common.preview.TemplatePreviewTheme
import com.example.androidbasetemplate.ui.common.topappbar.DrawerTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamScreen(
    teamViewModel: TeamViewModel = hiltViewModel(),
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: NavigationActions,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DrawerTopAppBar(
                drawerState = drawerState,
                title = R.string.team_title,
                scrollBehavior = scrollBehavior,
            )
        },
        content = {
            val uiState by teamViewModel.uiState.collectAsStateWithLifecycle()
            TeamListContent(it, uiState) {
                teamViewModel.getTeamList()
            }
        },
        bottomBar = {
            TemplateBottomAppBar(
                drawerState = drawerState,
                currentRoute = currentRoute,
                navigationActions
            )
        }
    )
}

@Composable
fun TeamListContent(
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
            TeamList(paddingValues, pokemonList = uiState.teamList)
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
@Preview("Pokemon List Content")
fun PokemonListContentPreview() {
    TemplatePreviewTheme {
        TeamListContent(
            uiState = TeamUiState.Success(getPokemonListMock()),
            paddingValues = PaddingValues(),
        ) {}
    }
}