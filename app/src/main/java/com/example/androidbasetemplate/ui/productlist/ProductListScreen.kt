package com.example.androidbasetemplate.ui.productlist

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.entity.Product
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(productListViewModel: ProductListViewModel, drawerState: DrawerState) {
    Scaffold(
        topBar = {
            TopAppBar(drawerState)
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // TODO: show FullScreenLoading() while loading data
                ProductList(productListViewModel.productList)
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview("Top App Bar", uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun TopAppBar(drawerState: DrawerState = DrawerState(DrawerValue.Closed)) {
    val coroutineScope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(id = R.string.product_list_title),
                style = MaterialTheme.typography.titleLarge,
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
        actions = {
            IconButton(onClick = { /* TODO: */ }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search_title),
                )
            }
        },
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState()),
    )
}

@Composable
@Preview("Product List", uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun ProductList(
    productList: List<Product> = listOf(
        Product(
            "Product name 1",
            "This is an example of product description 1",
            imageUrl = "https://definicion.de/wp-content/uploads/2009/06/producto.png",
        ),
        Product(
            "Product name 2",
            "This is an example of product description 2",
            imageUrl = "https://definicion.de/wp-content/uploads/2009/06/producto.png",
        ),
        Product(
            "Product name 3",
            "This is an example of product description 3",
            imageUrl = "https://definicion.de/wp-content/uploads/2009/06/producto.png",
        ),
        Product(
            "Product name 4",
            "This is an example of product description 4",
            imageUrl = "https://definicion.de/wp-content/uploads/2009/06/producto.png",
        ),
        Product(
            "Product name 5",
            "This is an example of product description 5",
            imageUrl = "https://definicion.de/wp-content/uploads/2009/06/producto.png",
        ),
    ),
) {
    LazyColumn(
        modifier = Modifier.fillMaxHeight().fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(productList) { product ->
            Product(product.productId, product.denomination)
        }
    }
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