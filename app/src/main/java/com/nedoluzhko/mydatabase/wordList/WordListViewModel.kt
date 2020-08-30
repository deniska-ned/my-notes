package com.nedoluzhko.mydatabase.wordList

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.selection.SelectionTracker
import com.nedoluzhko.mydatabase.database.WordDatabase
import com.nedoluzhko.mydatabase.database.WordEntity
import com.nedoluzhko.mydatabase.repository.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WordListViewModel(application: Application) : AndroidViewModel(application) {
    private val classTag = "WordListViewModel"

    init {
        Log.i(classTag, "WordListViewModel inited")
    }

    private val repository: WordRepository
    private val db: WordDatabase

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val allWords: LiveData<List<WordEntity>>

    lateinit var tracker: SelectionTracker<Long>

    val navigateToNewWordFragment = MutableLiveData<Boolean>()

    init {
        navigateToNewWordFragment.value = false
        db = WordDatabase.getInstance(application)
        val wordDao = db.wordDao

        repository = WordRepository(wordDao)
        allWords = repository.allWords
    }

    fun getSelectedItemsIds(): List<Long> {
        return tracker.selection.toList()
    }

    fun delete(word: WordEntity) {
        uiScope.launch {
            repository.delete(word)
        }
    }

    fun deleteByIds(ids: List<Long>) {
        uiScope.launch {
            repository.deleteByIds(ids)
        }
    }

    fun onFloatButtonPressed() {
        navigateToNewWordFragment.value = true
    }

    fun deleteSelectedItems() {
        val selectedItemIds = getSelectedItemsIds()
        Log.i(classTag, "Selected ids for removal: $selectedItemIds")

        deleteByIds(selectedItemIds)
        Log.i(classTag, "Items deleted")
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()

        Log.i(classTag, "WordListViewModel closed")
    }
}