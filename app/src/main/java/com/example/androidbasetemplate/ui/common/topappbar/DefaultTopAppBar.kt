package com.example.androidbasetemplate.ui.common.topappbar

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.ui.common.NavigationActions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview("Default Top App Bar", uiMode = Configuration.UI_MODE_NIGHT_NO)
fun DefaultTopAppBar(
    drawerState: DrawerState = DrawerState(DrawerValue.Closed),
    navigationActions: NavigationActions? = null,
    title: Int = R.string.settings_title,
    onBackButton: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(id = title),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackButton.invoke() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.menu_drawer_btn),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState()),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
    )
}
