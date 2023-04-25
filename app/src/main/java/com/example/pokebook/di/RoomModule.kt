package com.example.pokebook.di

import android.content.Context
import androidx.room.Room
import com.example.pokebook.data.local.SearchDao
import com.example.pokebook.data.local.SearchDatabase
import com.example.pokebook.data.model.RecentSearches
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun providesSearchDatabase(@ApplicationContext context: Context): SearchDatabase {
        return Room.databaseBuilder(
            context,
            SearchDatabase::class.java,
            "search.db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesSearchDao(searchDatabase: SearchDatabase): SearchDao {
        return searchDatabase.searchDao()
    }

}