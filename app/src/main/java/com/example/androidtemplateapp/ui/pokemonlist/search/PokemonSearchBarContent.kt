package com.example.androidtemplateapp.ui.pokemonlist.search

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidtemplateapp.entity.Pokemon
import com.example.androidtemplateapp.ui.common.error.GenericRetryView
import com.example.androidtemplateapp.ui.common.loader.FullScreenLoader
import com.example.androidtemplateapp.ui.common.mocks.getPokemonListMock
import com.example.androidtemplateapp.ui.common.notfound.NotFoundView
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme
import com.example.androidtemplateapp.ui.pokemonlist.SearchUiState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PokemonSearchBarContent(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    uiState: SearchUiState,
    onSelected: (Pokemon) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (uiState) {
            is SearchUiState.Loading -> FullScreenLoader()
            is SearchUiState.NotFound -> NotFoundView()
            is SearchUiState.Error -> GenericRetryView()
            is SearchUiState.Success -> {
                PokemonSearchList(
                    animatedVisibilityScope = animatedVisibilityScope,
                    pokemonList = uiState.pokemonList,
                    onPokemonItemClick = { onSelected(it) },
                )
            }

            is SearchUiState.Idle -> {
                /* Do nothing */
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Pokemon Search Bar Content")
@Preview(
    "Dark Pokemon Search Bar Content",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PokemonSearchBarContentPreview() {
    TemplatePreviewTheme {
        PokemonSearchBarContent(
            animatedVisibilityScope = it,
            uiState = SearchUiState.Success(getPokemonListMock()),
            onSelected = {}
        )
    }
}