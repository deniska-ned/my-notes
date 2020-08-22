package com.nedoluzhko.mydatabase.wordList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nedoluzhko.mydatabase.database.WordDatabase
import com.nedoluzhko.mydatabase.database.WordEntity
import com.nedoluzhko.mydatabase.repository.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WordListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WordRepository
    private val db: WordDatabase

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val tag = "WordListViewModel"

    val allWords: LiveData<List<WordEntity>>

    val navigateToNewWordFragment = MutableLiveData<Boolean>()

    init {
        navigateToNewWordFragment.value = false
        db = WordDatabase.getInstance(application)
        val wordDao = db.wordDao
        repository = WordRepository(wordDao)
        allWords = repository.allWords
    }

    fun delete(word: WordEntity) {
        uiScope.launch {
            repository.delete(word)
        }
    }

    fun onFloatButtonPressed() {
        navigateToNewWordFragment.value = true
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}