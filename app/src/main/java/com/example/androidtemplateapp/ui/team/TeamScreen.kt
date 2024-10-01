package com.example.androidtemplateapp.ui.team

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamScreen(
    drawerState: DrawerState,
    currentRoute: Routes,
    navigationActions: NavigationActions,
    uiState: TeamUiState,
    onRetry: () -> Unit,
    onSave: (pokemon: Pokemon) -> Unit,
) {
    var isFabContainerFullScreen by rememberSaveable { mutableStateOf(false) }

    if (isFabContainerFullScreen) {
        BackHandler { isFabContainerFullScreen = false }
    }

    Scaffold(
        topBar = {
            AnimatedTopAppBar(
                isVisible = !isFabContainerFullScreen,
                drawerState = drawerState,
                title = R.string.team_title,
            )
        },
        content = { paddingValues ->
            TeamContent(
                paddingValues = paddingValues,
                uiState = uiState,
                isFabContainerFullscreen = isFabContainerFullScreen,
                onRetry = { onRetry() },
                onFabContainerFullscreenChanged = { isFabContainerFullScreen = it },
                onSavePokemon = { pokemon ->
                    isFabContainerFullScreen = false
                    onSave(pokemon)
                }
            )
        },
        bottomBar = {
            AnimatedBottomAppBar(
                isVisible = !isFabContainerFullScreen,
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
    isFabContainerFullscreen: Boolean,
    onRetry: () -> Unit,
    onFabContainerFullscreenChanged: (Boolean) -> Unit,
    onSavePokemon: (Pokemon) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(if (!isFabContainerFullscreen) paddingValues else PaddingValues(0.dp))
    ) {

        when (uiState) {
            is TeamUiState.Loading -> {
                FullScreenLoader()
            }

            is TeamUiState.Error -> {
                GenericRetryView { onRetry() }
            }

            is TeamUiState.Success -> {
                val pagerState = rememberPagerState(pageCount = { uiState.teamList.size })
                val coroutineScope = rememberCoroutineScope()

                TeamPager(pagerState = pagerState, pokemonList = uiState.teamList)
                AnimatedFabContainer(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.BottomEnd),
                    fabContainerState = isFabContainerFullscreen,
                    onFabContainerStateChanged = { onFabContainerFullscreenChanged(it) },
                    onSave = {
                        onSavePokemon(it)
                        coroutineScope.launch {
                            delay(1600)
                            pagerState.animateScrollToPage(uiState.teamList.size)
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview("Team Screen")
@Preview(
    "Team Screen Fab Landscape",
    showBackground = true,
    device = "spec:width=400dp,height=900dp,dpi=420,orientation=landscape"
)
@Preview(name = "Team Fab Preview Tablet", device = Devices.TABLET)
fun TeamScreenFabPreview() {
    TemplatePreviewTheme {
        TeamScreen(
            drawerState = DrawerState(DrawerValue.Closed),
            currentRoute = Routes.Team,
            navigationActions = NavigationActions(rememberNavController()),
            uiState = TeamUiState.Success(getPokemonListMock()),
            onRetry = {},
            onSave = {}
        )
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Team Empty Content Fab", showBackground = true)
@Preview(
    name = "Team Empty Content Fab",
    showBackground = true,
    device = Devices.TABLET
)
fun TeamEmptyContentFabPreview() {
    TemplatePreviewTheme {
        TeamScreen(
            drawerState = DrawerState(DrawerValue.Closed),
            currentRoute = Routes.Team,
            navigationActions = NavigationActions(rememberNavController()),
            uiState = TeamUiState.Success(listOf()),
            onRetry = {},
            onSave = {}
        )
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Team Content Fullscreen", showBackground = true)
@Preview(
    name = "Tablet Team Content Fullscreen",
    showBackground = true,
    device = Devices.TABLET
)
@Preview(
    name = "Tablet Portrait Team Content Fullscreen",
    showBackground = true,
    device = "spec:width=1280dp,height=900dp,dpi=420,orientation=portrait"
)
fun TeamContentFullscreenPreview() {
    TemplatePreviewTheme {
        TeamContent(
            paddingValues = PaddingValues(0.dp),
            uiState = TeamUiState.Success(getPokemonListMock()),
            isFabContainerFullscreen = true,
            onRetry = {},
            onFabContainerFullscreenChanged = {},
            onSavePokemon = {}
        )
    }
}
