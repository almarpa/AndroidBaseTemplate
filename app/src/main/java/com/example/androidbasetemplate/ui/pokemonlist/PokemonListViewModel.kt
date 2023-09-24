package com.example.androidbasetemplate.ui.pokemonlist

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidbasetemplate.domain.PokemonUseCase
import com.example.androidbasetemplate.entity.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface PokemonListUiState {
    object Loading : PokemonListUiState
    data class Success(val pokemonList: List<Pokemon>) : PokemonListUiState
    object Error : PokemonListUiState
}

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonUseCase: PokemonUseCase,
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<PokemonListUiState>(PokemonListUiState.Success(emptyList()))
    val uiState: StateFlow<PokemonListUiState> = _uiState

    init {
        getPokemonList()
    }

    @VisibleForTesting
    private fun getPokemonList() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.tryEmit(PokemonListUiState.Loading)
            pokemonUseCase.getPokemons()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    Log.e("ERROR", "getPokemonList", e)
                    _uiState.tryEmit(PokemonListUiState.Error)
                }
                .collect { pokemonList ->
                    _uiState.tryEmit(PokemonListUiState.Success(pokemonList))
                }
        }
    }
}
