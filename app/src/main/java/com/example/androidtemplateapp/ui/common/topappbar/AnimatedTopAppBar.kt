package com.example.androidtemplateapp.ui.common.topappbar

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedTopAppBar(
    isVisible: Boolean,
    drawerState: DrawerState,
    title: Int,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearOutSlowInEasing
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearOutSlowInEasing
            )
        )
    ) {
        val coroutineScope = rememberCoroutineScope()

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
                actions = {},
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    scrolledContainerColor = MaterialTheme.colorScheme.surface,
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Preview("Dark Animated Top App Bar", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DarkAnimatedTopAppBarPreview() {
    TemplatePreviewTheme {
        AnimatedTopAppBar(
            isVisible = true,
            title = R.string.team_title,
            drawerState = DrawerState(DrawerValue.Closed),
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Preview("Animated Top App Bar")
@Composable
fun AnimatedTopAppBarPreview() {
    TemplatePreviewTheme {
        AnimatedTopAppBar(
            isVisible = true,
            title = R.string.team_title,
            drawerState = DrawerState(DrawerValue.Closed),
        )
    }
}