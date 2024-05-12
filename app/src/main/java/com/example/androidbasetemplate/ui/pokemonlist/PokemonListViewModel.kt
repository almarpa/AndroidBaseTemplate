package com.example.androidbasetemplate.ui.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidbasetemplate.domain.PokemonUseCase
import com.example.androidbasetemplate.entity.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface PokemonListUiState {
    data object Loading : PokemonListUiState
    data class Success(val pokemonList: List<Pokemon>) : PokemonListUiState
    data object Error : PokemonListUiState
}

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonUseCase: PokemonUseCase,
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<PokemonListUiState>(PokemonListUiState.Success(emptyList()))
    val uiState: StateFlow<PokemonListUiState> = _uiState

    var firstVisibleItemIdx: Int = 0
    var firstVisibleItemOffset: Int = 0

    init {
        getPokemonList()
    }

    fun getPokemonList() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.tryEmit(PokemonListUiState.Loading)
            pokemonUseCase.getPokemons()
                .catch {
                    _uiState.tryEmit(PokemonListUiState.Error)
                }
                .collect { pokemonList ->
                    _uiState.tryEmit(PokemonListUiState.Success(pokemonList))
                }
        }
    }
}
