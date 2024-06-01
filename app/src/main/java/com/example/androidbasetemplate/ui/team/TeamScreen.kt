package com.example.androidbasetemplate.ui.team

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            TeamListContent(it, uiState) { teamViewModel.getTeamList() }
        },
        bottomBar = {
            TemplateBottomAppBar(
                drawerState = drawerState,
                currentRoute = currentRoute,
                navigationActions = navigationActions
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
            Box(
                modifier = Modifier.padding(paddingValues = paddingValues),
                contentAlignment = Alignment.BottomEnd,
            ) {
                TeamList(pokemonList = uiState.teamList)
                AddPokemonFloatingButton { /* TODO: navigationActions.navigateToCreatePokemon( )*/ }
            }
        }
    }
}

@Composable
fun AddPokemonFloatingButton(onFloatingButtonPressed: () -> Unit) {
    ExtendedFloatingActionButton(
        modifier = Modifier.padding(16.dp),
        onClick = { onFloatingButtonPressed() },
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            imageVector = Icons.Default.Add, contentDescription = null,
        )
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

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Add Pokemon Floating Button")
fun AddPokemonFloatingButton() {
    TemplatePreviewTheme {
        AddPokemonFloatingButton {}
    }
}