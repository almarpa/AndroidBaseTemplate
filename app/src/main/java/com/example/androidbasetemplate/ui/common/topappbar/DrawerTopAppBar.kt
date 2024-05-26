package com.example.androidbasetemplate.ui.common.topappbar

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.ui.common.preview.TemplatePreviewTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerTopAppBar(
    drawerState: DrawerState = DrawerState(DrawerValue.Closed),
    title: Int = R.string.pokedex_title,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    allowSearch: Boolean = false,
    onDismissSearch: () -> Unit = {},
    onSearch: (String) -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    var isSearchBarVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.wrapContentHeight(),
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(stringResource(id = title), style = MaterialTheme.typography.titleLarge)
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
            actions = {
                if (allowSearch) {
                    SearchIcon(isSearchBarVisible) {
                        isSearchBarVisible = it
                        if (!it) onDismissSearch()
                    }
                }
            },
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
        )
        PokemonSearchBar(isSearchBarVisible) { onSearch(it) }
    }
}

@Composable
fun SearchIcon(isSearchBarVisible: Boolean, onValueChange: (Boolean) -> Unit) {
    IconButton(onClick = { onValueChange(!isSearchBarVisible) }) {
        Icon(
            imageVector = if (isSearchBarVisible) {
                Icons.Default.SearchOff
            } else {
                Icons.Default.Search
            },
            contentDescription = stringResource(R.string.menu_drawer_btn),
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonSearchBar(isSearchBarVisible: Boolean, onSearch: (String) -> Unit) {
    if (isSearchBarVisible) {
        var text by remember { mutableStateOf("") }
        var isSearchInputActive by remember { mutableStateOf(false) }
        val focusRequester = remember { FocusRequester() }
        val keyboard = LocalSoftwareKeyboardController.current

        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 0.dp, start = 16.dp, bottom = 8.dp, end = 16.dp)
                .focusRequester(focusRequester),
            query = text,
            onQueryChange = { text = it },
            onSearch = {
                onSearch(text)
                isSearchInputActive = false
            },
            active = isSearchInputActive,
            onActiveChange = { isSearchInputActive = it },
            placeholder = { Text(text = stringResource(id = R.string.search_title)) },
            trailingIcon = {
                if (text.isNotEmpty() && !isSearchInputActive) {
                    IconButton(onClick = { text = "" }) {
                        Icon(imageVector = Icons.Default.Cancel, contentDescription = null)
                    }
                } else {
                    IconButton(onClick = {
                        onSearch(text)
                        isSearchInputActive = false
                    }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }
                }
            }
        ) {}

        LaunchedEffect(focusRequester) {
            focusRequester.requestFocus()
            keyboard?.show()
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Preview("Drawer Top App Bar", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DrawerTopAppBarPreview() {
    TemplatePreviewTheme {
        DrawerTopAppBar(
            drawerState = DrawerState(DrawerValue.Closed),
        ) {}
    }
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Preview("Drawer Search Top App Bar")
@Composable
fun DrawerSearchTopAppBarPreview() {
    TemplatePreviewTheme {
        DrawerTopAppBar(
            drawerState = DrawerState(DrawerValue.Closed),
            allowSearch = true
        ) {}
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Pokemon Search Bar")
@Composable
fun PokemonSearchBarPreview() {
    TemplatePreviewTheme {
        PokemonSearchBar(
            isSearchBarVisible = true,
        ) {}
    }
}


