package com.nedoluzhko.mynotes.newWord

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class NewWordViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewWordViewModel::class.java)) {
            return NewWordViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}