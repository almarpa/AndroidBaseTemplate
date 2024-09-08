package com.example.androidtemplateapp.ui.pokemonlist

import android.app.Activity
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.androidtemplateapp.entity.Pokemon
import com.example.androidtemplateapp.ui.common.bottomappbar.AnimatedBottomAppBar
import com.example.androidtemplateapp.ui.common.error.GenericRetryView
import com.example.androidtemplateapp.ui.common.loader.FullScreenLoader
import com.example.androidtemplateapp.ui.common.mocks.getPokemonListMock
import com.example.androidtemplateapp.ui.common.navigation.NavigationActions
import com.example.androidtemplateapp.ui.common.navigation.Routes
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme
import com.example.androidtemplateapp.ui.pokemonlist.list.PokemonList
import com.example.androidtemplateapp.ui.pokemonlist.search.PokemonSearchTopAppBar
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.PokemonListScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    drawerState: DrawerState,
    currentRoute: Routes,
    navigationActions: NavigationActions,
    searchUiState: SearchUiState,
    paginatedPokemonList: LazyPagingItems<Pokemon>,
    onReload: () -> Unit,
    onSearch: (text: String) -> Unit,
    onDismissSearch: () -> Unit,
) {
    val activity = (LocalContext.current as? Activity)
    var isSearchActive by rememberSaveable { mutableStateOf(false) }
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    BackHandler { activity?.finish() }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            PokemonSearchTopAppBar(
                animatedVisibilityScope = animatedVisibilityScope,
                drawerState = drawerState,
                scrollBehaviour = scrollBehaviour,
                uiState = searchUiState,
                isSearchActive = isSearchActive,
                onSearchActiveChange = { isActive -> isSearchActive = isActive },
                onDismissSearch = { onDismissSearch() },
                onSearch = { onSearch(it) },
                onSelected = { navigationActions.navigateToDetailNavGraph(it) }
            )
        },
        content = { paddingValues ->
            PokemonListContent(
                modifier = Modifier.padding(paddingValues = paddingValues),
                animatedVisibilityScope = animatedVisibilityScope,
                paginatedPokemonList = paginatedPokemonList,
                onReload = { onReload() },
                onNavigateToPokemonDetail = { navigationActions.navigateToDetailNavGraph(it) }
            )
        },
        bottomBar = {
            AnimatedBottomAppBar(
                modifier = Modifier, // TODO: fix with .renderInSharedTransitionScopeOverlay(zIndexInOverlay = 1f)
                isVisible = !isSearchActive,
                drawerState = drawerState,
                currentRoute = currentRoute,
                navigationActions = navigationActions
            )
        },
    )
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterialApi::class)
@Composable
fun SharedTransitionScope.PokemonListContent(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    paginatedPokemonList: LazyPagingItems<Pokemon>,
    onReload: () -> Unit,
    onNavigateToPokemonDetail: (Pokemon) -> Unit,
) {
    Box {
        val pullRefreshState = rememberPullRefreshState(
            refreshing = false,
            onRefresh = { onReload() }
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (paginatedPokemonList.loadState.refresh) {
                is LoadState.Loading -> {
                    FullScreenLoader()
                }

                is LoadState.Error -> {
                    GenericRetryView { onReload() }
                }

                is LoadState.NotLoading -> {
                    PokemonList(
                        animatedVisibilityScope = animatedVisibilityScope,
                        pokemonList = paginatedPokemonList,
                        onPokemonItemClick = { onNavigateToPokemonDetail(it) }
                    )
                }
            }
        }

        PullRefreshIndicator(
            refreshing = false,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Pokemon List Screen")
@Preview("Pokemon List Screen", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PokemonListScreenPreview() {
    TemplatePreviewTheme {
        PokemonListScreen(
            animatedVisibilityScope = it,
            drawerState = DrawerState(DrawerValue.Closed),
            currentRoute = Routes.PokemonList,
            navigationActions = NavigationActions(rememberNavController()),
            searchUiState = SearchUiState.Success(getPokemonListMock()),
            paginatedPokemonList = flowOf(PagingData.from(getPokemonListMock())).collectAsLazyPagingItems(),
            onReload = {},
            onDismissSearch = {},
            onSearch = {},
        )
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Pokemon List Screen")
@Preview("Pokemon List Screen", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PokemonListScreenWithSearchActivePreview() {
    TemplatePreviewTheme {
        PokemonListScreen(
            animatedVisibilityScope = it,
            drawerState = DrawerState(DrawerValue.Closed),
            currentRoute = Routes.PokemonList,
            navigationActions = NavigationActions(rememberNavController()),
            searchUiState = SearchUiState.Error,
            paginatedPokemonList = flowOf(PagingData.from(getPokemonListMock())).collectAsLazyPagingItems(),
            onReload = {},
            onDismissSearch = {},
            onSearch = {},
        )
    }
}