package com.example.androidtemplateapp.ui.pokemonlist.search

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.entity.Pokemon
import com.example.androidtemplateapp.ui.common.mocks.getPokemonListMock
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme
import com.example.androidtemplateapp.ui.pokemonlist.SearchUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PokemonSearchTopAppBar(
    animatedVisibilityScope: AnimatedVisibilityScope,
    drawerState: DrawerState = DrawerState(DrawerValue.Closed),
    scrollBehaviour: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    uiState: SearchUiState,
    isSearchActive: Boolean = false,
    onSearchActiveChange: (Boolean) -> Unit = {},
    onDismissSearch: () -> Unit = {},
    onSearch: (String) -> Unit = {},
    onSelected: (Pokemon) -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()

    Box {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    stringResource(id = R.string.pokedex_title),
                    style = MaterialTheme.typography.titleLarge
                )
            },
            navigationIcon = {
                IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_menu),
                        contentDescription = stringResource(R.string.menu_drawer_btn),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            },
            actions = { SearchIcon { onSearchActiveChange(true) } },
            scrollBehavior = scrollBehaviour,
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                scrolledContainerColor = MaterialTheme.colorScheme.surface,
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
        )
        if (isSearchActive) {
            PokemonSearchBar(
                animatedVisibilityScope = animatedVisibilityScope,
                uiState = uiState,
                onSearch = { onSearch(it) },
                onCancel = {
                    onDismissSearch()
                    onSearchActiveChange(false)
                },
                onSelected = { onSelected(it) }
            )
        }
    }
}

@Composable
fun SearchIcon(onIconClick: () -> Unit) {
    IconButton(onClick = { onIconClick() }) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(R.string.menu_drawer_btn),
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Preview("Inactive Search Top App Bar")
@Preview("Inactive Dark Search Top App Bar", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun InactiveSearchTopAppBarPreview() {
    TemplatePreviewTheme {
        PokemonSearchTopAppBar(
            animatedVisibilityScope = it,
            uiState = SearchUiState.Success(getPokemonListMock()),
            drawerState = DrawerState(DrawerValue.Closed),
        ) {}
    }
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Preview("Active Search Top App Bar")
@Preview("Active Dark Search Top App Bar", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ActiveSearchTopAppBarPreview() {
    TemplatePreviewTheme {
        PokemonSearchTopAppBar(
            animatedVisibilityScope = it,
            uiState = SearchUiState.Success(getPokemonListMock()),
            drawerState = DrawerState(DrawerValue.Closed),
            isSearchActive = true
        ) {}
    }
}


