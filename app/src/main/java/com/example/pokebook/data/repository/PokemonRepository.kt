package com.example.pokebook.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.pokebook.data.model.Pokemon
import com.example.pokebook.data.model.RecentSearches
import com.example.pokebook.data.remote.PokemonDataSource
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val pokemonDataSource: PokemonDataSource) {

    suspend fun getPokemon(pokemon: String): Pokemon {
        return pokemonDataSource.getPokemon(pokemon)
    }

    // Room
    suspend fun getSearch(): List<RecentSearches> {
        return pokemonDataSource.getSearch()
    }

    suspend fun insertSearch(recentSearches: RecentSearches) {
        pokemonDataSource.insertSearch(recentSearches)
    }

    suspend fun deleteSearch(recentSearches: RecentSearches) {
        pokemonDataSource.deleteSearch(recentSearches)
    }

    suspend fun updateSearch(time: Long, search: String) {
        pokemonDataSource.updateSearch(time, search)
    }
}