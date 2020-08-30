package com.nedoluzhko.mynotes.repository

import androidx.lifecycle.LiveData
import com.nedoluzhko.mynotes.database.WordDao
import com.nedoluzhko.mynotes.database.WordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordRepository(private val wordDao: WordDao) {
    val allWords: LiveData<List<WordEntity>> = wordDao.getAllWords()

    suspend fun insert(word: WordEntity) {
        withContext(Dispatchers.IO) {
            wordDao.insert(word)
        }
    }

    suspend fun delete(wordEntity: WordEntity) {
        withContext(Dispatchers.IO) {
            wordDao.delete(wordEntity)
        }
    }

    suspend fun deleteByIds(ids: List<Long>) {
        withContext(Dispatchers.IO) {
            for (id in ids) wordDao.deleteById(id)
        }
    }
}