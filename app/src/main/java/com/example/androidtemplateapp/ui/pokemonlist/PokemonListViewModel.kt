package com.example.androidtemplateapp.ui.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.androidtemplateapp.domain.PokemonUseCase
import com.example.androidtemplateapp.entity.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SearchUiState {
    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data class Success(val pokemonList: List<Pokemon>) : SearchUiState
    data object NotFound : SearchUiState
    data object Error : SearchUiState
}

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonUseCase: PokemonUseCase,
) : ViewModel() {

    companion object {
        const val PAGE_SIZE = 20
    }

    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _searchUiState

    private var _visibleItems: Pair<Int, Int> = 0 to 0
    val visibleItems: Pair<Int, Int> = _visibleItems

    val pokemonList: Flow<PagingData<Pokemon>> =
        pokemonUseCase.getPokemons(pageSize = PAGE_SIZE).cachedIn(viewModelScope)

    fun onPokemonSearch(name: String) {
        if (name.length > 2) {
            _searchUiState.tryEmit(SearchUiState.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                pokemonUseCase.searchPokemonByName(name)
                    .catch {
                        _searchUiState.tryEmit(SearchUiState.Error)
                    }
                    .collect { searchList ->
                        _searchUiState.tryEmit(
                            if (searchList.isEmpty()) {
                                SearchUiState.NotFound
                            } else {
                                SearchUiState.Success(searchList)
                            }
                        )
                    }
            }
        } else {
            removeCurrentSearch()
        }
    }

    fun removeCurrentSearch() {
        _searchUiState.tryEmit(SearchUiState.Idle)
    }

    fun setCurrentScrollPosition(visibleItems: Pair<Int, Int>) {
        _visibleItems = visibleItems
    }
}
