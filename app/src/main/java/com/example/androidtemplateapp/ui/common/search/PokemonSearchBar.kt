package com.example.androidtemplateapp.ui.common.search

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonSearchBar(
    textSearched: String = "",
    onSearch: (String) -> Unit,
    onCancel: () -> Unit,
) {
    var searchText by remember { mutableStateOf(textSearched) }
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, start = 16.dp, bottom = 10.dp, end = 16.dp),
        query = searchText,
        onQueryChange = {
            searchText = it
            if (searchText.length > 1) onSearch(searchText)
        },
        onSearch = { onSearch(searchText) },
        active = false,
        onActiveChange = {},
        placeholder = { Text(text = stringResource(id = R.string.search_title)) },
        shadowElevation = 12.dp,
        leadingIcon = {
            IconButton(onClick = { onCancel() }) {
                Icon(imageVector = Icons.Default.Cancel, contentDescription = null)
            }
        },
        trailingIcon = {
            IconButton(onClick = { onSearch(searchText) }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }
        }
    ) {}
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Pokemon Search Bar")
@Preview("Dark Pokemon Search Bar", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PokemonSearchBarPreview() {
    TemplatePreviewTheme {
        PokemonSearchBar(
            textSearched = "Char",
            onCancel = {},
            onSearch = {}
        )
    }
}