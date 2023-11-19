package com.example.androidbasetemplate.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
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

@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateDrawer(
    currentRoute: String = TemplateDestinations.HOME_ROUTE,
    navigateToSettings: () -> Unit = {},
    navigateToAbout: () -> Unit = {},
    closeDrawer: () -> Unit = {},
) {
    ModalDrawerSheet {
        TemplateAppLogo(
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp),
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.settings_title)) },
            icon = { Icon(Icons.Filled.Settings, null) },
            selected = currentRoute == TemplateDestinations.SETTINGS_ROUTE,
            onClick = {
                navigateToSettings()
                closeDrawer()
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.about_title)) },
            icon = { Icon(Icons.Filled.Person, null) },
            selected = currentRoute == TemplateDestinations.ABOUT_ROUTE,
            onClick = {
                navigateToAbout()
                closeDrawer()
            },
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
