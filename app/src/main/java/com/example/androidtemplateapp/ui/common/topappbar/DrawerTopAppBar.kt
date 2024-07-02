package com.example.androidtemplateapp.ui.common.topappbar

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme
import com.example.androidtemplateapp.ui.common.search.CustomSearchBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerTopAppBar(
    drawerState: DrawerState = DrawerState(DrawerValue.Closed),
    title: Int = R.string.pokedex_title,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    allowSearch: Boolean = false,
    textSearched: String = "",
    onDismissSearch: () -> Unit = {},
    onSearch: (String) -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    var isSearchBarVisible by remember { mutableStateOf(textSearched.isNotEmpty()) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
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
                if (allowSearch && !isSearchBarVisible) {
                    SearchIcon { isSearchBarVisible = true }
                }
            },
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                scrolledContainerColor = MaterialTheme.colorScheme.surface,
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
        )
        if (isSearchBarVisible) {
            CustomSearchBar(
                textSearched = textSearched,
                onSearch = {
                    onSearch(it)
                    keyboardController?.hide()
                },
                onCancel = {
                    onDismissSearch()
                    isSearchBarVisible = false
                },
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
@Preview("Dark Drawer Search Top App Bar", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
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
            allowSearch = true,
        ) {}
    }
}


