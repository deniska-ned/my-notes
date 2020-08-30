package com.nedoluzhko.mynotes.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var word: String = "Empty string"
)