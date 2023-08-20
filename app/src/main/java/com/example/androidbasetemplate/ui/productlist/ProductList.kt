package com.example.androidbasetemplate.ui.productlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.androidbasetemplate.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductList(productListViewModel: ProductListViewModel, drawerState: DrawerState) {
    ProductListTopAppBar(drawerState = drawerState)
    FullScreenLoading()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductListTopAppBar(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
) {
    val coroutineScope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        title = {},
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
            IconButton(onClick = { /* TODO: */ }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search_title),
                )
            }
        },
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState()),
        modifier = modifier,
    )
}

@Composable
private fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
    ) {
        CircularProgressIndicator()
    }
}
