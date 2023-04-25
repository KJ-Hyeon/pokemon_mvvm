package com.example.pokebook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokebook.data.model.RecentSearches

@Database(entities = [RecentSearches::class], version = 1)
abstract class SearchDatabase: RoomDatabase() {

    abstract fun searchDao(): SearchDao

}