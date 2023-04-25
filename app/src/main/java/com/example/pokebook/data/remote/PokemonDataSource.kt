package com.example.pokebook.data.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.pokebook.data.local.SearchDao
import com.example.pokebook.data.model.Pokemon
import com.example.pokebook.data.model.RecentSearches
import javax.inject.Inject

class PokemonDataSource @Inject constructor(
    private val pokeService: PokeService,
    private val searchDao: SearchDao

) {

    // Retrofit2
    suspend fun getPokemon(pokemon: String): Pokemon {
        return pokeService.getPokemon(pokemon)
    }

    // Room
    suspend fun getSearch(): List<RecentSearches> {
        return searchDao.getSearches()
    }

    suspend fun insertSearch(recentSearches: RecentSearches) {
        searchDao.insertSearch(recentSearches)
    }

    suspend fun deleteSearch(recentSearches: RecentSearches) {
        searchDao.deleteSearch(recentSearches.search)
    }

    suspend fun updateSearch(time: Long, search: String) {
        searchDao.updateSearch(time, search)
    }

}