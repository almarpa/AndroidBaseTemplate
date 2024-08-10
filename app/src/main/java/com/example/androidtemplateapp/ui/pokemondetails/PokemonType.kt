package com.example.androidtemplateapp.ui.pokemondetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtemplateapp.entity.TypeX
import com.example.androidtemplateapp.entity.TypeXX
import com.example.androidtemplateapp.entity.enums.PokemonTypeEnum
import com.example.androidtemplateapp.ui.common.spacer.CustomSpacer

@Composable
fun PokemonType(types: List<TypeX>) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (type in types) {
            CustomSpacer(width = 8)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .clip(CircleShape)
                    .background(type.typeXX.getColor())
                    .height(30.dp)
            ) {
                Text(
                    text = type.typeXX.name.value.uppercase(), color = Color.White, fontSize = 18.sp
                )
            }
            CustomSpacer(width = 8)
        }
    }
}

@Composable
@Preview("Pokemon Type")
fun PokemonTypePreview() {
    PokemonType(
        listOf(
            TypeX(1, TypeXX(PokemonTypeEnum.ICE, "")),
            TypeX(2, TypeXX(PokemonTypeEnum.PSYCHIC, "")),
        )
    )
}
