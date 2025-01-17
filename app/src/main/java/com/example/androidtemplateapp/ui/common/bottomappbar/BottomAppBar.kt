package com.example.androidtemplateapp.ui.common.bottomappbar

import android.content.res.Configuration
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidtemplateapp.ui.common.mocks.getBottomAppBarItemsMock
import com.example.androidtemplateapp.ui.common.navigation.Routes
import com.example.androidtemplateapp.ui.theme.TemplateTheme

data class BottomAppBarItem(
    val icon: @Composable () -> Unit,
    val label: Int,
    val color: Color,
    val route: Routes,
)


@Composable
fun BottomAppBar(
    modifier: Modifier = Modifier,
    bottomAppBarItems: List<BottomAppBarItem>,
    currentRoute: Routes,
    onRouteSelected: (Routes) -> Unit = {},
) {
    NavigationBar(modifier = modifier.clip(RoundedCornerShape(20.dp))) {
        bottomAppBarItems.forEach { item ->
            NavigationBarItem(
                icon = item.icon,
                label = { item.label },
                selected = currentRoute == item.route,
                onClick = { onRouteSelected(item.route) }
            )
        }
    }
}


@Composable
@Preview("Bottom App Bar")
@Preview("Bottom App Bar", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun TemplateBottomAppBarPreview() {
    TemplateTheme {
        BottomAppBar(
            currentRoute = Routes.PokemonList,
            bottomAppBarItems = getBottomAppBarItemsMock(),
            onRouteSelected = {},
        )
    }
}