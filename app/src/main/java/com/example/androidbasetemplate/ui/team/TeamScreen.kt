package com.example.androidbasetemplate.ui.team

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import com.example.androidbasetemplate.ui.team.FabContainerState.Fab
import com.example.androidbasetemplate.ui.team.FabContainerState.Fullscreen

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
    var containerState by remember { mutableStateOf<FabContainerState>(Fab) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AnimatedVisibility(
                visible = containerState == Fab,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = LinearOutSlowInEasing
                    )
                ),
                exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = LinearOutSlowInEasing
                    )
                )
            ) {
                DrawerTopAppBar(
                    drawerState = drawerState,
                    title = R.string.team_title,
                    scrollBehavior = scrollBehavior,
                )
            }
        },
        content = {
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.BottomEnd
            ) {
                val uiState by teamViewModel.uiState.collectAsStateWithLifecycle()
                TeamListContent(it, uiState) { teamViewModel.getTeamList() }
                FabContainer(
                    Modifier.padding(bottom = it.calculateBottomPadding()),
                    containerState,
                ) {
                    containerState = it
                }
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = containerState == Fab,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = LinearOutSlowInEasing
                    )
                ),
                exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = LinearOutSlowInEasing
                    )
                )
            ) {
                TemplateBottomAppBar(
                    drawerState = drawerState,
                    currentRoute = currentRoute,
                    navigationActions = navigationActions
                )
            }
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
                modifier = Modifier
                    .padding(paddingValues = paddingValues)
            ) {
                TeamList(pokemonList = uiState.teamList)
            }
        }
    }
}

@Composable
private fun FabContainer(
    modifier: Modifier = Modifier,
    containerState: FabContainerState,
    onContainerStateChanged: (FabContainerState) -> Unit,
) {
    val transition = updateTransition(targetState = containerState, label = "fabTransition")
    val backgroundColor by transition.animateColor(label = "fabContainerColorAnim") { state ->
        when (state) {
            Fab -> Color.Transparent
            Fullscreen -> MaterialTheme.colorScheme.primary
        }
    }
    val cornerRadius by transition.animateDp(label = "fabContainerDpAnim") { state ->
        when (state) {
            Fab -> 22.dp
            Fullscreen -> 0.dp
        }
    }
    val elevation by transition.animateDp(label = "") { state ->
        when (state) {
            Fab -> 16.dp
            Fullscreen -> 0.dp
        }
    }

    transition.AnimatedContent(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(cornerRadius)
            )
            .drawBehind { drawRect(backgroundColor) }
    ) { state ->
        when (state) {
            Fab -> AddPokemonFloatingButton {
                onContainerStateChanged(Fullscreen)
            }

            Fullscreen -> AddPokemonContent {
                onContainerStateChanged(Fab)
            }
        }
    }
}

@Composable
fun AddPokemonFloatingButton(onFabButtonPressed: () -> Unit) {
    ExtendedFloatingActionButton(
        modifier = Modifier.padding(16.dp),
        onClick = { onFabButtonPressed() },
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            imageVector = Icons.Default.Add, contentDescription = null,
        )
    }
}

@Composable
fun AddPokemonContent(onCancel: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize(),
    ) {
        IconButton(onClick = { onCancel() }) {
            Icon(imageVector = Icons.Default.Cancel, contentDescription = null)
        }
        TextField(
            value = "Test",
            onValueChange = {},
            label = { Text(text = "Test") },
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
@Preview("Pokemon List Content", showBackground = true)
@Preview(
    "Dark Add Pokemon Floating Button",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
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
@Preview("Add Pokemon Floating Button", showBackground = true)
@Preview(
    "Dark Add Pokemon Floating Button",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun AddPokemonFloatingButtonPreview() {
    TemplatePreviewTheme {
        AddPokemonFloatingButton {}
    }
}