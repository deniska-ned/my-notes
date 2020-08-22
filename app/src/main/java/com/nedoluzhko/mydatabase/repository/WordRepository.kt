package com.nedoluzhko.mydatabase.repository

import androidx.lifecycle.LiveData
import com.nedoluzhko.mydatabase.database.WordDao
import com.nedoluzhko.mydatabase.database.WordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordRepository(private val wordDao: WordDao) {
    val allWords: LiveData<List<WordEntity>> = wordDao.getAllWords()

    suspend fun insert(word: WordEntity) {
        withContext(Dispatchers.IO) {
            wordDao.insert(word)
        }
    }

    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            wordDao.deleteAll()
        }
    }

    suspend fun delete(wordEntity: WordEntity) {
        withContext(Dispatchers.IO) {
            wordDao.delete(wordEntity)
        }
    }
}