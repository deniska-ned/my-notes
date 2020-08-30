package com.nedoluzhko.mydatabase.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wordEntity: WordEntity)

    @Query("DELETE FROM word_table")
    fun deleteAll()

    @Query("SELECT * FROM word_table ORDER BY word")
    fun getAllWords(): LiveData<List<WordEntity>>

    @Delete
    fun delete(wordEntity: WordEntity)

    @Query("DELETE FROM word_table WHERE id = :id")
    fun deleteById(id: Long)
}