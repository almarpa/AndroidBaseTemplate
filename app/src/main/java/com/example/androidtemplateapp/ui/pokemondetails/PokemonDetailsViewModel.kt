package com.example.androidtemplateapp.ui.pokemondetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.androidtemplateapp.domain.PokemonDetailsUseCase
import com.example.androidtemplateapp.entity.Pokemon
import com.example.androidtemplateapp.entity.PokemonDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val pokemonDetailsUseCase: PokemonDetailsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val pokemon = MutableStateFlow(savedStateHandle.toRoute<Pokemon>())

    val pokemonDetails: StateFlow<PokemonDetails?> =
        pokemon.filterNotNull().flatMapLatest { pokemon ->
            pokemonDetailsUseCase.getPokemonDetails(pokemon.id)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null,
        )
}
