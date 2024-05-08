package com.example.androidbasetemplate.ui.pokemonlist.details

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidbasetemplate.entity.PokemonDetails
import com.example.androidbasetemplate.entity.Stat
import com.example.androidbasetemplate.entity.StatX
import com.example.androidbasetemplate.entity.TypeX
import com.example.androidbasetemplate.entity.TypeXX
import com.example.androidbasetemplate.entity.enums.PokemonTypeEnum
import com.example.androidbasetemplate.entity.enums.StatNameEnum

@Preview("Pokemon Stats", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PokemonStats(
    pokemonDetails: PokemonDetails = PokemonDetails(
        id = 1,
        order = 1,
        name = "Bulbasour",
        baseExperience = 64,
        height = 24,
        weight = 12,
        imageURL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
        stats = listOf(
            Stat(
                baseStat = 50,
                effort = 30,
                statX = StatX(StatNameEnum.ATTACK, "")
            ),
            Stat(
                baseStat = 80,
                effort = 70,
                statX = StatX(StatNameEnum.DEFENSE, "")
            ),
            Stat(
                baseStat = 80,
                effort = 70,
                statX = StatX(name = StatNameEnum.HP, url = "")
            )
        ),
        types = listOf(
            TypeX(
                slot = 1,
                typeXX = TypeXX(PokemonTypeEnum.BUG, "")
            ),
            TypeX(
                slot = 2,
                typeXX = TypeXX(PokemonTypeEnum.POISON, "")
            )
        )
    ),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        pokemonDetails.stats.forEach { stat ->
            PokemonStat(stat = stat)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview("Pokemon Stat", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PokemonStat(
    stat: Stat = Stat(
        baseStat = 50,
        effort = 50,
        statX = StatX(name = StatNameEnum.HP, url = "")
    ),
) {
    var startAnim by remember { mutableStateOf(false) }
    val animatedStatValue by animateFloatAsState(
        targetValue = if (startAnim) stat.baseStat / 100.toFloat() else .15f,
        animationSpec = tween(1000, 100),
        label = "StatProgressAnimation"
    )

    LaunchedEffect(key1 = true) { startAnim = true }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(.2f),
            text = LocalContext.current.getString(stat.statX.getAbbreviation()),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedStatValue)
                    .clip(CircleShape)
                    .background(stat.statX.getColor())
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = (animatedStatValue * 100).toInt().toString(),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                )
            }
        }
    }
}