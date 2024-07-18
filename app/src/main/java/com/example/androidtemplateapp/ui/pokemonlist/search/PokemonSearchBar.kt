package com.example.androidtemplateapp.ui.pokemonlist.search

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.entity.Pokemon
import com.example.androidtemplateapp.ui.common.mocks.getPokemonListMock
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme
import com.example.androidtemplateapp.ui.pokemonlist.SearchUiState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PokemonSearchBar(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    uiState: SearchUiState,
    onSearch: (String) -> Unit,
    onSelected: (Pokemon) -> Unit,
    onCancel: () -> Unit,
) {
    var searchText by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    var isSearchInputLoaded by remember { mutableStateOf(false) }

    SearchBar(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onGloballyPositioned {
                if (!isSearchInputLoaded) {
                    focusRequester.requestFocus()
                    isSearchInputLoaded = true
                }
            },
        query = searchText,
        onQueryChange = {
            searchText = it
            onSearch(searchText)
        },
        onSearch = { onSearch(searchText) },
        active = true,
        onActiveChange = {},
        placeholder = { Text(text = stringResource(id = R.string.search_title)) },
        shadowElevation = 12.dp,
        trailingIcon = {
            IconButton(onClick = { onCancel() }) {
                Icon(
                    imageVector = Icons.Default.Cancel,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null
                )
            }
        }
    ) {
        if (uiState !is SearchUiState.Idle) {
            PokemonSearchBarContent(
                animatedVisibilityScope = animatedVisibilityScope,
                uiState = uiState,
                onSelected = { onSelected(it) }
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Search Bar")
@Preview(
    "Dark Search Bar",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ActivatedSearchTopBarPreview() {
    TemplatePreviewTheme {
        PokemonSearchBar(
            animatedVisibilityScope = it,
            uiState = SearchUiState.Success(getPokemonListMock()),
            onCancel = {},
            onSearch = {},
            onSelected = {}
        )
    }
}