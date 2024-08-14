package com.example.androidtemplateapp.ui.common.tabrow

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidtemplateapp.ui.common.mocks.getPokemonStatListMock
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme
import com.example.androidtemplateapp.ui.common.spacer.CustomSpacer
import com.example.androidtemplateapp.ui.pokemondetails.PokemonStats

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeableTabRow(
    modifier: Modifier,
    tabs: List<String>,
    contentScreens: List<@Composable () -> Unit>,
    containerColor: Color,
    contentColor: Color,
    indicatorColor: Color,
) {
    Column(modifier = modifier.fillMaxWidth()) {

        var selectedTabIndex by remember { mutableIntStateOf(0) }
        val pagerState = rememberPagerState { tabs.size }

        LaunchedEffect(key1 = selectedTabIndex) {
            pagerState.animateScrollToPage(selectedTabIndex)
        }
        LaunchedEffect(key1 = pagerState.currentPage) {
            selectedTabIndex = pagerState.currentPage
        }

        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = containerColor,
            contentColor = contentColor,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = indicatorColor
                )
            }
        ) {
            tabs.forEachIndexed { index, tabTitle ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                ) {
                    Text(modifier = Modifier.padding(16.dp), text = tabTitle)
                }
            }
        }
        CustomSpacer(16)

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            contentScreens.getOrNull(selectedTabIndex)?.invoke()
        }

        CustomSpacer(16)
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview(name = "Swipeable Tab Row", showBackground = true)
@Preview(
    name = "Dark Swipeable Tab Row",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun SwipeableTabRowPreview() {
    TemplatePreviewTheme {
        SwipeableTabRow(
            modifier = Modifier,
            tabs = listOf("Stats", "Moves"),
            contentScreens = listOf(
                { PokemonStats(stats = getPokemonStatListMock()) },
                { PokemonStats(stats = getPokemonStatListMock()) },
            ),
            containerColor = Color.White,
            contentColor = Color.Black,
            indicatorColor = Color.Black
        )
    }
}