package com.example.androidbasetemplate.ui.favorites

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

sealed interface FavouritesUiState {
    data object Loading : FavouritesUiState
    data class Success(val favouriteList: List<Pokemon>) : FavouritesUiState
    data object Error : FavouritesUiState
}

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val pokemonUseCase: PokemonUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavouritesUiState>(FavouritesUiState.Loading)
    val uiState: StateFlow<FavouritesUiState> = _uiState

    init {
        getFavourites()
    }

    fun getFavourites() {
        _uiState.tryEmit(FavouritesUiState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            pokemonUseCase.getFavouritesPokemons()
                .catch {
                    _uiState.tryEmit(FavouritesUiState.Error)
                }
                .collect { pokemonList ->
                    _uiState.tryEmit(FavouritesUiState.Success(pokemonList))
                }
        }
    }

    fun savePokemonToFavourites(pokemon: Pokemon) {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonUseCase.savePokemonToFavourites(pokemon)
        }
    }
}
