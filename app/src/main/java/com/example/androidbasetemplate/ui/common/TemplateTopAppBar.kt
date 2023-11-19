package com.example.androidbasetemplate.ui.common

import android.content.res.Configuration
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.ui.TemplateNavigationActions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview("Top App Bar", uiMode = Configuration.UI_MODE_NIGHT_NO)
fun TemplateTopAppBar(
    drawerState: DrawerState = DrawerState(DrawerValue.Closed),
    navigationActions: TemplateNavigationActions? = null,
    title: Int = R.string.home_title,
) {
    val coroutineScope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(id = title),
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
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState()),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
    )
}
