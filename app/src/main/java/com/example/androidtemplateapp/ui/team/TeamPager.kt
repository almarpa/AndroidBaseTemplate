package com.example.androidtemplateapp.ui.team

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.entity.Pokemon
import com.example.androidtemplateapp.ui.common.mocks.getPokemonListMock
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TeamPager(pagerState: PagerState, pokemonList: List<Pokemon>) {
    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        HorizontalPager(
            modifier = Modifier.fillMaxHeight(),
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 40.dp),
        ) { page ->
            pokemonList.getOrNull(page % (pokemonList.size))?.let { pokemon ->
                MemberItem(
                    pokemon = pokemon,
                    pagerState = pagerState,
                    page = page
                )
            }
        }
    }
}

@Composable
fun MemberItem(pokemon: Pokemon, pagerState: PagerState, page: Int) {
    val pageOffset =
        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

    Card(
        modifier = Modifier
            .padding(20.dp)
            .height(200.dp + 200.dp * (1 - pageOffset))
            .graphicsLayer {
                alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
            },
        shape = AbsoluteCutCornerShape(40.dp),
        colors = CardDefaults.cardColors(containerColor = pokemon.getDominantColor() ?: MaterialTheme.colorScheme.secondary)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MemberImage(modifier = Modifier.weight(.6f), pokemon = pokemon)
            MemberName(modifier = Modifier.weight(.2f), pokemon = pokemon)
        }
    }
}

@Composable
fun MemberImage(pokemon: Pokemon, modifier: Modifier) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(pokemon.url)
            .crossfade(true)
            .apply { if (LocalInspectionMode.current) placeholder(R.drawable.pokeball) }
            .build(),
        contentDescription = "Member Image",
        contentScale = ContentScale.FillBounds,
        modifier = modifier.aspectRatio(1f)
    )
}

@Composable
fun MemberName(pokemon: Pokemon, modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp, bottom = 12.dp)
                .align(Alignment.Center),
            textAlign = TextAlign.Center,
            text = pokemon.name.uppercase(),
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview("Team Pager", showBackground = true)
@Preview(
    "Team Pager Landscape",
    showBackground = true,
    device = "spec:width=400dp,height=900dp,dpi=420,orientation=landscape"
)
@Preview("Dark Team Pager", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Tablet Dark Team Pager", device = Devices.TABLET)
fun TeamPagerPreview() {
    TemplatePreviewTheme {
        TeamPager(pagerState = rememberPagerState { getPokemonListMock().size }, pokemonList = getPokemonListMock())
    }
}