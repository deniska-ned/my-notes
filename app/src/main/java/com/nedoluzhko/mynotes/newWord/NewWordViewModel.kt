package com.nedoluzhko.mynotes.newWord

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nedoluzhko.mynotes.database.WordDatabase
import com.nedoluzhko.mynotes.database.WordEntity
import com.nedoluzhko.mynotes.repository.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewWordViewModel(application: Application) : AndroidViewModel(application) {
    private val tag = "NewWordViewModel"

    private val repository: WordRepository
    private val db: WordDatabase

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val navigateToWordListFragment = MutableLiveData<Boolean>()

    val newWordField = MutableLiveData<String>()

    init {
        navigateToWordListFragment.value = false
        db = WordDatabase.getInstance(application)
        val wordDao = db.wordDao
        repository = WordRepository(wordDao)
    }

    fun onAddButtonClicked() {
        if (newWordField.value != null) {
            uiScope.launch {
                repository.insert(WordEntity(word = newWordField.value!!))
                navigateToWordListFragment.value = true
            }
        } else {
            Log.i(tag, "NO VALUE")
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}