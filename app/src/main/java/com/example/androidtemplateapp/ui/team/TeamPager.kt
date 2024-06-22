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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.androidtemplateapp.entity.Pokemon
import com.example.androidtemplateapp.ui.common.mocks.getPokemonListMock
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme
import java.net.URLDecoder
import kotlin.math.absoluteValue
import com.example.androidtemplateapp.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TeamPager(pokemonList: List<Pokemon>) {
    val pagerState = rememberPagerState(pageCount = { pokemonList.size })

    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        HorizontalPager(
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MemberItem(pokemon: Pokemon, pagerState: PagerState, page: Int) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .padding(20.dp)
            .graphicsLayer {
                val pageOffset =
                    ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
            },
        shape = AbsoluteCutCornerShape(40.dp),
        colors = CardDefaults.cardColors(containerColor = pokemon.getDominantColorOrDefault())
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MemberImage(pokemon)
            MemberName(pokemon)
        }
    }
}

@Composable
fun MemberImage(pokemon: Pokemon) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(URLDecoder.decode(pokemon.url, "UTF-8"))
            .crossfade(true)
            .apply { if (LocalInspectionMode.current) placeholder(R.drawable.pokeball) }
            .build(),
        contentDescription = "Member Image",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .clip(CircleShape)
            .aspectRatio(1f)
            .padding(8.dp)
    )
}

@Composable
fun MemberName(pokemon: Pokemon) {
    Text(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 40.dp)
            .fillMaxWidth(),
        text = pokemon.name.uppercase(),
        textAlign = TextAlign.Center,
        color = Color.White,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview("Team Pager", showBackground = true)
@Preview("Dark Team Pager", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun TeamPagerPreview() {
    TemplatePreviewTheme {
        TeamPager(pokemonList = getPokemonListMock())
    }
}