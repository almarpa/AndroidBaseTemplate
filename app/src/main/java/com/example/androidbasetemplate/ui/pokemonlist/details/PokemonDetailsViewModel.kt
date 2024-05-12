package com.example.androidbasetemplate.ui.pokemonlist.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidbasetemplate.domain.PokemonUseCase
import com.example.androidbasetemplate.entity.PokemonDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val pokemonUseCase: PokemonUseCase,
) : ViewModel() {

    private val _pokemonDetails = MutableLiveData<PokemonDetails>()
    val pokemonDetails: LiveData<PokemonDetails>
        get() = _pokemonDetails

    fun getPokemonDetails(pokemonID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _pokemonDetails.postValue(pokemonUseCase.getPokemon(pokemonID))
        }
    }
}
