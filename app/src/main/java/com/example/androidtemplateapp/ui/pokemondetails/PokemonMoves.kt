package com.example.androidtemplateapp.ui.pokemondetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtemplateapp.entity.Move
import com.example.androidtemplateapp.ui.common.mocks.getPokemonMoveListMock

@Composable
fun PokemonMoves(moves: List<Move>) {
    val firstFourMoves = moves.take(4)

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            count = firstFourMoves.size,
            key = { firstFourMoves[it].move.name }
        ) {
            PokemonMove(move = firstFourMoves[it])
        }
    }
}

@Composable
fun PokemonMove(move: Move) {
    Card(shape = CutCornerShape(16.dp)) {
        Box(Modifier.padding(12.dp)) {
            Text(
                text = move.move.name.uppercase(),
                maxLines = 2,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }
    }
}

@Preview("Pokemon Moves")
@Composable
fun PokemonMovesPreview() {
    PokemonMoves(moves = getPokemonMoveListMock())
}