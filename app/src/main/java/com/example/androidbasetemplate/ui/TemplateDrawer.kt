package com.example.androidbasetemplate.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.ui.theme.TemplateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToProductList: () -> Unit,
    closeDrawer: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    ModalDrawerSheet(modifier) {
        TemplateAppLogo(
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp),
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.home_title)) },
            icon = { Icon(Icons.Filled.Home, null) },
            selected = currentRoute == TemplateDestinations.HOME_ROUTE,
            onClick = { navigateToHome(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.product_list_title)) },
            icon = { Icon(Icons.Filled.List, null) },
            selected = currentRoute == TemplateDestinations.PRODUCT_LIST,
            onClick = { navigateToProductList(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        )
    }
}

@Composable
private fun TemplateAppLogo(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 24.sp,
        )
    }
}

@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppDrawer() {
    TemplateTheme {
        AppDrawer(
            currentRoute = TemplateDestinations.HOME_ROUTE,
            navigateToHome = {},
            navigateToProductList = {},
        )
    }
}
