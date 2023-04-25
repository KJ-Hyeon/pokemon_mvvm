package com.example.pokebook.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecentSearches(
    val search: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var time: Long = System.currentTimeMillis()
}
