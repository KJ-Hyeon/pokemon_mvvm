package com.example.pokebook.data.local

import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.pokebook.data.model.RecentSearches
import retrofit2.http.GET

@Dao
interface SearchDao {

    @Query("SELECT * FROM RecentSearches ORDER BY RecentSearches.time DESC")
    suspend fun getSearches(): List<RecentSearches>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(recentSearches: RecentSearches)

    @Query("DELETE FROM RecentSearches WHERE search = :search")
    suspend fun deleteSearch(search: String)

    @Query("UPDATE RecentSearches SET time = :time WHERE search = :search")
    suspend fun updateSearch(time: Long, search: String)


}